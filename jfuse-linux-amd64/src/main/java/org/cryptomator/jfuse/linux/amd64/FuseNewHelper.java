package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_h;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Helper class to call fuse_new
 * <p>
 * This class is necessary due to changes in libfuse 3.17.1 and onwards: In this version, only fuse_new is only available as a versioned symbol or as a renamed symbol (fuse_new_31).
 * <p>
 * Versioned symbols can be loaded by a custom SymbolLookup (as done in PR #117), but libraries loaded via {@link System#loadLibrary(String)} cannot be accessed.
 * As a result, we would need to speciy for each set of Linux distributions a path where the library might be located.
 * To circumvent this issue, we use {@code fuse_version} to decide which (unversioned) symbol name we have to use.
 */
public class FuseNewHelper {

	private static final AtomicReference<FuseNewHelper> INSTANCE = new AtomicReference<>(null);

	public static final FunctionDescriptor DESC = FunctionDescriptor.of(
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_POINTER,
			fuse_h.C_LONG,
			fuse_h.C_POINTER
	);

	private final MethodHandle fuse_new;

	private FuseNewHelper(String symbolName) {
		this.fuse_new = Linker.nativeLinker().downcallHandle(
				findOrThrow(symbolName),
				DESC);
	}

	public MemorySegment fuse_new(MemorySegment args, MemorySegment op, long op_size, MemorySegment private_data) {
		try {
			return (MemorySegment) fuse_new.invokeExact(args, op, op_size, private_data);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}

	private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup()
			.or(Linker.nativeLinker().defaultLookup());

	private static MemorySegment findOrThrow(String symbol) {
		return SYMBOL_LOOKUP.find(symbol)
				.orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
	}


	public synchronized static FuseNewHelper instantiate() throws IllegalStateException {
		if (INSTANCE.get() != null) {
			throw new IllegalStateException("Already instantiated");
		}

		if (getLibVersion() < 317) {
			INSTANCE.set(new FuseNewHelper("fuse_new"));
		} else {
			INSTANCE.set(new FuseNewHelper("fuse_new_31"));
		}
		return INSTANCE.get();
	}

	private static int getLibVersion() {
		var fuse_versionSymbol = SymbolLookup.loaderLookup().find("fuse_version").orElseThrow();
		var fuse_versionMethodHandle = Linker.nativeLinker().downcallHandle(fuse_versionSymbol, FunctionDescriptor.of(ValueLayout.JAVA_INT));
		try {
			return (int) fuse_versionMethodHandle.invokeExact();
		} catch (Throwable e) {
			throw new RuntimeException("Failed to call native method fuse_new", e);
		}
	}

	@Nullable
	public static FuseNewHelper getInstance() {
		return INSTANCE.get();
	}
}
