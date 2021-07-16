package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.linux.lowlevel.fuse_operations;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.util.concurrent.CompletableFuture;

class LinuxFuseOperationsMapper {

	final CompletableFuture<Integer> initialized = new CompletableFuture<>();
	final CompletableFuture<Void> destroyed = new CompletableFuture<>();
	final MemorySegment struct;
	private final FuseOperations delegate;
	private final ResourceScope scope;

	public LinuxFuseOperationsMapper(FuseOperations delegate, ResourceScope scope) {
		this.struct = fuse_operations.allocate(scope);
		this.delegate = delegate;
		this.scope = scope;
		fuse_operations.init$set(struct, fuse_operations.init.allocate(this::init, scope));
		fuse_operations.destroy$set(struct, fuse_operations.destroy.allocate(this::destroy, scope));
		delegate.supportedOperations().forEach(this::bind);
	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT, DESTROY -> { /* handled already */ }
			case ACCESS -> fuse_operations.access$set(struct, fuse_operations.access.allocate(this::access, scope));
			case GET_ATTR -> fuse_operations.getattr$set(struct, fuse_operations.getattr.allocate(this::getattr, scope));
			case OPEN -> fuse_operations.open$set(struct, fuse_operations.open.allocate(this::open, scope));
			case OPEN_DIR -> fuse_operations.opendir$set(struct, fuse_operations.opendir.allocate(this::opendir, scope));
			case READ -> fuse_operations.read$set(struct, fuse_operations.read.allocate(this::read, scope));
			case READ_DIR -> fuse_operations.readdir$set(struct, fuse_operations.readdir.allocate(this::readdir, scope));
			case RELEASE -> fuse_operations.release$set(struct, fuse_operations.release.allocate(this::release, scope));
			case RELEASE_DIR -> fuse_operations.releasedir$set(struct, fuse_operations.releasedir.allocate(this::releasedir, scope));
			case STATFS -> fuse_operations.statfs$set(struct, fuse_operations.statfs.allocate(this::statfs, scope));
		}
	}

	private MemoryAddress init(MemoryAddress conn) {
		try (var scope = ResourceScope.newConfinedScope()) {
			if (delegate.supportedOperations().contains(FuseOperations.Operation.INIT)) {
				delegate.init(new LinuxFuseConnInfo(conn, scope));
			}
			initialized.complete(0);
		} catch (Exception e) {
			initialized.completeExceptionally(e);
		}
		return MemoryAddress.NULL;
	}

	private void destroy(MemoryAddress addr) {
		try {
			if (delegate.supportedOperations().contains(FuseOperations.Operation.DESTROY)) {
				delegate.destroy();
			}
			destroyed.complete(null);
		} catch (Exception e) {
			destroyed.completeExceptionally(e);
		}
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(CLinker.toJavaString(path), mask);
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.getattr(CLinker.toJavaString(path), new LinuxStat(stat, scope));
		}
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.open(CLinker.toJavaString(path), new LinuxFileInfo(fi, scope));
		}
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.opendir(CLinker.toJavaString(path), new LinuxFileInfo(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = buf.asSegment(size, scope).asByteBuffer();
			return delegate.read(CLinker.toJavaString(path), buffer, size, offset, new LinuxFileInfo(fi, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.readdir(CLinker.toJavaString(path), new LinuxDirFiller(buf, filler), offset, new LinuxFileInfo(fi, scope));
		}
	}

	private int release(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.release(CLinker.toJavaString(path), new LinuxFileInfo(fi, scope));
		}
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.releasedir(CLinker.toJavaString(path), new LinuxFileInfo(fi, scope));
		}
	}

	private int statfs(MemoryAddress path, MemoryAddress statvfs) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.statfs(CLinker.toJavaString(path), new LinuxStatvfs(statvfs, scope));
		}
	}

}
