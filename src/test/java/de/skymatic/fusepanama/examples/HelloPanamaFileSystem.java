package de.skymatic.fusepanama.examples;

import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseConnInfo;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.Statvfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.CompletionException;

import static de.skymatic.fusepanama.FuseOperations.Operation.*;

public class HelloPanamaFileSystem implements FuseOperations {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
	}

	private static final int S_IFDIR = 0040000;
	private static final int S_IFREG = 0100000;

	private static final Logger LOG = LoggerFactory.getLogger(HelloPanamaFileSystem.class);

	public static final String HELLO_PATH = "/hello.txt";
	public static final String HELLO_STR = "Hello Panama!";

	public static void main(String[] args) {
		Path mountPoint = Path.of("/Volumes/foo");
		try (var fuse = new Fuse(new HelloPanamaFileSystem())) {
			LOG.info("Mounting at {}...", mountPoint);
			int result = fuse.mount(mountPoint);
			if (result == 0) {
				LOG.info("Mounted to {}. Unmount to terminate this process", mountPoint);
			} else {
				LOG.error("Failed to mount to {}. Exit code: {}", mountPoint, result);
			}
		} catch (CompletionException e) {
			LOG.error("Un/Mounting failed. ", e);
			System.exit(1);
		}
	}

	@Override
	public Set<Operation> supportedOperations() {
		return EnumSet.of(DESTROY, GET_ATTR, INIT, OPEN, OPEN_DIR, READ, READ_DIR, RELEASE, RELEASE_DIR, STATFS);
	}

	@Override
	public int access(String path, int mask) {
		LOG.debug("access() {}", path);
		return 0;
	}

	@Override
	public int getattr(String path, Stat stat) {
		LOG.debug("getattr() {}", path);
		if ("/".equals(path)) {
			stat.setMode((short) (S_IFDIR | 0755));
			stat.setNLink((short) 2);
			return 0;
		} else if (HELLO_PATH.equals(path)) {
			stat.setMode((short) (S_IFREG | 0444));
			stat.setNLink((short) 1);
			stat.setSize(HELLO_STR.getBytes().length);
			return 0;
		} else if (path.length() == 4) {
			stat.setMode((short) (S_IFREG | 0444));
			stat.setNLink((short) 1);
			stat.setSize(0);
			return 0;
		} else {
			return -Errno.ENOENT;
		}
	}

	@Override
	public void init(FuseConnInfo conn) {
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
			return -Errno.ENOENT;
		}
		return 0;
	}

	@Override
	public int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		LOG.debug("read() {}", path);
		if (!HELLO_PATH.equals(path)) {
			return -Errno.ENOENT;
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
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi) {
		LOG.debug("readdir() {}", path);
		if (offset == 0) {
			filler.fill(".", null, 0);
			filler.fill("..", null, 1);
			filler.fill(HELLO_PATH.substring(1), null, 2);
			filler.fill("aaa", null, 3);
			filler.fill("bbb", null, 4);
			filler.fill("ccc", null, 5);
			filler.fill("ddd", null, 6);
			filler.fill("xxx", null, 7);
			filler.fill("yyy", null, 8);
			filler.fill("zzz", null, 9);
		}
		return 0;
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
