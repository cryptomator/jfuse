package org.cryptomator.jfuse.linux.amd64;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
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
	private final MethodHandle dlerror;
	private final MethodHandle dlvsym;
	private final MethodHandle dlsym;
	private final AtomicReference<MemorySegment> libHandle = new AtomicReference<>();

	private FuseSymbolLookup() {
		this.dlopen = linker.downcallHandle(linker.defaultLookup().find("dlopen").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlopen")), DLOPEN);
		this.dlerror = linker.downcallHandle(linker.defaultLookup().find("dlerror").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlerror")), DLERROR);
		this.dlvsym = linker.downcallHandle(linker.defaultLookup().find("dlvsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlvsym")), DLVSYM);
		this.dlsym = linker.downcallHandle(linker.defaultLookup().find("dlsym").orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol dlsym")), DLSYM);
	}

	public static FuseSymbolLookup getInstance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final FuseSymbolLookup INSTANCE = new FuseSymbolLookup();
	}

	public void open(String libPath) {
		try (var session = Arena.ofConfined()) {
			MemorySegment handle = (MemorySegment) dlopen.invokeExact(session.allocateFrom(libPath), RTLD_NOW);
			libHandle.set(handle);
		} catch (Throwable e) {
			throw new AssertionError(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param nameAndVersion the symbol name and version separated by '@'.
	 */
	@Override
	public Optional<MemorySegment> find(String nameAndVersion) {
		var handle = libHandle.get();
		if (handle == null) {
			return Optional.empty();
		}
		var sep = nameAndVersion.indexOf('@');
		try (var session = Arena.ofConfined()) {
			MemorySegment addr = MemorySegment.NULL;
			if(sep != -1) {
				String name = nameAndVersion.substring(0, sep);
				String version = nameAndVersion.substring(sep + 1);
				addr = (MemorySegment) dlvsym.invokeExact(handle, session.allocateFrom(name),session.allocateFrom(version));
			}

			if(MemorySegment.NULL.equals(addr)) {
				addr = (MemorySegment) dlsym.invokeExact(handle, session.allocateFrom(nameAndVersion));
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