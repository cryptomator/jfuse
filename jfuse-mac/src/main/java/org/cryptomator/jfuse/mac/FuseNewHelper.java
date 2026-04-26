package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.mac.extr.fuse3.fuse_h;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Helper class to call fuse_new
 * <p>
 * This class is necessary due to changes in libfuse 3.17.1 and onwards: The function {@code fuse_new} is _not_ available anymore as unversioned symbol.
 * One can call the function either by also specifying the version or call the unversioned symbol {@code fuse_new_31}.
 * <p>
 * On macFUSE, we call {@code _fuse_new_31} (the 5-arg internal variant) directly, passing a properly
 * initialized {@code libfuse_version} struct with {@code darwin_extensions_enabled=0}.
 * This is required for FSKit backend support — the 4-arg wrapper zeros the version struct,
 * which has the same effect, but calling the internal symbol directly makes the intent explicit
 * and matches what fuse_main_real_versioned does.
 */
public class FuseNewHelper {

	private static final AtomicReference<FuseNewHelper> INSTANCE = new AtomicReference<>(null);
	private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup().or(Linker.nativeLinker().defaultLookup());

	// 5-arg _fuse_new_31: (fuse_args*, fuse_operations*, size_t, libfuse_version*, void*) -> fuse*
	private static final FunctionDescriptor DESC_5ARG = FunctionDescriptor.of(
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_LONG,
			fuse_h.C_POINTER,
			fuse_h.C_POINTER
	);

	// 4-arg fallback: (fuse_args*, fuse_operations*, size_t, void*) -> fuse*
	private static final FunctionDescriptor DESC_4ARG = FunctionDescriptor.of(
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_LONG,
			fuse_h.C_POINTER
	);

	// libfuse_version struct: {uint32 major, uint32 minor, uint32 hotfix, uint32 flags}
	private static final MemoryLayout LIBFUSE_VERSION_LAYOUT = MemoryLayout.structLayout(
			ValueLayout.JAVA_INT.withName("major"),
			ValueLayout.JAVA_INT.withName("minor"),
			ValueLayout.JAVA_INT.withName("hotfix"),
			ValueLayout.JAVA_INT.withName("flags")
	);

	private final MethodHandle fuse_new;
	private final boolean fiveArg;

	private FuseNewHelper(String symbolName, boolean fiveArg) {
		this.fiveArg = fiveArg;
		this.fuse_new = Linker.nativeLinker().downcallHandle(
				findOrThrow(symbolName),
				fiveArg ? DESC_5ARG : DESC_4ARG);
	}

	public MemorySegment fuse_new(MemorySegment args, MemorySegment op, long op_size, MemorySegment private_data) {
		try {
			if (fiveArg) {
				try (var arena = Arena.ofConfined()) {
					var version = arena.allocate(LIBFUSE_VERSION_LAYOUT);
					version.set(ValueLayout.JAVA_INT, 0, 3);  // major
					version.set(ValueLayout.JAVA_INT, 4, 18); // minor
					version.set(ValueLayout.JAVA_INT, 8, 2);  // hotfix
					version.set(ValueLayout.JAVA_INT, 12, 1); // darwin_extensions_enabled=1
					return (MemorySegment) fuse_new.invokeExact(args, op, op_size, version, private_data);
				}
			} else {
				return (MemorySegment) fuse_new.invokeExact(args, op, op_size, private_data);
			}
		} catch (Throwable ex) {
			throw new AssertionError("should not reach here", ex);
		}
	}

	public synchronized static FuseNewHelper getInstance() {
		if (INSTANCE.get() == null) {
			INSTANCE.set(createInstance());
		}
		return INSTANCE.get();
	}

	private static FuseNewHelper createInstance() throws IllegalStateException {
		if (getLibVersion() < 317) {
			return new FuseNewHelper("fuse_new", false);
		} else {
			// Prefer _fuse_new_31 (5-arg internal) if available, fall back to fuse_new_31 (4-arg wrapper)
			if (SYMBOL_LOOKUP.find("_fuse_new_31").isPresent()) {
				return new FuseNewHelper("_fuse_new_31", true);
			} else {
				return new FuseNewHelper("fuse_new_31", false);
			}
		}
	}

	private static int getLibVersion() {
		var fuse_versionSymbol = SymbolLookup.loaderLookup().find("fuse_version").orElseThrow();
		var fuse_versionMethodHandle = Linker.nativeLinker().downcallHandle(fuse_versionSymbol, FunctionDescriptor.of(ValueLayout.JAVA_INT));
		try {
			return (int) fuse_versionMethodHandle.invokeExact();
		} catch (Throwable e) {
			throw new RuntimeException("Failed to call native method fuse_version", e);
		}
	}

	private static MemorySegment findOrThrow(String symbol) {
		return SYMBOL_LOOKUP.find(symbol)
				.orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
	}
}
