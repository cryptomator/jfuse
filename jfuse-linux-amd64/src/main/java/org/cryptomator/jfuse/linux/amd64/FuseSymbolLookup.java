package org.cryptomator.jfuse.linux.amd64;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

import static java.lang.foreign.ValueLayout.ADDRESS;

public class FuseSymbolLookup implements SymbolLookup {

	private static final MemorySegment RTLD_GLOBAL = MemorySegment.ofAddress(0L); // defined in /usr/include/dlfcn.h

	// https://man7.org/linux/man-pages/man3/dlerror.3.html
	private static final FunctionDescriptor DLERROR = FunctionDescriptor.of(ADDRESS);

	// https://man7.org/linux/man-pages/man3/dlvsym.3.html
	private static final FunctionDescriptor DLVSYM = FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS, ADDRESS);
	private static final FunctionDescriptor DLSYM = FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS);

	private final MethodHandle dlerror;
	private final MethodHandle dlvsym;
	private final MethodHandle dlsym;

	private FuseSymbolLookup() {
		var linker = Linker.nativeLinker();
		var defaultLookup = linker.defaultLookup();
		this.dlerror = linker.downcallHandle(defaultLookup.find("dlerror").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlerror")), DLERROR);
		this.dlvsym = linker.downcallHandle(defaultLookup.find("dlvsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlvsym")), DLVSYM);
		this.dlsym = linker.downcallHandle(defaultLookup.find("dlsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlsym")), DLSYM);
	}

	public static FuseSymbolLookup getInstance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final FuseSymbolLookup INSTANCE = new FuseSymbolLookup();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param nameAndVersion the symbol name and version separated by '@'.
	 */
	@Override
	public Optional<MemorySegment> find(String nameAndVersion) {
		var sep = nameAndVersion.indexOf('@');
		try (var session = Arena.ofConfined()) {
			MemorySegment addr = MemorySegment.NULL;
			if (sep != -1) {
				String name = nameAndVersion.substring(0, sep);
				String version = nameAndVersion.substring(sep + 1);
				addr = (MemorySegment) dlvsym.invokeExact(RTLD_GLOBAL, session.allocateFrom(name), session.allocateFrom(version));
			}

			if (MemorySegment.NULL.equals(addr)) {
				addr = (MemorySegment) dlsym.invokeExact(RTLD_GLOBAL, session.allocateFrom(nameAndVersion));
			}

			if (MemorySegment.NULL.equals(addr)) {
				var error = (MemorySegment) dlerror.invokeExact();
				System.err.println("dlvsym failed for symbol " + nameAndVersion + ": " + error.getString(0));
				return Optional.empty();
			} else {
				return Optional.of(addr);
			}
		} catch (Throwable e) {
			return Optional.empty();
		}
	}

}