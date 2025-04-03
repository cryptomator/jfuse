package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_h;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

/**
 * Class for not jextract'able symbols, e.g. fuse_new_31
 */
public class fuse_hHelper {

	private static final String FUSE_NEW_SYMBOLNAME = "fuse_new_31";

	static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup()
			.or(Linker.nativeLinker().defaultLookup());

	static MemorySegment findOrThrow(String symbol) {
		return SYMBOL_LOOKUP.find(symbol)
				.orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
	}

	private static class fuse_new {
		public static final FunctionDescriptor DESC = FunctionDescriptor.of(
				fuse_h.C_POINTER,
				fuse_h.C_POINTER,
				fuse_h.C_POINTER,
				fuse_h.C_LONG,
				fuse_h.C_POINTER
		);

		public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(
				findOrThrow(FUSE_NEW_SYMBOLNAME),
				DESC);
	}

	/**
	 * Function descriptor for:
	 * {@snippet lang = c:
	 * struct fuse *fuse_new(struct fuse_args *args, const struct fuse_operations *op, size_t op_size, void *private_data)
	 *}
	 */
	public static FunctionDescriptor fuse_new$descriptor() {
		return fuse_new.DESC;
	}


	/**
	 * Downcall method handle for:
	 * {@snippet lang = c:
	 * struct fuse *fuse_new(struct fuse_args *args, const struct fuse_operations *op, size_t op_size, void *private_data)
	 *}
	 */
	public static MethodHandle fuse_new$handle() {
		return fuse_new.HANDLE;
	}

	/**
	 * {@snippet lang = c:
	 * struct fuse *fuse_new(struct fuse_args *args, const struct fuse_operations *op, size_t op_size, void *private_data)
	 *}
	 */
	public static MemorySegment fuse_new(MemorySegment args, MemorySegment op, long op_size, MemorySegment private_data) {
		var mh$ = fuse_new.HANDLE;
		try {
			return (MemorySegment) mh$.invokeExact(args, op, op_size, private_data);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
}
