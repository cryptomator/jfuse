package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_fill_dir_t;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;

import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_LONG_LONG;
import static jdk.incubator.foreign.CLinker.C_POINTER;

/**
 * {@link de.skymatic.fusepanama.lowlevel.fuse_fill_dir_t} is an upcall interface
 * and can't be used in order to invoke the "filler" callback that C expectes us to call.
 */
public class DirFiller {

	// int fuse_fill_dir_t(void *buf, const char *name, const struct stat *stbuf, off_t off);
	private static final MethodType METHOD_TYPE = MethodType.methodType(int.class,
			MemoryAddress.class,
			MemoryAddress.class,
			MemoryAddress.class,
			long.class);

	private static final FunctionDescriptor FUNC = FunctionDescriptor.of(C_INT,
			C_POINTER,
			C_POINTER,
			C_POINTER,
			C_LONG_LONG);

	private final MemoryAddress buf;
	//private final MethodHandle methodHandle;
	private final fuse_fill_dir_t callback;

	DirFiller(MemoryAddress buf, MemoryAddress callback) {
		this.buf = buf;
		//this.methodHandle = CLinker.getInstance().downcallHandle(callback, METHOD_TYPE, FUNC);
		System.out.println("FOO: " + callback);
		this.callback = fuse_fill_dir_t.ofAddress(callback);
		System.out.println("BAR");
	}

	/**
	 * Inserts a single item into the directory entry buffer.
	 * @param name The file name
	 * @param stat Currently ignored, future use.
	 * @param offset The offset of the readdir-call plus the number of already filled entries
	 * @return 0 if readdir should continue to fill in further items, non-zero otherwise
	 */
	public int fill(String name, Stat stat, long offset) {
		System.out.println("fill");
		try (var scope = ResourceScope.globalScope()) {
			// return (int) methodHandle.invokeExact(buf, CLinker.toCString(name, StandardCharsets.UTF_8, scope).address(), MemoryAddress.NULL, offset);
			return callback.apply(buf, CLinker.toCString(name, StandardCharsets.UTF_8, scope).address(), MemoryAddress.NULL, offset);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new AssertionError(e);
		}
	}

}
