package de.skymatic.fusepanama.examples;

import java.foreign.Scope;
import java.foreign.memory.Callback;
import java.foreign.memory.Pointer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.libfuse.fuse_common_h;
import com.github.libfuse.fuse_h;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.StubFuseFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usr.include.sys.stat_h;

public class HelloFileSystem extends StubFuseFileSystem {

	private static final int S_IFDIR = 0040000;
	private static final int S_IFREG = 0100000;

	private static final Logger LOG = LoggerFactory.getLogger(HelloFileSystem.class);

	public static final String HELLO_PATH = "/hello";
	public static final String HELLO_STR = "Hello World!";

	public static void main(String[] args) throws IOException {
		Path mountPoint = Files.createTempDirectory("mnt");
		LOG.info("mounting at {}. Unmount to terminate this process.", mountPoint);
		Fuse.mount(new HelloFileSystem(), mountPoint.toString(), false);
	}

	@Override
	public int getattr(Pointer<Byte> path, Pointer<stat_h.stat> buf) {
		String p = Pointer.toString(path);
		LOG.debug("getattr() {}", p);
		if ("/".equals(p)) {
			buf.get().st_mode$set((short) (S_IFDIR | 0755));
			buf.get().st_nlink$set((short) 2);
			return 0;
		} else if (HELLO_PATH.equals(p)) {
			buf.get().st_mode$set((short) (S_IFREG | 0444));
			buf.get().st_nlink$set((short) 1);
			buf.get().st_size$set(HELLO_STR.getBytes().length);
			return 0;
		} else {
			return -Errno.ENOENT;
		}
	}

	@Override
	public int readdir(Pointer<Byte> path, Pointer<?> buf, Callback<fuse_h.FI1> filler, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		String p = Pointer.toString(path);
		LOG.debug("readdir() {}", p);
		try (Scope scope = fi.scope().fork()) {
			filler.asFunction().fn(buf, scope.allocateCString("."), Pointer.ofNull(), 0);
			filler.asFunction().fn(buf, scope.allocateCString(".."), Pointer.ofNull(), 0);
			filler.asFunction().fn(buf, scope.allocateCString(HELLO_PATH.substring(1)), Pointer.ofNull(), 0);
			return 0;
		}
	}

	@Override
	public int open(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		String p = Pointer.toString(path);
		LOG.debug("open() {}", p);
		if (!HELLO_PATH.equals(p)) {
			return -Errno.ENOENT;
		}
		return 0;
	}

	@Override
	public int read(Pointer<Byte> path, Pointer<Byte> buf, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		String p = Pointer.toString(path);
		LOG.debug("read() {}", p);
		if (!HELLO_PATH.equals(p)) {
			return -Errno.ENOENT;
		}

		ByteBuffer content = StandardCharsets.UTF_8.encode(HELLO_STR);
		content.position((int) Math.min(content.capacity(), offset));
		long numBytes = Math.min(size, content.remaining());

		Pointer.copy(Pointer.fromByteBuffer(content), buf, numBytes);

		return (int) numBytes;
	}
	
}
