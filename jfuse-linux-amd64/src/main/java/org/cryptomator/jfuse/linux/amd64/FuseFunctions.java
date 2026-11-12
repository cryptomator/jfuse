package org.cryptomator.jfuse.linux.amd64;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_BOOLEAN;
import static java.lang.foreign.ValueLayout.JAVA_INT;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

/**
 * These method references can not be jextract'ed, partly due to jextract not being able to understand {@code #define},
 * partly due to slight differences in the FUSE API, which applies a versioning scheme via dlvsym, that Panama's default
 * {@link java.lang.foreign.SymbolLookup} doesn't support.
 */
class FuseFunctions {

	// see https://github.com/libfuse/libfuse/blob/fuse-3.12.0/include/fuse_lowlevel.h#L1892-L1923
	private static final FunctionDescriptor PARSE_CMDLINE_DESCRIPTOR = FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS);
	//https://github.com/libfuse/libfuse/blob/fuse-3.17.4/lib/fuse_lowlevel.c#L2035
	private static final FunctionDescriptor SET_FEATURE_FLAG_DESCRIPTOR = FunctionDescriptor.of(JAVA_BOOLEAN, ADDRESS, JAVA_LONG);
	private static final FunctionDescriptor UNSET_FEATURE_FLAG_DESCRIPTOR = FunctionDescriptor.ofVoid(ADDRESS, JAVA_LONG);
	private static final FunctionDescriptor GET_FEATURE_FLAG_DESCRIPTOR = FunctionDescriptor.of(JAVA_BOOLEAN, ADDRESS, JAVA_LONG);

	private final MethodHandle fuse_parse_cmdline;
	private final Optional<MethodHandle> fuse_set_feature_flag;
	private final Optional<MethodHandle> fuse_unset_feature_flag;
	private final Optional<MethodHandle> fuse_get_feature_flag;

	private FuseFunctions() {
		var lookup = SymbolLookup.loaderLookup();
		var linker = Linker.nativeLinker();
		this.fuse_parse_cmdline = lookup.find("fuse_parse_cmdline")
				.map(symbol -> linker.downcallHandle(symbol, PARSE_CMDLINE_DESCRIPTOR))
				.orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol fuse_parse_cmdline"));
		this.fuse_set_feature_flag = lookup.find("fuse_set_feature_flag")
				.map(symbol -> linker.downcallHandle(symbol, SET_FEATURE_FLAG_DESCRIPTOR));
		this.fuse_unset_feature_flag = lookup.find("fuse_unset_feature_flag")
				.map(symbol -> linker.downcallHandle(symbol, UNSET_FEATURE_FLAG_DESCRIPTOR));
		this.fuse_get_feature_flag = lookup.find("fuse_get_feature_flag")
				.map(symbol -> linker.downcallHandle(symbol, GET_FEATURE_FLAG_DESCRIPTOR));
	}

	private static class Holder {
		private static final FuseFunctions INSTANCE = new FuseFunctions();
	}

	/**
	 * Indicates whether the loaded fuse library exports the {@code fuse_*_feature_flag} functions.
	 *
	 * @return {@code true} if {@link #fuse_set_feature_flag(MemorySegment, long)}, {@link #fuse_unset_feature_flag(MemorySegment, long)} and {@link #fuse_get_feature_flag(MemorySegment, long)} can be called
	 * @implNote libfuse added these functions in 3.17.0, but as {@code static inline} functions in {@code fuse_common.h}. They only became exported symbols in 3.17.3, which {@code fuse_version()} can not distinguish from 3.17.0 - 3.17.2.
	 */
	public static boolean hasFeatureFlagFunctions() {
		return Holder.INSTANCE.fuse_set_feature_flag.isPresent() //
				&& Holder.INSTANCE.fuse_unset_feature_flag.isPresent() //
				&& Holder.INSTANCE.fuse_get_feature_flag.isPresent();
	}

	public static int fuse_parse_cmdline(MemorySegment args, MemorySegment opts) {
		try {
			return (int) Holder.INSTANCE.fuse_parse_cmdline.invokeExact(args, opts);
		} catch (Throwable e) {
			throw new AssertionError("should not reach here", e);
		}
	}

	public static boolean fuse_set_feature_flag(MemorySegment fuse_conn_info, long flag) throws UnsupportedOperationException {
		var method = Holder.INSTANCE.fuse_set_feature_flag.orElseThrow(() -> new UnsupportedOperationException("The loaded fuse library does not implement fuse_set_feature_flag"));
		try {
			return (boolean) method.invokeExact(fuse_conn_info, flag);
		} catch (Throwable e) {
			throw new AssertionError("should not reach here", e);
		}
	}

	public static void fuse_unset_feature_flag(MemorySegment fuse_conn_info, long flag) throws UnsupportedOperationException {
		var method = Holder.INSTANCE.fuse_unset_feature_flag.orElseThrow(() -> new UnsupportedOperationException("The loaded fuse library does not implement fuse_unset_feature_flag"));
		try {
			method.invokeExact(fuse_conn_info, flag);
		} catch (Throwable e) {
			throw new AssertionError("should not reach here", e);
		}
	}

	public static boolean fuse_get_feature_flag(MemorySegment fuse_conn_info, long flag) throws UnsupportedOperationException {
		var method = Holder.INSTANCE.fuse_get_feature_flag.orElseThrow(() -> new UnsupportedOperationException("The loaded fuse library does not implement fuse_get_feature_flag"));
		try {
			return (boolean) method.invokeExact(fuse_conn_info, flag);
		} catch (Throwable e) {
			throw new AssertionError("should not reach here", e);
		}
	}

}
