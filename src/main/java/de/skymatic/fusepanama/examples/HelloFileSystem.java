package de.skymatic.fusepanama.examples;

import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelloFileSystem implements FuseOperations {

	private static final int S_IFDIR = 0040000;
	private static final int S_IFREG = 0100000;

	private static final Logger LOG = LoggerFactory.getLogger(HelloFileSystem.class);

	public static final String HELLO_PATH = "/hello";
	public static final String HELLO_STR = "Hello World!";

	public static void main(String[] args) {
		Path mountPoint = Path.of("/Volumes/foo");
		LOG.info("mounting at {}. Unmount to terminate this process.", mountPoint);
		Fuse.mount(new HelloFileSystem(), mountPoint);
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
		} else {
			return -Errno.ENOENT;
		}
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
	public int readdir(String path, DirFiller filler, long offset, FileInfo fi) {
		LOG.debug("readdir() {}", path);
		if (offset == 0) {
			filler.fill(".", null, 0);
			filler.fill("..", null, 1);
			filler.fill(HELLO_PATH.substring(1), null, 2);
		}
		return 0;
	}

}
