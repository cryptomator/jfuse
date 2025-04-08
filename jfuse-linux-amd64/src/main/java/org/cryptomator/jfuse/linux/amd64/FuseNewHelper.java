package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_h;

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
 * This class is necessary due to changes in libfuse 3.17.1 and onwards: The function {@code fuse_new} is _not_ available anymore as unversioned symbol.
 * One can call the function either by also specifying the version or call the unversioned symbol {@code fuse_new_31}.
 * <p>
 * Versioned symbols require a custom SymbolLookup (see PR #117), but libraries loaded via {@link System#loadLibrary(String)} cannot be accessed.
 * Hence, this would require to _always_ set the libPath, loosing compatiblity to more exotic linux OSs with custom lib locations.
 * To circumvent this issue, we call {@code fuse_version} to decide which (unversioned) symbol name we have to use.
 */
public class FuseNewHelper {

	private static final AtomicReference<FuseNewHelper> INSTANCE = new AtomicReference<>(null);
	private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup().or(Linker.nativeLinker().defaultLookup());
	private static final FunctionDescriptor DESC = FunctionDescriptor.of(
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
			return new FuseNewHelper("fuse_new");
		} else {
			return new FuseNewHelper("fuse_new_31");
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
