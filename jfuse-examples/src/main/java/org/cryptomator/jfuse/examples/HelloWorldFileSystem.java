package org.cryptomator.jfuse.examples;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.Statvfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.cryptomator.jfuse.api.Stat.S_IFDIR;
import static org.cryptomator.jfuse.api.Stat.S_IFREG;

public class HelloWorldFileSystem implements FuseOperations {

	private static final Logger LOG = LoggerFactory.getLogger(HelloWorldFileSystem.class);

	public static final String HELLO_PATH = "/hello.txt";
	public static final String HELLO_STR = "Hello Panama!";
	private final Errno errno;

	public static void main(String[] args) {
		if (args.length != 1) {
			LOG.error("Invalid number of arguments. Expected {mountPoint}.");
		}
		Path mountPoint = Path.of(args[0]);
		var builder = Fuse.builder();
		var fuseOps = new HelloWorldFileSystem(builder.errno());
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

	public HelloWorldFileSystem(Errno errno) {
		this.errno = errno;
	}

	@Override
	public Errno errno() {
		return errno;
	}

	@Override
	public Set<Operation> supportedOperations() {
		return EnumSet.of(Operation.DESTROY, Operation.GET_ATTR, Operation.INIT, Operation.OPEN, Operation.OPEN_DIR, Operation.READ, Operation.READ_DIR, Operation.RELEASE, Operation.RELEASE_DIR, Operation.STATFS);
	}

	@Override
	public int access(String path, int mask) {
		LOG.debug("access() {}", path);
		return 0;
	}

	@SuppressWarnings("OctalInteger")
	@Override
	public int getattr(String path, Stat stat, FileInfo fi) {
		LOG.debug("getattr() {}", path);
		if ("/".equals(path)) {
			stat.setMode(S_IFDIR | 0755);
			stat.setNLink((short) 2);
			return 0;
		} else if (HELLO_PATH.equals(path)) {
			stat.setMode(S_IFREG | 0444);
			stat.setNLink((short) 1);
			stat.setSize(HELLO_STR.getBytes().length);
			return 0;
		} else if (path.length() == 4) {
			stat.setMode(S_IFREG | 0444);
			stat.setNLink((short) 1);
			stat.setSize(0);
			return 0;
		} else {
			return -errno.enoent();
		}
	}

	@Override
	public void init(FuseConnInfo conn, FuseConfig cfg) {
		LOG.info("init() {}.{}", conn.protoMajor(), conn.protoMinor());
	}

	@Override
	public void destroy() {
		LOG.info("destroy()");
	}

	@Override
	public int open(String path, FileInfo fi) {
		LOG.debug("open() {}", path);
		if (!HELLO_PATH.equals(path)) {
			return -errno.enoent();
		}
		return 0;
	}

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.debug("read() {}", path);
		if (!HELLO_PATH.equals(path)) {
			return -errno.enoent();
		}

		ByteBuffer content = StandardCharsets.UTF_8.encode(HELLO_STR);
		int pos = (int) Math.min(content.capacity(), offset);
		int len = (int) Math.min(size, content.remaining());
		buf.put(content.slice(pos, len));

		return len;
	}

	@Override
	public int release(String path, FileInfo fi) {
		LOG.debug("release() {}", path);
		return 0;
	}

	@Override
	public int opendir(String path, FileInfo fi) {
		LOG.debug("opendir() {}", path);
		return 0;
	}

	@Override
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi, int flags) {
		LOG.debug("readdir() {} {}", path, offset);
		try {
			filler.fill(".");
			filler.fill("..");
			filler.fill(HELLO_PATH.substring(1));
			filler.fill("aaa");
			filler.fill("bbb");
			filler.fill("ccc");
			filler.fill("ddd");
			filler.fill("xxx");
			filler.fill("yyy");
			filler.fill("zzz");
			return 0;
		} catch (IOException e) {
			return -errno.eio();
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
