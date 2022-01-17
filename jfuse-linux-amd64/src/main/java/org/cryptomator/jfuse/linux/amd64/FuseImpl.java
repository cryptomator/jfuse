package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_operations;
import org.cryptomator.jfuse.linux.amd64.extr.stat_h;
import org.cryptomator.jfuse.linux.amd64.extr.timespec;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public final class FuseImpl extends Fuse {

	private final CompletableFuture<Integer> initialized = new CompletableFuture<>();
	private final FuseOperations delegate;
	private final MemorySegment struct;

	public FuseImpl(FuseOperations fuseOperations) {
		this.struct = fuse_operations.allocate(fuseScope);
		this.delegate = fuseOperations;
		fuse_operations.init$set(struct, fuse_operations.init.allocate(this::init, fuseScope).address());
		fuseOperations.supportedOperations().forEach(this::bind);
	}

	private MemoryAddress init(MemoryAddress conn) {
		try (var scope = ResourceScope.newConfinedScope()) {
			if (delegate.supportedOperations().contains(FuseOperations.Operation.INIT)) {
				delegate.init(new FuseConnInfoImpl(conn, scope));
			}
			initialized.complete(0);
		} catch (Exception e) {
			initialized.completeExceptionally(e);
		}
		return MemoryAddress.NULL;
	}

	@Override
	protected CompletableFuture<Integer> initialized() {
		return initialized;
	}

	@Override
	protected int fuseMain(int argc, MemorySegment argv) {
		return fuse_h.fuse_main_real(argc, argv, struct, struct.byteSize(), MemoryAddress.NULL);
	}

//	private void exit() {
//		try (var scope = ResourceScope.newConfinedScope()) {
//			var ctx = fuse_context.ofAddress(fuse_h.fuse_get_context(), fuseScope);
//			var fusePtr = fuse_context.fuse$get(ctx);
//			fuse_h.fuse_exit(fusePtr);
//		}
//	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> { /* handled already */ }
			case ACCESS -> fuse_operations.access$set(struct, fuse_operations.access.allocate(this::access, fuseScope).address());
			case CHMOD -> fuse_operations.chmod$set(struct, fuse_operations.chmod.allocate(this::chmod, fuseScope).address());
			case CREATE -> fuse_operations.create$set(struct, fuse_operations.create.allocate(this::create, fuseScope).address());
			case DESTROY -> fuse_operations.destroy$set(struct, fuse_operations.destroy.allocate(this::destroy, fuseScope).address());
			case GET_ATTR -> fuse_operations.getattr$set(struct, fuse_operations.getattr.allocate(this::getattr, fuseScope).address());
			case MKDIR -> fuse_operations.mkdir$set(struct, fuse_operations.mkdir.allocate(this::mkdir, fuseScope).address());
			case OPEN -> fuse_operations.open$set(struct, fuse_operations.open.allocate(this::open, fuseScope).address());
			case OPEN_DIR -> fuse_operations.opendir$set(struct, fuse_operations.opendir.allocate(this::opendir, fuseScope).address());
			case READ -> fuse_operations.read$set(struct, fuse_operations.read.allocate(this::read, fuseScope).address());
			case READ_DIR -> fuse_operations.readdir$set(struct, fuse_operations.readdir.allocate(this::readdir, fuseScope).address());
			case READLINK -> fuse_operations.readlink$set(struct, fuse_operations.readlink.allocate(this::readlink, fuseScope).address());
			case RELEASE -> fuse_operations.release$set(struct, fuse_operations.release.allocate(this::release, fuseScope).address());
			case RELEASE_DIR -> fuse_operations.releasedir$set(struct, fuse_operations.releasedir.allocate(this::releasedir, fuseScope).address());
			case RENAME -> fuse_operations.rename$set(struct, fuse_operations.rename.allocate(this::rename, fuseScope).address());
			case RMDIR -> fuse_operations.rmdir$set(struct, fuse_operations.rmdir.allocate(this::rmdir, fuseScope).address());
			case STATFS -> fuse_operations.statfs$set(struct, fuse_operations.statfs.allocate(this::statfs, fuseScope).address());
			case SYMLINK -> fuse_operations.symlink$set(struct, fuse_operations.symlink.allocate(this::symlink, fuseScope).address());
			case TRUNCATE -> fuse_operations.truncate$set(struct, fuse_operations.truncate.allocate(this::truncate, fuseScope).address());
			case UNLINK -> fuse_operations.unlink$set(struct, fuse_operations.unlink.allocate(this::unlink, fuseScope).address());
			case UTIMENS -> fuse_operations.utimens$set(struct, fuse_operations.utimens.allocate(this::utimens, fuseScope).address());
			case WRITE -> fuse_operations.write$set(struct, fuse_operations.write.allocate(this::write, fuseScope).address());
		}
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemoryAddress path, int mode) {
		return delegate.chmod(path.getUtf8String(0), mode);
	}

	private int create(MemoryAddress path, int mode, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.create(path.getUtf8String(0), mode, new FileInfoImpl(fi, scope));
		}
	}

	private void destroy(MemoryAddress addr) {
		delegate.destroy();
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.getattr(path.getUtf8String(0), new StatImpl(stat, scope));
		}
	}

	private int mkdir(MemoryAddress path, int mode) {
		return delegate.mkdir(path.getUtf8String(0), mode);
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.open(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.opendir(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.read(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, scope), offset, new FileInfoImpl(fi, scope));
		}
	}

	private int readlink(MemoryAddress path, MemoryAddress buf, long len) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, len, scope).asByteBuffer();
			return delegate.readlink(path.getUtf8String(0), buffer, len);
		}
	}

	private int release(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.release(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.releasedir(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int rename(MemoryAddress oldpath, MemoryAddress newpath) {
		return delegate.rename(oldpath.getUtf8String(0), newpath.getUtf8String(0));
	}

	private int rmdir(MemoryAddress path) {
		return delegate.rmdir(path.getUtf8String(0));
	}

	private int statfs(MemoryAddress path, MemoryAddress statvfs) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.statfs(path.getUtf8String(0), new StatvfsImpl(statvfs, scope));
		}
	}

	private int symlink(MemoryAddress linkname, MemoryAddress target) {
		return delegate.symlink(linkname.getUtf8String(0), target.getUtf8String(0));
	}

	private int truncate(MemoryAddress path, long size) {
		return delegate.truncate(path.getUtf8String(0), size);
	}

	private int unlink(MemoryAddress path) {
		return delegate.unlink(path.getUtf8String(0));
	}

	private int utimens(MemoryAddress path, MemoryAddress times) {
		if (MemoryAddress.NULL.equals(times)) {
			// set both times to current time (using on-heap memory segments)
			var segment = MemorySegment.ofByteBuffer(ByteBuffer.allocate((int) timespec.$LAYOUT().byteSize()));
			timespec.tv_sec$set(segment, 0);
			timespec.tv_nsec$set(segment, stat_h.UTIME_NOW());
			var time = new TimeSpecImpl(segment);
			return delegate.utimens(path.getUtf8String(0), time, time);
		} else {
			try (var scope = ResourceScope.newConfinedScope()) {
				var seq = MemoryLayout.sequenceLayout(2, timespec.$LAYOUT());
				var segment = MemorySegment.ofAddress(times, seq.byteSize(), scope);
				var time0 = segment.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = segment.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
//				var timeSpecs = segment.elements(seq.elementLayout()).map(MacTimeSpec::new).toArray(MacTimeSpec[]::new);
				return delegate.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1));
			}
		}
	}

	private int write(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, scope));
		}
	}

}
