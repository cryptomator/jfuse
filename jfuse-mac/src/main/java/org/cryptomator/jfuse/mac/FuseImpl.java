package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.MountFailedException;
import org.cryptomator.jfuse.mac.extr.fuse_args;
import org.cryptomator.jfuse.mac.extr.fuse_h;
import org.cryptomator.jfuse.mac.extr.fuse_operations;
import org.cryptomator.jfuse.mac.extr.stat_h;
import org.cryptomator.jfuse.mac.extr.timespec;
import org.jetbrains.annotations.VisibleForTesting;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.util.List;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class FuseImpl extends Fuse {

	private final FuseOperations delegate;
	private final MemorySegment fuseOps;

	public FuseImpl(FuseOperations fuseOperations) {
		this.fuseOps = fuse_operations.allocate(fuseScope);
		this.delegate = fuseOperations;
		fuseOperations.supportedOperations().forEach(this::bind);
	}

	@Override
	protected FuseMount mount(List<String> args) throws MountFailedException {
		var fuseArgs = parseArgs(args);
		var ch  = fuse_h.fuse_mount(fuseArgs.mountPoint(), fuseArgs.args());
		if (MemoryAddress.NULL.equals(ch)) {
			throw new MountFailedException("fuse_mount failed");
		}
		var fuse = fuse_h.fuse_new(ch, fuseArgs.args(), fuseOps, fuseOps.byteSize(), MemoryAddress.NULL);
		if (MemoryAddress.NULL.equals(fuse)) {
			fuse_h.fuse_unmount(fuseArgs.mountPoint(), ch);
			throw new MountFailedException("fuse_new failed");
		}
		return new FuseMountImpl(fuse, ch, fuseArgs);
	}

	@VisibleForTesting
	FuseArgs parseArgs(List<String> cmdLineArgs) throws IllegalArgumentException {
		var args = fuse_args.allocate(fuseScope);
		var argc = cmdLineArgs.size();
		var argv = fuseScope.allocateArray(ValueLayout.ADDRESS, argc + 1L);
		for (int i = 0; i < argc; i++) {
			var cString = fuseScope.allocateUtf8String(cmdLineArgs.get(i));
			argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
		}
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemoryAddress.NULL);
		fuse_args.argc$set(args, argc);
		fuse_args.argv$set(args, argv.address());
		fuse_args.allocated$set(args, 0);

		var multithreaded = fuseScope.allocate(JAVA_INT, 1);
		var foreground = fuseScope.allocate(JAVA_INT, 1);
		var mountPointPtr = fuseScope.allocate(ValueLayout.ADDRESS);
		int parseResult = fuse_h.fuse_parse_cmdline(args, mountPointPtr, multithreaded, foreground);
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		var isMultiThreaded = multithreaded.get(JAVA_INT, 0) == 1;
		var mountPoint = mountPointPtr.get(ValueLayout.ADDRESS, 0);
		return new FuseArgs(args, mountPoint, isMultiThreaded);
	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse_operations.init$set(fuseOps, fuse_operations.init.allocate(this::init, fuseScope).address());
			case ACCESS -> fuse_operations.access$set(fuseOps, fuse_operations.access.allocate(this::access, fuseScope).address());
			case CHMOD -> fuse_operations.chmod$set(fuseOps, fuse_operations.chmod.allocate(this::chmod, fuseScope).address());
			case CHOWN -> fuse_operations.chown$set(fuseOps, fuse_operations.chown.allocate(this::chown, fuseScope).address());
			case CREATE -> fuse_operations.create$set(fuseOps, fuse_operations.create.allocate(this::create, fuseScope).address());
			case DESTROY -> fuse_operations.destroy$set(fuseOps, fuse_operations.destroy.allocate(this::destroy, fuseScope).address());
			case GET_ATTR -> {
				fuse_operations.getattr$set(fuseOps, fuse_operations.getattr.allocate(this::getattr, fuseScope).address());
				fuse_operations.fgetattr$set(fuseOps, fuse_operations.fgetattr.allocate(this::fgetattr, fuseScope).address());
			}
			case MKDIR -> fuse_operations.mkdir$set(fuseOps, fuse_operations.mkdir.allocate(this::mkdir, fuseScope).address());
			case OPEN -> fuse_operations.open$set(fuseOps, fuse_operations.open.allocate(this::open, fuseScope).address());
			case OPEN_DIR -> fuse_operations.opendir$set(fuseOps, fuse_operations.opendir.allocate(this::opendir, fuseScope).address());
			case READ -> fuse_operations.read$set(fuseOps, fuse_operations.read.allocate(this::read, fuseScope).address());
			case READ_DIR -> fuse_operations.readdir$set(fuseOps, fuse_operations.readdir.allocate(this::readdir, fuseScope).address());
			case READLINK -> fuse_operations.readlink$set(fuseOps, fuse_operations.readlink.allocate(this::readlink, fuseScope).address());
			case RELEASE -> fuse_operations.release$set(fuseOps, fuse_operations.release.allocate(this::release, fuseScope).address());
			case RELEASE_DIR -> fuse_operations.releasedir$set(fuseOps, fuse_operations.releasedir.allocate(this::releasedir, fuseScope).address());
			case RENAME -> fuse_operations.rename$set(fuseOps, fuse_operations.rename.allocate(this::rename, fuseScope).address());
			case RMDIR -> fuse_operations.rmdir$set(fuseOps, fuse_operations.rmdir.allocate(this::rmdir, fuseScope).address());
			case STATFS -> fuse_operations.statfs$set(fuseOps, fuse_operations.statfs.allocate(this::statfs, fuseScope).address());
			case SYMLINK -> fuse_operations.symlink$set(fuseOps, fuse_operations.symlink.allocate(this::symlink, fuseScope).address());
			case TRUNCATE -> {
				fuse_operations.truncate$set(fuseOps, fuse_operations.truncate.allocate(this::truncate, fuseScope).address());
				fuse_operations.ftruncate$set(fuseOps, fuse_operations.ftruncate.allocate(this::ftruncate, fuseScope).address());
			}
			case UNLINK -> fuse_operations.unlink$set(fuseOps, fuse_operations.unlink.allocate(this::unlink, fuseScope).address());
			case UTIMENS -> fuse_operations.utimens$set(fuseOps, fuse_operations.utimens.allocate(this::utimens, fuseScope).address());
			case WRITE -> fuse_operations.write$set(fuseOps, fuse_operations.write.allocate(this::write, fuseScope).address());
		}
	}

	private Addressable init(MemoryAddress conn) {
		try (var scope = MemorySession.openConfined()) {
			delegate.init(new FuseConnInfoImpl(conn, scope), null);
		}
		return MemoryAddress.NULL;
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemoryAddress path, short mode) {
		return delegate.chmod(path.getUtf8String(0), mode, null);
	}

	@VisibleForTesting
	int chown(MemoryAddress path, int uid, int gid) {
		return delegate.chown(path.getUtf8String(0), uid, gid, null);
	}

	private int create(MemoryAddress path, short mode, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.create(path.getUtf8String(0), mode, new FileInfoImpl(fi, scope));
		}
	}

	private void destroy(MemoryAddress addr) {
		delegate.destroy();
	}

	@VisibleForTesting
	int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.getattr(path.getUtf8String(0), new StatImpl(stat, scope), null);
		}
	}

	@VisibleForTesting
	int fgetattr(MemoryAddress path, MemoryAddress stat, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.getattr(path.getUtf8String(0), new StatImpl(stat, scope), new FileInfoImpl(fi, scope));
		}
	}

	private int mkdir(MemoryAddress path, short mode) {
		return delegate.mkdir(path.getUtf8String(0), mode);
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.open(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.opendir(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.read(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, scope));
		}
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, scope), offset, new FileInfoImpl(fi, scope), 0);
		}
	}

	private int readlink(MemoryAddress path, MemoryAddress buf, long len) {
		try (var scope = MemorySession.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf, len, scope).asByteBuffer();
			return delegate.readlink(path.getUtf8String(0), buffer, len);
		}
	}

	private int release(MemoryAddress path, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.release(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.releasedir(path.getUtf8String(0), new FileInfoImpl(fi, scope));
		}
	}

	private int rename(MemoryAddress oldpath, MemoryAddress newpath) {
		return delegate.rename(oldpath.getUtf8String(0), newpath.getUtf8String(0), 0);
	}

	private int rmdir(MemoryAddress path) {
		return delegate.rmdir(path.getUtf8String(0));
	}

	private int statfs(MemoryAddress path, MemoryAddress statvfs) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.statfs(path.getUtf8String(0), new StatvfsImpl(statvfs, scope));
		}
	}

	private int symlink(MemoryAddress linkname, MemoryAddress target) {
		return delegate.symlink(linkname.getUtf8String(0), target.getUtf8String(0));
	}

	@VisibleForTesting
	int truncate(MemoryAddress path, long size) {
		return delegate.truncate(path.getUtf8String(0), size, null);
	}

	@VisibleForTesting
	int ftruncate(MemoryAddress path, long size, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.truncate(path.getUtf8String(0), size, new FileInfoImpl(fi, scope));
		}
	}

	private int unlink(MemoryAddress path) {
		return delegate.unlink(path.getUtf8String(0));
	}

	@VisibleForTesting
	int utimens(MemoryAddress path, MemoryAddress times) {
		try (var scope = MemorySession.openConfined()) {
			if (MemoryAddress.NULL.equals(times)) {
				// set both times to current time (using on-heap memory segments)
				var segment = MemorySegment.allocateNative(timespec.$LAYOUT().byteSize(), scope);
				timespec.tv_sec$set(segment, 0);
				timespec.tv_nsec$set(segment, stat_h.UTIME_NOW());
				var time = new TimeSpecImpl(segment);
				return delegate.utimens(path.getUtf8String(0), time, time, null);
			} else {
				var seq = MemoryLayout.sequenceLayout(2, timespec.$LAYOUT());
				var segment = MemorySegment.ofAddress(times, seq.byteSize(), scope);
				var time0 = segment.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = segment.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
				return delegate.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), null);
			}
		}
	}

	private int write(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf, size, scope).asByteBuffer();
			return delegate.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, scope));
		}
	}

}
