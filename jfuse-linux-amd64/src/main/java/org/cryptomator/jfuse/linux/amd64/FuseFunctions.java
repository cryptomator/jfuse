package org.cryptomator.jfuse.linux.amd64;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

/**
 * These method references can not be jextract'ed, partly due to jextract not being able to understand {@code #define},
 * partly due to slight differences in the FUSE API, which applies a versioning scheme via dlvsym, that Panama's default
 * {@link java.lang.foreign.SymbolLookup} doesn't support.
 */
class FuseFunctions {

	// see https://github.com/libfuse/libfuse/blob/fuse-3.12.0/include/fuse_lowlevel.h#L1892-L1923
	private static final FunctionDescriptor FUSE_PARSE_CMDLINE = FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS);

	private final MethodHandle fuse_parse_cmdline;

	private FuseFunctions() {
		var lookup = SymbolLookup.loaderLookup();
		var linker = Linker.nativeLinker();
		this.fuse_parse_cmdline = lookup.find("fuse_parse_cmdline")
				.map(symbol -> linker.downcallHandle(symbol, FUSE_PARSE_CMDLINE))
				.orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol fuse_parse_cmdline"));
	}

	private static class Holder {
		private static final FuseFunctions INSTANCE = new FuseFunctions();
	}

	public static int fuse_parse_cmdline(MemorySegment args, MemorySegment opts) {
		try {
			return (int) Holder.INSTANCE.fuse_parse_cmdline.invokeExact(args, opts);
		} catch (Throwable e) {
			throw new AssertionError("should not reach here", e);
		}
	}

}
