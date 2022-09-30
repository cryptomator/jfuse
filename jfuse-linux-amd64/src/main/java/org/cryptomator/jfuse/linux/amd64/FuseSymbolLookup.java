package org.cryptomator.jfuse.linux.amd64;

import java.lang.foreign.Addressable;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public class FuseSymbolLookup implements SymbolLookup {

	private static final int RTLD_NOW = 0x02;

	// https://man7.org/linux/man-pages/man3/dlopen.3.html
	private static final FunctionDescriptor DLOPEN = FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT);
	private static final FunctionDescriptor DLCLOSE = FunctionDescriptor.of(JAVA_INT, ADDRESS);
	private static final FunctionDescriptor DLERROR = FunctionDescriptor.of(ADDRESS);

	// https://man7.org/linux/man-pages/man3/dlvsym.3.html
	private static final FunctionDescriptor DLVSYM = FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS);
	private static final FunctionDescriptor DLSYM = FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS);

	private final Linker linker = Linker.nativeLinker();
	private final MethodHandle dlopen;
	private final MethodHandle dlclose;
	private final MethodHandle dlerror;
	private final MethodHandle dlvsym;
	private final MethodHandle dlsym;
	private final AtomicReference<MemoryAddress> libHandle = new AtomicReference<>();

	private FuseSymbolLookup() {
		this.dlopen = linker.downcallHandle(linker.defaultLookup().lookup("dlopen").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlopen")), DLOPEN);
		this.dlclose = linker.downcallHandle(linker.defaultLookup().lookup("dlclose").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlclose")), DLCLOSE);
		this.dlerror = linker.downcallHandle(linker.defaultLookup().lookup("dlerror").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlerror")), DLERROR);
		this.dlvsym = linker.downcallHandle(linker.defaultLookup().lookup("dlvsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlvsym")), DLVSYM);
		this.dlsym = linker.downcallHandle(linker.defaultLookup().lookup("dlsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlsym")), DLSYM);
	}

	public static FuseSymbolLookup getInstance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final FuseSymbolLookup INSTANCE = new FuseSymbolLookup();
	}

	public void open(String libPath, MemorySession session) {
		try {
			MemoryAddress handle = (MemoryAddress) dlopen.invokeExact((Addressable) session.allocateUtf8String(libPath), RTLD_NOW);
			libHandle.set(handle);
			session.addCloseAction(this::close);
		} catch (Throwable e) {
			throw new AssertionError(e);
		}
	}

	private void close() {
		try {
			dlclose.invokeExact((Addressable) libHandle.getAndSet(null));
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param nameAndVersion the symbol name and version separated by '@'.
	 */
	@Override
	public Optional<MemorySegment> lookup(String nameAndVersion) {
		var handle = libHandle.get();
		if (handle == null) {
			return Optional.empty();
		}
		var sep = nameAndVersion.indexOf('@');
		try (var session = MemorySession.openConfined()) {
			MemoryAddress addr = MemoryAddress.NULL;
			if(sep != -1) {
				String name = nameAndVersion.substring(0, sep);
				String version = nameAndVersion.substring(sep + 1);
				addr = (MemoryAddress) dlvsym.invokeExact((Addressable) handle, (Addressable) session.allocateUtf8String(name),(Addressable) session.allocateUtf8String(version));
			}

			if(MemoryAddress.NULL.equals(addr)) {
				addr = (MemoryAddress) dlsym.invokeExact((Addressable) handle, (Addressable) session.allocateUtf8String(nameAndVersion));
			}

			if (MemoryAddress.NULL.equals(addr)) {
				var error = (MemoryAddress) dlerror.invokeExact();
				System.err.println("dlvsym failed for symbol " + nameAndVersion + ": " + error.getUtf8String(0));
				return Optional.empty();
			} else {
				return Optional.of(MemorySegment.ofAddress(addr, 0, MemorySession.global()));
			}
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

}