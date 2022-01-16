package de.skymatic.fusepanama.examples;

import com.google.common.base.CharMatcher;
import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.Statvfs;
import de.skymatic.fusepanama.TimeSpec;
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
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MirrorPosixFileSystem implements FuseOperations {

	private static final Logger LOG = LoggerFactory.getLogger(HelloPanamaFileSystem.class);

	private final Path root;
	private final Errno errno;
	private final ConcurrentMap<Long, FileChannel> openFiles;
	private final AtomicLong fileHandleGen = new AtomicLong(1L);
	private final FileStore fileStore;

	public static void main(String[] args) {
		Path mirrored = Path.of("/Users/sebastian/Desktop/TMP");
		Path mountPoint = Path.of("/Volumes/foo");
		var builder = Fuse.builder();
		try (var fuse = builder.build(new MirrorPosixFileSystem(mirrored, builder.errno()))) {
			LOG.info("Mounting at {}...", mountPoint);
			int result = fuse.mount("fuse-panama", mountPoint, "-s", "-ovolname=mirror");
			if (result == 0) {
				LOG.info("Mounted to {}. Unmount to terminate this process", mountPoint);
			} else {
				LOG.error("Failed to mount to {}. Exit code: {}", mountPoint, result);
			}
		} catch (CompletionException e) {
			LOG.error("Un/Mounting failed. ", e);
			System.exit(1);
		} catch (IOException e) {
			LOG.error("Failed to create mirror", e);
			System.exit(1);
		}
	}

	public MirrorPosixFileSystem(Path root, Errno errno) throws IOException {
		this.root = root;
		this.errno = errno;
		this.fileStore = Files.getFileStore(root);
		this.openFiles = new ConcurrentHashMap<>();
	}

	private Path resolvePath(String absolutePath) {
		String relativePath = CharMatcher.is('/').trimLeadingFrom(absolutePath);
		return root.resolve(relativePath);
	}

	@Override
	public Set<Operation> supportedOperations() {
		return EnumSet.of(
				Operation.ACCESS,
				Operation.CHMOD,
				Operation.CREATE,
				Operation.DESTROY,
				Operation.GET_ATTR,
				Operation.MKDIR,
				Operation.OPEN_DIR,
				Operation.READ_DIR,
				Operation.RELEASE_DIR,
				Operation.RENAME,
				Operation.RMDIR,
				Operation.OPEN,
				Operation.READ,
				Operation.READLINK,
				Operation.RELEASE,
				Operation.STATFS,
				Operation.SYMLINK,
				Operation.TRUNCATE,
				Operation.UNLINK,
				Operation.UTIMENS,
				Operation.WRITE
		);
	}

	@Override
	public Errno errno() {
		return errno;
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

	@Override
	public int getattr(String path, Stat stat) {
		LOG.trace("getattr {}", path);
		Path node = resolvePath(path);
		try {
			var attrs = Files.readAttributes(node, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			stat.setPermissions(attrs.permissions());
			stat.setSize(attrs.size());
			stat.setNLink((short) 1);
			if (attrs.isDirectory()) {
				stat.toggleDir(true);
				stat.setNLink((short) (2 + countSubDirs(node)));
			} else if (attrs.isSymbolicLink()) {
				stat.toggleLnk(true);
			} else if (attrs.isRegularFile()){
				stat.toggleReg(true);
			}
			stat.aTime().set(attrs.lastAccessTime().toInstant());
			stat.mTime().set(attrs.lastModifiedTime().toInstant());
			stat.cTime().set(attrs.lastAccessTime().toInstant());
			stat.birthTime().set(attrs.creationTime().toInstant());
			return 0;
		} catch (NoSuchFileException e) {
			return -errno.enoent();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	private long countSubDirs(Path dir) throws IOException {
		try (var ds = Files.newDirectoryStream(dir)) {
			return StreamSupport.stream(ds.spliterator(), false).filter(Files::isDirectory).count();
		}
	}

	@Override
	public int chmod(String path, int mode) {
		LOG.trace("chmod {}", path);
		Path node = resolvePath(path);
		try {
			Files.setPosixFilePermissions(node, FileModes.toPermissions(mode));
			return 0;
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int utimens(String path, TimeSpec atime, TimeSpec mtime) {
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
			Files.createDirectory(node, attr);
			return 0;
		} catch (FileAlreadyExistsException e) {
			return -errno.eexist();
		} catch (IOException e) {
			return -errno.eio();
		}
	}

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
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi) {
		LOG.trace("readdir {}", path);
		Path node = resolvePath(path);
		try (var ds = Files.newDirectoryStream(node)) {
			var childNames = StreamSupport.stream(ds.spliterator(), false).map(Path::getFileName).map(Path::toString);
			var allNames = Stream.concat(Stream.of(".", ".."), childNames);
			filler.fillNamesFromOffset(allNames.skip(offset), offset);
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

	private int createOrOpen(String path, FileInfo fi, FileAttribute<?>... attr) {
		Path node = resolvePath(path);
		try {
			var fc = FileChannel.open(node, fi.getOpenFlags(), attr);
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

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.trace("read {} at pos {}", path, offset);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -errno.ebadf();
		}
		try {
			return fc.read(buf, offset);
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
			return fc.write(buf, offset);
		} catch (IOException e) {
			return -errno.eio();
		}
	}

	@Override
	public int truncate(String path, long size) {
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
	public int rename(String oldpath, String newpath) {
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
}
