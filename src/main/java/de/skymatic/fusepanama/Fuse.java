package de.skymatic.fusepanama;

import de.skymatic.fusepanama.macfuse.fuse_h;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayouts;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.NativeScope;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Fuse implements AutoCloseable {

	private final NativeScope globalScope;
	private final FuseOperations fuseOperations;

	private Fuse(FuseOperations fuseOperations, Path mountPoint) {
		this.globalScope = NativeScope.unboundedScope();
		this.fuseOperations = fuseOperations;
		var nativeFuseOps = fuse_h.fuse_operations.allocate(globalScope);
		var notImplementedMethods = Arrays.stream(fuseOperations.getClass().getMethods())
				.filter(method -> method.getAnnotation(NotImplemented.class) != null)
				.map(Method::getName)
				.collect(Collectors.toSet());

		if (!notImplementedMethods.contains("getattr")) {
			var method = fuse_h.fuse_operations.getattr.allocate(this::getattr);
			fuse_h.fuse_operations.getattr$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("readdir")) {
			var method = fuse_h.fuse_operations.readdir.allocate(this::readdir);
			fuse_h.fuse_operations.readdir$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("open")) {
			var method = fuse_h.fuse_operations.open.allocate(this::open);
			fuse_h.fuse_operations.open$set(nativeFuseOps, method.address());
		}

		if (!notImplementedMethods.contains("read")) {
			var method = fuse_h.fuse_operations.read.allocate(this::read);
			fuse_h.fuse_operations.read$set(nativeFuseOps, method.address());
		}

		// TODO: add further methods

		fuseMain(Arrays.asList("fusefs-3000", "-f", "-d", mountPoint.toString()), nativeFuseOps);
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		return fuseOperations.getattr(CLinker.toJavaStringRestricted(path), new Stat(stat));
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		return fuseOperations.readdir(CLinker.toJavaStringRestricted(path), new DirFiller(buf, filler), offset, new FileInfo(fi));
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		return fuseOperations.open(CLinker.toJavaStringRestricted(path), new FileInfo(fi));
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		var buffer = buf.asSegmentRestricted(size).asByteBuffer();
		return fuseOperations.read(CLinker.toJavaStringRestricted(path), buffer, size, offset, new FileInfo(fi));
	}

	private int fuseMain(List<String> flags, MemorySegment fuseOperations) {
		try (var scope = NativeScope.unboundedScope()) {
			var args = flags.stream().map(s -> CLinker.toCString(s, scope)).toArray(Addressable[]::new);
			var argc = args.length;
			var argv = scope.allocateArray(MemoryLayouts.ADDRESS, args);
			return fuse_h.fuse_main_real(argc, argv, fuseOperations, fuse_h.fuse_operations.sizeof(), MemoryAddress.NULL);
		}
	}

	@Override
	public void close() {
		var ctx = fuse_h.fuse_context.ofAddressRestricted(fuse_h.fuse_get_context());
		var fusePtr = fuse_h.fuse_context.fuse$get(ctx);
		fuse_h.fuse_exit(fusePtr);
		globalScope.close();
	}

	public static Fuse mount(FuseOperations fuseOperations, Path mountPoint) {
		return new Fuse(fuseOperations, mountPoint);
	}

}
