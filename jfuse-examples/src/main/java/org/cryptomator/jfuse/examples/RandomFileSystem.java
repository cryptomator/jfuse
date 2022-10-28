package org.cryptomator.jfuse.examples;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.Statvfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.cryptomator.jfuse.api.Stat.S_IFDIR;
import static org.cryptomator.jfuse.api.Stat.S_IFREG;

public class RandomFileSystem implements FuseOperations {

	private static final Logger LOG = LoggerFactory.getLogger(RandomFileSystem.class);

	private final Errno errno;
	private final RandomFileStructure rfs;


	public static void main(String[] args) {
		if (args.length != 1) {
			LOG.error("Invalid number of arguments. Expected {mountPoint}.");
		}
		Path mountPoint = Path.of(args[0]);
		var builder = Fuse.builder();
		var fuseOps = new RandomFileSystem(builder.errno());
		try (var fuse = builder.build(fuseOps)) {
			LOG.info("Mounting at {}...", mountPoint);
			fuse.mount("jfuse", mountPoint, "-s");
			LOG.info("Mounted to {}.", mountPoint);
			LOG.info("Enter a anything to unmount...");
			System.in.read();
		} catch (FuseMountFailedException | TimeoutException e) {
			LOG.error("Un/Mounting failed. ", e);
			System.exit(1);
		} catch (IOException e) {
			LOG.error("Failed to create mirror", e);
			System.exit(1);
		}
	}

	public RandomFileSystem(Errno errno) {
		this.errno = errno;
		this.rfs = RandomFileStructure.init(42L, 100);
	}

	@Override
	public Errno errno() {
		return errno;
	}

	@Override
	public Set<Operation> supportedOperations() {
		return EnumSet.of(Operation.GET_ATTR, Operation.OPEN, Operation.OPEN_DIR, Operation.READ, Operation.READ_DIR, Operation.RELEASE, Operation.RELEASE_DIR, Operation.STATFS, Operation.INIT);
	}

	@Override
	public int getattr(String path, Stat stat, FileInfo fi) {
		LOG.debug("getattr() {}", path);
		var node = rfs.getNode(path);
		if (node == null) {
			return -errno.enoent();
		} else {
			fillStats(node, stat);
			return 0;
		}
	}

	@SuppressWarnings("OctalInteger")
	private void fillStats(RandomFileStructure.Node node, Stat stat){
		if (node.isDir()) {
			stat.setMode(S_IFDIR | 0755);
			stat.setNLink((short) (2 + node.children().values().stream().filter(RandomFileStructure.Node::isDir).count()));
			stat.mTime().set(node.lastModified());
		} else {
			stat.setMode(S_IFREG | 0444);
			stat.setNLink((short) 1);
			stat.setSize(0);
			stat.mTime().set(node.lastModified());
		}
	}

	@Override
	public int open(String path, FileInfo fi) {
		return 0;
	}

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		return 0;
	}

	@Override
	public int release(String path, FileInfo fi) {
		return 0;
	}

	@Override
	public int opendir(String path, FileInfo fi) {
		LOG.debug("opendir() {}", path);
		return 0;
	}

	@Override
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi, int flags) {
		LOG.debug("readdir() {} offset={} plus={}", path, offset, (flags & FUSE_READDIR_PLUS) == flags);
		var node = rfs.getNode(path);
		if (node == null) {
			return -errno.enoent();
		} else if (!node.isDir()) {
			return -errno.enotdir();
		} else {
			if (offset == 0 && filler.fill(".", stat -> fillStats(node, stat), ++offset, DirFiller.FUSE_FILL_DIR_PLUS) != 0)
				return 0;
			if (offset == 1 && filler.fill("..", stat -> {}, ++offset, 0) != 0)
				return 0;
			assert offset > 1;
			var childIter = node.children().values().stream().skip(offset - 2).iterator();
			while (childIter.hasNext()) {
				var child = childIter.next();
				if (filler.fill(child.name(), stat -> fillStats(child, stat), ++offset, DirFiller.FUSE_FILL_DIR_PLUS) != 0) {
					return 0;
				}
			}
			return 0;
		}
	}

	@Override
	public int releasedir(String path, FileInfo fi) {
		LOG.debug("releasedir() {}", path);
		return 0;
	}

	@Override
	public int statfs(String path, Statvfs statvfs) {
		LOG.debug("statfs() {}", path);
		statvfs.setNameMax(255);
		statvfs.setBsize(4096);
		statvfs.setBlocks(1000);
		statvfs.setBfree(500);
		statvfs.setBavail(500);
		return 0;
	}
}
