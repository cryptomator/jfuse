package org.cryptomator.jfuse.examples;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.api.FileModes;
import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.api.TimeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

public abstract sealed class AbstractMirrorFileSystem implements FuseOperations permits PosixMirrorFileSystem, WindowsMirrorFileSystem {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractMirrorFileSystem.class);

	protected final Path root;
	protected final Errno errno;
	protected final FileStore fileStore;
	protected final ConcurrentMap<Long, FileChannel> openFiles = new ConcurrentHashMap<>();
	protected final AtomicLong fileHandleGen = new AtomicLong(1L);

	protected AbstractMirrorFileSystem(Path root, Errno errno, FileStore fileStore) {
		this.root = root;
		this.errno = errno;
		this.fileStore = fileStore;
	}

	protected Path resolvePath(String absolutePath) {
		var relativePath = new StringBuilder(absolutePath);
		while (relativePath.length() > 0 && relativePath.charAt(0) == '/') {
			relativePath.deleteCharAt(0);
		}
		return root.resolve(relativePath.toString());
	}

	@Override
	public Set<FuseOperations.Operation> supportedOperations() {
		return EnumSet.of(
				FuseOperations.Operation.ACCESS,
				FuseOperations.Operation.CREATE,
				FuseOperations.Operation.DESTROY,
				FuseOperations.Operation.FLUSH,
				FuseOperations.Operation.FSYNC,
				FuseOperations.Operation.FSYNCDIR,
				FuseOperations.Operation.GET_ATTR,
				FuseOperations.Operation.INIT,
				FuseOperations.Operation.MKDIR,
				FuseOperations.Operation.OPEN_DIR,
				FuseOperations.Operation.READ_DIR,
				FuseOperations.Operation.RELEASE_DIR,
				FuseOperations.Operation.RENAME,
				FuseOperations.Operation.RMDIR,
				FuseOperations.Operation.OPEN,
				FuseOperations.Operation.READ,
				FuseOperations.Operation.READLINK,
				FuseOperations.Operation.RELEASE,
				FuseOperations.Operation.STATFS,
				FuseOperations.Operation.SYMLINK,
				FuseOperations.Operation.TRUNCATE,
				FuseOperations.Operation.UNLINK,
				FuseOperations.Operation.UTIMENS,
				FuseOperations.Operation.WRITE
		);
	}

	@Override
	public Errno errno() {
		return errno;
	}

	@Override
	public void init(FuseConnInfo conn, FuseConfig cfg) {
		conn.setWant(conn.want() | FuseConnInfo.FUSE_CAP_BIG_WRITES);
		conn.setMaxRead(Integer.MAX_VALUE);
		conn.setMaxWrite(Integer.MAX_VALUE);
		conn.setMaxBackground(16);
		conn.setCongestionThreshold(4);
	}

	@Override
	public int access(String path, int mask) {
		LOG.trace("access {}", path);
		Path node = resolvePath(path);
		Set<AccessMode> desiredAccess = EnumSet.noneOf(AccessMode.class);
		if ((mask & 0x01) == 0x01) desiredAccess.add(AccessMode.EXECUTE);
		if ((mask & 0x02) == 0x02) desiredAccess.add(AccessMode.WRITE);
		if ((mask & 0x04) == 0x04) desiredAccess.add(AccessMode.READ);
		try {
			node.getFileSystem().provider().checkAccess(node, desiredAccess.toArray(AccessMode[]::new));
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (AccessDeniedException e) {
			return -errno.eacces();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int statfs(String path, Statvfs statvfs) {
		LOG.trace("statfs");
		try {
			long bsize = 4096L;
			statvfs.setBsize(bsize);
			statvfs.setFrsize(bsize);
			statvfs.setBlocks(fileStore.getTotalSpace() / bsize);
			statvfs.setBavail(fileStore.getUsableSpace() / bsize);
			statvfs.setBfree(fileStore.getUnallocatedSpace() / bsize);
			statvfs.setNameMax(255);
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int symlink(String linkname, String target) {
		LOG.trace("symlink {} -> {}", linkname, target);
		Path node = resolvePath(linkname);
		try {
			Files.createSymbolicLink(node, Path.of(target));
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int readlink(String path, ByteBuffer buf, long len) {
		LOG.trace("readlink {}", path);
		Path node = resolvePath(path);
		try {
			var target = Files.readSymbolicLink(node);
			var tmp = StandardCharsets.UTF_8.encode(target.toString());
			buf.put(tmp);
			return 0;
		} catch (BufferOverflowException e) {
			return -errno.enomem();
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@SuppressWarnings("OctalInteger")
	@Override
	public int getattr(String path, Stat stat, FileInfo fi) {
		LOG.trace("getattr {}", path);
		Path node = resolvePath(path);
		try {
			var attrs = readAttributes(node);
			copyAttrsToStat(attrs, stat);
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@SuppressWarnings("OctalInteger")
	protected void copyAttrsToStat(BasicFileAttributes attrs, Stat stat) {
		if (attrs instanceof PosixFileAttributes posixAttrs) {
			stat.setPermissions(posixAttrs.permissions());
		} else if (attrs instanceof DosFileAttributes dosAttrs) {
			int mode = 0444;
			mode |= dosAttrs.isReadOnly() ? 0000 : 0200; // add write access for owner
			mode |= attrs.isDirectory() ? 0111 : 0000; // add execute access for directories
			stat.setMode(mode);
		}
		stat.setSize(attrs.size());
		stat.setNLink((short) 1);
		if (attrs.isDirectory()) {
			stat.setModeBits(Stat.S_IFDIR);
			stat.setNLink((short) 2); // quick and dirty implementation. should really be 2 + subdir count
		} else if (attrs.isSymbolicLink()) {
			stat.setModeBits(Stat.S_IFLNK);
		} else if (attrs.isRegularFile()) {
			stat.setModeBits(Stat.S_IFREG);
		}
		stat.aTime().set(attrs.lastAccessTime().toInstant());
		stat.mTime().set(attrs.lastModifiedTime().toInstant());
		stat.cTime().set(attrs.lastAccessTime().toInstant());
		stat.birthTime().set(attrs.creationTime().toInstant());
	}

	protected abstract BasicFileAttributes readAttributes(Path node) throws IOException;

	private long countSubDirs(Path dir) throws IOException {
		try (var ds = Files.newDirectoryStream(dir)) {
			return StreamSupport.stream(ds.spliterator(), false).filter(Files::isDirectory).count();
		}
	}

	@Override
	public int utimens(String path, TimeSpec atime, TimeSpec mtime, FileInfo fi) {
		LOG.trace("utimens {}", path);
		Path node = resolvePath(path);
		var view = Files.getFileAttributeView(node, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		var lastModified = mtime.getOptional().map(FileTime::from).orElse(null);
		var lastAccess = atime.getOptional().map(FileTime::from).orElse(null);
		try {
			view.setTimes(lastModified, lastAccess, null);
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int mkdir(String path, int mode) {
		LOG.trace("mkdir {}", path);
		Path node = resolvePath(path);
		var attr = PosixFilePermissions.asFileAttribute(FileModes.toPermissions(mode));
		try {
			createDir(node, attr);
			return 0;
		} catch (FileAlreadyExistsException e) {
			return -errno.eexist();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	protected abstract void createDir(Path node, FileAttribute<Set<PosixFilePermission>> permissions) throws IOException;

	@Override
	public int opendir(String path, FileInfo fi) {
		LOG.trace("opendir {}", path);
		Path node = resolvePath(path);
		if (Files.isDirectory(node)) {
			// no-op: this is a quick and dirty implementation.
			// usually you'd want to open the dir now and keep it open until releasedir(), blocking the resource
			return 0;
		} else {
			return -errno.enotdir();
		}
	}

	@Override
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi, int flags) {
		LOG.trace("readdir {}", path);
		Path node = resolvePath(path);

		try (var ds = Files.newDirectoryStream(node)) {
			filler.fill(".");
			filler.fill("..");
			for (var child : ds) {
				filler.fill(child.getFileName().toString());
			}
			return 0;
		} catch (NotDirectoryException e) {
			return -errno.enotdir();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int releasedir(String path, FileInfo fi) {
		// no-op
		return 0;
	}

	@Override
	public int rmdir(String path) {
		Path node = resolvePath(path);
		if (!Files.isDirectory(node, LinkOption.NOFOLLOW_LINKS)) {
			return -errno.enotdir();
		}
		try {
			Files.delete(node);
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int create(String path, int mode, FileInfo fi) {
		LOG.trace("create {}", path);
		return createOrOpen(path, fi, PosixFilePermissions.asFileAttribute(FileModes.toPermissions(mode)));
	}

	@Override
	public int open(String path, FileInfo fi) {
		LOG.trace("open {}", path);
		return createOrOpen(path, fi);
	}

	private int createOrOpen(String path, FileInfo fi, FileAttribute<?>... attrs) {
		Path node = resolvePath(path);
		try {
			var fc = openFileChannel(node, fi.getOpenFlags(), attrs);
			var fh = fileHandleGen.incrementAndGet();
			fi.setFh(fh);
			openFiles.put(fh, fc);
			return 0;
		} catch (FileAlreadyExistsException e) {
			return -errno.eexist();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	protected abstract FileChannel openFileChannel(Path node, Set<? extends OpenOption> openOptions, FileAttribute<?>... attrs) throws IOException;

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.trace("read {} at pos {}", path, offset);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			int read = 0;
			int toRead = (int) Math.min(size, buf.limit());
			while (read < toRead) {
				int r = fc.read(buf, offset + read);
				if (r == -1) {
					LOG.trace("Reached EOF");
					break;
				}
				read += r;
			}
			return read;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int write(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.trace("write {} at pos {}", path, offset);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			int written = 0;
			int toWrite = (int) Math.min(size, buf.limit());
			while (written < toWrite) {
				written += fc.write(buf, offset + written);
			}
			return written;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int truncate(String path, long size, FileInfo fi) {
		LOG.trace("truncate {} to size {}", path, size);
		Path node = resolvePath(path);
		try (FileChannel fc = FileChannel.open(node, StandardOpenOption.WRITE)) {
			fc.truncate(size);
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int release(String path, FileInfo fi) {
		LOG.trace("release {}", path);
		var fc = openFiles.remove(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			fc.close();
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int unlink(String path) {
		LOG.trace("unlink {}", path);
		Path node = resolvePath(path);
		if (Files.isDirectory(node, LinkOption.NOFOLLOW_LINKS)) {
			return -errno.eisdir();
		}
		try {
			Files.delete(node);
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int rename(String oldpath, String newpath, int flags) {
		LOG.trace("rename {} -> {}", oldpath, newpath);
		Path nodeOld = resolvePath(oldpath);
		Path nodeNew = resolvePath(newpath);
		try {
			Files.move(nodeOld, nodeNew, StandardCopyOption.REPLACE_EXISTING);
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public void destroy() {
		if (!openFiles.isEmpty()) {
			LOG.warn("Found unclosed files when unmounting...");
		}
		openFiles.forEach((fh, fc) -> {
			try {
				fc.close();
			} catch (IOException e) {
				LOG.warn("Failed to close resource with file handle {}", fh);
			}
		});
	}

	@Override
	public int flush(String path, FileInfo fi) {
		LOG.trace("flush {}", path);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			fc.force(false);
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int fsync(String path, int datasync, FileInfo fi) {
		LOG.trace("fsync {}", path);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			fc.force(datasync == 0);
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int fsyncdir(String path, int datasync, FileInfo fi) {
		LOG.trace("fsyncdir {}", path);
		// no-op: this quick and dirty impl doesn't open/close dirs
		return 0;
	}
}
