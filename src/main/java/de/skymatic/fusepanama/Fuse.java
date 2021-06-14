package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_context;
import de.skymatic.fusepanama.lowlevel.fuse_h;
import de.skymatic.fusepanama.lowlevel.fuse_operations;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Fuse implements AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(Fuse.class);

	private final ResourceScope fuseScope;
	private final FuseOperations fuseOperations;

	private Fuse(FuseOperations fuseOperations, Path mountPoint) {
		this.fuseScope = ResourceScope.newConfinedScope();
		this.fuseOperations = fuseOperations;
		var nativeFuseOps = fuse_operations.allocate(fuseScope);
		var notImplementedMethods = Arrays.stream(fuseOperations.getClass().getMethods())
				.filter(method -> method.getAnnotation(NotImplemented.class) != null)
				.map(Method::getName)
				.collect(Collectors.toSet());

		if (!notImplementedMethods.contains("getattr")) {
			var method = fuse_operations.getattr.allocate(this::getattr, fuseScope);
			fuse_operations.getattr$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("readdir")) {
			var method = fuse_operations.readdir.allocate(this::readdir, fuseScope);
			fuse_operations.readdir$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("open")) {
			var method = fuse_operations.open.allocate(this::open, fuseScope);
			fuse_operations.open$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("read")) {
			var method = fuse_operations.read.allocate(this::read, fuseScope);
			fuse_operations.read$set(nativeFuseOps, method.address());
		}

		// TODO: add further methods

		fuseMain(Arrays.asList("fusefs-3000", "-f", mountPoint.toString()), nativeFuseOps);
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return fuseOperations.getattr(CLinker.toJavaString(path, UTF_8), new Stat(stat, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			LOG.info("readdir {}", CLinker.toJavaString(path, UTF_8));
			return fuseOperations.readdir(CLinker.toJavaString(path, UTF_8), new DirFiller(buf, filler), offset, new FileInfo(fi, scope));
		}
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return fuseOperations.open(CLinker.toJavaString(path, UTF_8), new FileInfo(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = buf.asSegment(size, scope).asByteBuffer();
			return fuseOperations.read(CLinker.toJavaString(path, UTF_8), buffer, size, offset, new FileInfo(fi, scope));
		}
	}

	private int fuseMain(List<String> flags, MemorySegment fuseOperations) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var cStrings = flags.stream().map(s -> CLinker.toCString(s, UTF_8, scope)).toArray(Addressable[]::new);
			var allocator = SegmentAllocator.ofScope(scope);
			var argc = cStrings.length;
			var argv = allocator.allocateArray(CLinker.C_POINTER, cStrings);
			return fuse_h.fuse_main_real(argc, argv, fuseOperations, fuse_operations.sizeof(), MemoryAddress.NULL);
		}
	}

	@Override
	public void close() {
		try (var scope = ResourceScope.newConfinedScope()) {
			var ctx = fuse_context.ofAddress(fuse_h.fuse_get_context(), scope);
			var fusePtr = fuse_context.fuse$get(ctx);
			fuse_h.fuse_exit(fusePtr);
			fuseScope.close();
		}
	}

	public static Fuse mount(FuseOperations fuseOperations, Path mountPoint) {
		return new Fuse(fuseOperations, mountPoint);
	}

}
