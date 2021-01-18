package de.skymatic.fusepanama.examples;

import de.skymatic.fusepanama.Errno;
import jnr.ffi.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseFileInfo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class HelloJnrFileSystem extends FuseStubFS {

	private static final int S_IFDIR = 0040000;
	private static final int S_IFREG = 0100000;

	private static final Logger LOG = LoggerFactory.getLogger(HelloJnrFileSystem.class);

	public static final String HELLO_PATH = "/hello.txt";
	public static final String HELLO_STR = "Hello JNR!";

	public static void main(String[] args) {
		Path mountPoint = Path.of("/Volumes/bar");
		LOG.info("mounting at {}. Unmount to terminate this process.", mountPoint);
		new HelloJnrFileSystem().mount(mountPoint, true, false);
	}

	@Override
	public int getattr(String path, FileStat stat) {
		LOG.debug("getattr() {}", path);
		if ("/".equals(path)) {
			stat.st_mode.set((short) (S_IFDIR | 0755));
			stat.st_nlink.set((short) 2);
			return 0;
		} else if (HELLO_PATH.equals(path)) {
			stat.st_mode.set((short) (S_IFREG | 0444));
			stat.st_nlink.set((short) 1);
			stat.st_size.set(HELLO_STR.getBytes().length);
			return 0;
		} else if (path.length() == 4) {
			stat.st_mode.set((short) (S_IFREG | 0444));
			stat.st_nlink.set((short) 1);
			stat.st_size.set(0);
			return 0;
		} else {
			return -Errno.ENOENT;
		}
	}

	@Override
	public int open(String path, FuseFileInfo fi) {
		LOG.debug("open() {}", path);
		if (!HELLO_PATH.equals(path)) {
			return -Errno.ENOENT;
		}
		return 0;
	}

	@Override
	public int read(String path, Pointer buf, long size, long offset, FuseFileInfo fi) {
		LOG.debug("read() {}", path);
		if (!HELLO_PATH.equals(path)) {
			return -Errno.ENOENT;
		}

		ByteBuffer content = StandardCharsets.UTF_8.encode(HELLO_STR);
		int pos = (int) Math.min(content.capacity(), offset);
		int len = (int) Math.min(size, content.remaining());
		buf.put(0, content.array(), pos, len);

		return len;
	}

	@Override
	public int readdir(String path, Pointer buf, FuseFillDir filler, long offset, FuseFileInfo fi) {
		LOG.debug("readdir() {}", path);
		if (offset == 0) {
			filler.apply(buf, ".", null, 0);
			filler.apply(buf, "..", null, 1);
			filler.apply(buf, HELLO_PATH.substring(1), null, 2);
			filler.apply(buf, "aaa", null, 3);
			filler.apply(buf, "bbb", null, 4);
			filler.apply(buf, "ccc", null, 5);
			filler.apply(buf, "ddd", null, 6);
			filler.apply(buf, "xxx", null, 7);
			filler.apply(buf, "yyy", null, 8);
			filler.apply(buf, "zzz", null, 9);
		}
		return 0;
	}

}
