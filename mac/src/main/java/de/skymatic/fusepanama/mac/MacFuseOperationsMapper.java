package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.mac.lowlevel.fuse_operations;
import de.skymatic.fusepanama.mac.lowlevel.stat_h;
import de.skymatic.fusepanama.mac.lowlevel.timespec;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

class MacFuseOperationsMapper {

	final CompletableFuture<Integer> initialized = new CompletableFuture<>();
	final MemorySegment struct;
	private final FuseOperations delegate;
	private final ResourceScope scope;

	public MacFuseOperationsMapper(FuseOperations delegate, ResourceScope scope) {
		this.struct = fuse_operations.allocate(scope);
		this.delegate = delegate;
		this.scope = scope;
		fuse_operations.init$set(struct, fuse_operations.init.allocate(this::init, scope).address());
		delegate.supportedOperations().forEach(this::bind);
	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> { /* handled already */ }
			case ACCESS -> fuse_operations.access$set(struct, fuse_operations.access.allocate(this::access, scope).address());
			case CHMOD -> fuse_operations.chmod$set(struct, fuse_operations.chmod.allocate(this::chmod, scope).address());
			case CREATE -> fuse_operations.create$set(struct, fuse_operations.create.allocate(this::create, scope).address());
			case DESTROY -> fuse_operations.destroy$set(struct, fuse_operations.destroy.allocate(this::destroy, scope).address());
			case GET_ATTR -> fuse_operations.getattr$set(struct, fuse_operations.getattr.allocate(this::getattr, scope).address());
			case MKDIR -> fuse_operations.mkdir$set(struct, fuse_operations.mkdir.allocate(this::mkdir, scope).address());
			case OPEN -> fuse_operations.open$set(struct, fuse_operations.open.allocate(this::open, scope).address());
			case OPEN_DIR -> fuse_operations.opendir$set(struct, fuse_operations.opendir.allocate(this::opendir, scope).address());
			case READ -> fuse_operations.read$set(struct, fuse_operations.read.allocate(this::read, scope).address());
			case READ_DIR -> fuse_operations.readdir$set(struct, fuse_operations.readdir.allocate(this::readdir, scope).address());
			case READLINK -> fuse_operations.readlink$set(struct, fuse_operations.readlink.allocate(this::readlink, scope).address());
			case RELEASE -> fuse_operations.release$set(struct, fuse_operations.release.allocate(this::release, scope).address());
			case RELEASE_DIR -> fuse_operations.releasedir$set(struct, fuse_operations.releasedir.allocate(this::releasedir, scope).address());
			case RENAME -> fuse_operations.rename$set(struct, fuse_operations.rename.allocate(this::rename, scope).address());
			case RMDIR -> fuse_operations.rmdir$set(struct, fuse_operations.rmdir.allocate(this::rmdir, scope).address());
			case STATFS -> fuse_operations.statfs$set(struct, fuse_operations.statfs.allocate(this::statfs, scope).address());
			case SYMLINK -> fuse_operations.symlink$set(struct, fuse_operations.symlink.allocate(this::symlink, scope).address());
			case TRUNCATE -> fuse_operations.truncate$set(struct, fuse_operations.truncate.allocate(this::truncate, scope).address());
			case UNLINK -> fuse_operations.unlink$set(struct, fuse_operations.unlink.allocate(this::unlink, scope).address());
			case UTIMENS -> fuse_operations.utimens$set(struct, fuse_operations.utimens.allocate(this::utimens, scope).address());
			case WRITE -> fuse_operations.write$set(struct, fuse_operations.write.allocate(this::write, scope).address());
		}
	}

	private MemoryAddress init(MemoryAddress conn) {
		try (var scope = ResourceScope.newConfinedScope()) {
			if (delegate.supportedOperations().contains(FuseOperations.Operation.INIT)) {
				delegate.init(new MacFuseConnInfo(conn, scope));
			}
			initialized.complete(0);
		} catch (Exception e) {
			initialized.completeExceptionally(e);
		}
		return MemoryAddress.NULL;
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemoryAddress path, short mode) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.chmod(path.getUtf8String(0), mode);
		}
	}

	private int create(MemoryAddress path, short mode, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.create(path.getUtf8String(0), mode, new MacFileInfo(fi, scope));
		}
	}

	private void destroy(MemoryAddress addr) {
		delegate.destroy();
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.getattr(path.getUtf8String(0), new MacStat(stat, scope));
		}
	}

	private int mkdir(MemoryAddress path, short mode) {
		return delegate.mkdir(path.getUtf8String(0), mode);
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.open(path.getUtf8String(0), new MacFileInfo(fi, scope));
		}
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.opendir(path.getUtf8String(0), new MacFileInfo(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.read(path.getUtf8String(0), buffer, size, offset, new MacFileInfo(fi, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.readdir(path.getUtf8String(0), new MacDirFiller(buf, filler, scope), offset, new MacFileInfo(fi, scope));
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
			return delegate.release(path.getUtf8String(0), new MacFileInfo(fi, scope));
		}
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return delegate.releasedir(path.getUtf8String(0), new MacFileInfo(fi, scope));
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
			return delegate.statfs(path.getUtf8String(0), new MacStatvfs(statvfs, scope));
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
			var time = new MacTimeSpec(segment);
			return delegate.utimens(path.getUtf8String(0), time, time);
		} else {
			try (var scope = ResourceScope.newConfinedScope()) {
				var seq = MemoryLayout.sequenceLayout(2, timespec.$LAYOUT());
				var segment = MemorySegment.ofAddress(times, seq.byteSize(), scope);
				var time0 = segment.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = segment.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
//				var timeSpecs = segment.elements(seq.elementLayout()).map(MacTimeSpec::new).toArray(MacTimeSpec[]::new);
				return delegate.utimens(path.getUtf8String(0), new MacTimeSpec(time0), new MacTimeSpec(time1));
			}
		}
	}

	private int write(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.write(path.getUtf8String(0), buffer, size, offset, new MacFileInfo(fi, scope));
		}
	}

}
