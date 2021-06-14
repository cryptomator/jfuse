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

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Fuse implements AutoCloseable {

	private final ResourceScope fuseScope;

	private Fuse(FuseOperations fuseOperations, Path mountPoint) {
		this.fuseScope = ResourceScope.newConfinedScope();
		var nativeFuseOps = fuse_operations.allocate(fuseScope);
		fuseOperations.supportedOperations().forEach(op -> op.bind(fuseOperations, nativeFuseOps, fuseScope));
		fuseMain(Arrays.asList("fusefs-3000", "-f", mountPoint.toString()), nativeFuseOps);
	}

	private int fuseMain(List<String> flags, MemorySegment fuseOperations) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var cStrings = flags.stream().map(s -> CLinker.toCString(s, UTF_8, scope)).toArray(Addressable[]::new);
			var allocator = SegmentAllocator.ofScope(scope);
			var argc = cStrings.length;
			var argv = allocator.allocateArray(CLinker.C_POINTER, cStrings);
			return fuse_h.fuse_main_real(argc, argv, fuseOperations, fuseOperations.byteSize(), MemoryAddress.NULL);
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
