package de.skymatic.fusepanama;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.NativeScope;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static jdk.incubator.foreign.CLinker.C_INT;
import static jdk.incubator.foreign.CLinker.C_LONG_LONG;
import static jdk.incubator.foreign.CLinker.C_POINTER;

/**
 * {@link de.skymatic.fusepanama.macfuse.fuse_h.fuse_fs_readdir$filler} is an upcall interface
 * and can't be used in order to invoke the "filler" callback that C expectes us to call.
 */
public class DirFiller {

	// int fuse_fill_dir_t(void *buf, const char *name, const struct stat *stbuf, off_t off);
	private static final MethodType METHOD_TYPE = MethodType.methodType(Integer.class,
			MemoryAddress.class,
			MemoryAddress.class,
			MemoryAddress.class,
			Long.class);

	private static final FunctionDescriptor FUNC = FunctionDescriptor.of(C_INT,
			C_POINTER,
			C_POINTER,
			C_POINTER,
			C_LONG_LONG);

	private final MemoryAddress buf;
	private final MethodHandle methodHandle;

	DirFiller(MemoryAddress buf, MemoryAddress callback) {
		this.buf = buf;
		this.methodHandle = CLinker.getInstance().downcallHandle(callback, METHOD_TYPE, FUNC);
	}

	/**
	 * Inserts a single item into the directory entry buffer.
	 * @param name The file name
	 * @param stat Currently ignored, future use.
	 * @param offset The offset of the readdir-call plus the number of already filled entries
	 * @return 0 if readdir should continue to fill in further items, non-zero otherwise
	 */
	public int fill(String name, Stat stat, long offset) {
		try (NativeScope scope = NativeScope.unboundedScope()) {
			return (int) methodHandle.invokeExact(buf, CLinker.toCString(name, scope), null, offset);
		} catch (Throwable e) {
			throw new AssertionError(e);
		}
	}

}
