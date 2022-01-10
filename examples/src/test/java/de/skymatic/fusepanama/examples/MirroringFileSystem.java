package de.skymatic.fusepanama.examples;

import com.google.common.base.CharMatcher;
import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.Statvfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MirroringFileSystem implements FuseOperations {

	private static final Logger LOG = LoggerFactory.getLogger(HelloPanamaFileSystem.class);

	private final Path root;
	private final ConcurrentMap<Long, FileChannel> openFiles;
	private final AtomicLong fileHandleGen = new AtomicLong(1l);
	private final FileStore fileStore;

	public static void main(String[] args) {
		Path mirrored = Path.of("/Users/sebastian/Desktop/TMP");
		Path mountPoint = Path.of("/Volumes/foo");
		try (var fuse = Fuse.create(new MirroringFileSystem(mirrored))) {
			LOG.info("Mounting at {}...", mountPoint);
			int result = fuse.mount("fuse-panama", mountPoint, "-s", "-r");
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

	public MirroringFileSystem(Path root) throws IOException {
		this.root = root;
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
				Operation.STATFS,
				Operation.GET_ATTR,
				Operation.OPEN_DIR,
				Operation.READ_DIR,
				Operation.RELEASE_DIR,
				Operation.OPEN,
				Operation.READ,
				Operation.RELEASE,
				Operation.DESTROY);
	}

	@Override
	public int access(String path, int mask) {
		Path node = resolvePath(path);
		LOG.debug("access {}", node);
		Set<AccessMode> desiredAccess = EnumSet.noneOf(AccessMode.class);
		if ((mask & 0x01) == 0x01) desiredAccess.add(AccessMode.EXECUTE);
		if ((mask & 0x02) == 0x02) desiredAccess.add(AccessMode.WRITE);
		if ((mask & 0x04) == 0x04) desiredAccess.add(AccessMode.READ);
		try {
			node.getFileSystem().provider().checkAccess(node, desiredAccess.toArray(AccessMode[]::new));
			return 0;
		} catch (NoSuchFileException e) {
			return -ERRNO.enoent();
		} catch (AccessDeniedException e) {
			return -ERRNO.eacces();
		} catch (IOException e) {
			return -ERRNO.eio();
		}
	}

	@Override
	public int statfs(String path, Statvfs statvfs) {
		Path node = resolvePath(path);
		LOG.debug("statfs {}", node);
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
			return -ERRNO.eio();
		}
	}

	@Override
	public int readlink(String path, ByteBuffer buf, long len) {
		// TODO: implement
		return -ERRNO.enosys();
	}

	@Override
	public int getattr(String path, Stat stat) {
		Path node = resolvePath(path);
		LOG.debug("getattr {}", node);
		try {
			var attrs = Files.readAttributes(node, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			stat.setMode((short) FileModes.instance().toMode(attrs));
			stat.setSize(attrs.size());
			if (attrs.isDirectory()) {
				stat.setNLink((short) 2);  // TODO: is this correct?
			} else {
				stat.setNLink((short) 1);
			}
			stat.aTime().set(attrs.lastAccessTime().toInstant());
			stat.mTime().set(attrs.lastModifiedTime().toInstant());
			stat.cTime().set(attrs.lastAccessTime().toInstant());
			stat.birthTime().set(attrs.creationTime().toInstant());
			return 0;
		} catch (IOException e) {
			return -ERRNO.eio();
		}
	}

	@Override
	public int opendir(String path, FileInfo fi) {
		Path node = resolvePath(path);
		LOG.debug("opendir {}", node);
		if (Files.isDirectory(node)) {
			// no-op: this is a quick and dirty implementation.
			// usually you'd want to open the dir now and keep it open until releasedir(), blocking the resource
			return 0;
		} else {
			return -ERRNO.enotdir();
		}
	}

	@Override
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi) {
		Path node = resolvePath(path);
		LOG.debug("readdir {}", node);
		try (var ds = Files.newDirectoryStream(node)) {
			var childNames = StreamSupport.stream(ds.spliterator(), false).map(Path::getFileName).map(Path::toString);
			var allNames = Stream.concat(Stream.of(".", ".."), childNames);
			filler.fillNamesFromOffset(allNames.skip(offset), offset);
			return 0;
		} catch (NotDirectoryException e) {
			return -ERRNO.enotdir();
		} catch (IOException e) {
			return -ERRNO.eio();
		}
	}

	@Override
	public int releasedir(String path, FileInfo fi) {
		// no-op
		return 0;
	}

	@Override
	public int open(String path, FileInfo fi) {
		Path node = resolvePath(path);
		LOG.debug("open {}", node);
		try {
			// TODO check fi.getFlags() and adjust OpenOptions
			var fc = FileChannel.open(node, StandardOpenOption.READ);
			var fh = fileHandleGen.incrementAndGet();
			fi.setFh(fh);
			openFiles.put(fh, fc);
			return 0;
		} catch (IOException e) {
			return -ERRNO.eio();
		}
	}

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.debug("read {} at pos {}", path, offset);
		var fc = openFiles.get(fi.getFh());
		if (fc == null) {
			return -ERRNO.ebadf();
		}
		try {
			return fc.read(buf, offset);
		} catch (IOException e) {
			return -ERRNO.eio();
		}
	}

	@Override
	public int release(String path, FileInfo fi) {
		LOG.debug("release {}", path);
		var fc = openFiles.remove(fi.getFh());
		if (fc == null) {
			return -ERRNO.ebadf();
		}
		try {
			fc.close();
			return 0;
		} catch (IOException e) {
			return -ERRNO.eio();
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
