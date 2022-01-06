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
	final MemorySegment struct;
	private final FuseOperations delegate;
	private final ResourceScope scope;

	public LinuxFuseOperationsMapper(FuseOperations delegate, ResourceScope scope) {
		this.struct = fuse_operations.allocate(scope);
		this.delegate = delegate;
		this.scope = scope;
		fuse_operations.init$set(struct, fuse_operations.init.allocate(this::init, scope).address());
		delegate.supportedOperations().forEach(this::bind);
	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> { /* handled already */ }
			case DESTROY -> fuse_operations.destroy$set(struct, fuse_operations.destroy.allocate(this::destroy, scope).address());
			case ACCESS -> fuse_operations.access$set(struct, fuse_operations.access.allocate(this::access, scope).address());
			case GET_ATTR -> fuse_operations.getattr$set(struct, fuse_operations.getattr.allocate(this::getattr, scope).address());
			case OPEN -> fuse_operations.open$set(struct, fuse_operations.open.allocate(this::open, scope).address());
			case OPEN_DIR -> fuse_operations.opendir$set(struct, fuse_operations.opendir.allocate(this::opendir, scope).address());
			case READ -> fuse_operations.read$set(struct, fuse_operations.read.allocate(this::read, scope).address());
			case READ_DIR -> fuse_operations.readdir$set(struct, fuse_operations.readdir.allocate(this::readdir, scope).address());
			case RELEASE -> fuse_operations.release$set(struct, fuse_operations.release.allocate(this::release, scope).address());
			case RELEASE_DIR -> fuse_operations.releasedir$set(struct, fuse_operations.releasedir.allocate(this::releasedir, scope).address());
			case STATFS -> fuse_operations.statfs$set(struct, fuse_operations.statfs.allocate(this::statfs, scope).address());
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
		delegate.destroy();
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(path.getUtf8String(0), mask);
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.getattr(path.getUtf8String(0), new LinuxStat(stat, scope));
		}
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.open(path.getUtf8String(0), new LinuxFileInfo(fi, scope));
		}
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.opendir(path.getUtf8String(0), new LinuxFileInfo(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.read(path.getUtf8String(0), buffer, size, offset, new LinuxFileInfo(fi, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.readdir(path.getUtf8String(0), new LinuxDirFiller(buf, filler, scope), offset, new LinuxFileInfo(fi, scope));
		}
	}

	private int release(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.release(path.getUtf8String(0), new LinuxFileInfo(fi, scope));
		}
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.releasedir(path.getUtf8String(0), new LinuxFileInfo(fi, scope));
		}
	}

	private int statfs(MemoryAddress path, MemoryAddress statvfs) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.statfs(path.getUtf8String(0), new LinuxStatvfs(statvfs, scope));
		}
	}

}
