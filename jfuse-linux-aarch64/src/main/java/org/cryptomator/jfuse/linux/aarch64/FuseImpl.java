package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseArgs;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.FuseSession;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_args;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_h;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_operations;
import org.cryptomator.jfuse.linux.aarch64.extr.ll.fuse_cmdline_opts;
import org.cryptomator.jfuse.linux.aarch64.extr.ll.fuse_lowlevel_h;
import org.cryptomator.jfuse.linux.aarch64.extr.stat_h;
import org.cryptomator.jfuse.linux.aarch64.extr.timespec;
import org.jetbrains.annotations.VisibleForTesting;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FuseImpl extends Fuse {

	private final CompletableFuture<Integer> initialized = new CompletableFuture<>();
	private final FuseOperations delegate;
	private final MemorySegment fuseOps;

	public FuseImpl(FuseOperations fuseOperations) {
		this.fuseOps = fuse_operations.allocate(fuseScope);
		this.delegate = fuseOperations;
		fuse_operations.init$set(fuseOps, fuse_operations.init.allocate(this::init, fuseScope).address());
		fuseOperations.supportedOperations().forEach(this::bind);
	}

	private MemoryAddress init(MemoryAddress conn, MemoryAddress fuseConfig) {
		try (var scope = MemorySession.openConfined()) {
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
	protected FuseArgs parseCmdLine(List<String> args, MemorySession scope) {
		var fuseArgs = fuse_args.allocate(scope);
		var argc = args.size();
		var argv = scope.allocateArray(ValueLayout.ADDRESS, argc + 1);
		for (int i = 0; i < argc; i++) {
			var cString = scope.allocateUtf8String(args.get(i));
			argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
		}
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemoryAddress.NULL);
		fuse_args.argc$set(fuseArgs, argc);
		fuse_args.argv$set(fuseArgs, argv.address());
		fuse_args.allocated$set(fuseArgs, 0);

		var opts = fuse_cmdline_opts.allocate(scope);
		fuse_lowlevel_h.fuse_parse_cmdline(fuseArgs.address(), opts.address());

		System.out.println("args: " + String.join(" ", args));
		{
			var parsedArgc = fuse_args.argc$get(fuseArgs);
			var parsedArgv = fuse_args.argv$get(fuseArgs);
			for (int i = 0; i < parsedArgc; i++) {
				var cString = parsedArgv.getAtIndex(ValueLayout.ADDRESS, i).getUtf8String(0);
				System.out.println("arg[" + i + "]: " + cString);
			}
		}
		return new FuseArgs(fuseArgs, opts);
	}

	@Override
	protected FuseSession mount(FuseArgs args, Path mountPoint) {
		try (var scope = MemorySession.openConfined()) {
			var mountPointStr = scope.allocateUtf8String(mountPoint.toString());
			var fuse = fuse_h.fuse_new(args.args(), fuseOps, fuseOps.byteSize(), MemoryAddress.NULL);
			if (MemoryAddress.NULL.equals(fuse)) {
				// TODO use explicit exception type
				throw new IllegalArgumentException("fuse_new failed");
			}
			var session = fuse_h.fuse_get_session(fuse);
			//var mountPointStr = fuse_cmdline_opts.mountpoint$get(args.opts());
			if (fuse_h.fuse_mount(fuse, mountPointStr) != 0) {
				// TODO any cleanup needed?
				// TODO use explicit exception type
				throw new IllegalArgumentException("fuse_mount failed for mount point " + mountPoint);
			}
			return new FuseSession(fuse, session);
		}
	}

	@Override
	protected int loop(FuseSession session, boolean multithreaded) {
		// TODO support fuse_loop_mt
		return fuse_h.fuse_loop(session.fuse());
	}

	@Override
	protected void unmount(FuseSession session) {
		fuse_h.fuse_unmount(session.fuse());
	}

	@Override
	protected void destroy(FuseSession session) {
		fuse_h.fuse_destroy(session.fuse());
	}

	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> { /* handled already */ }
			case ACCESS -> fuse_operations.access$set(fuseOps, fuse_operations.access.allocate(this::access, fuseScope).address());
			case CHMOD -> fuse_operations.chmod$set(fuseOps, fuse_operations.chmod.allocate(this::chmod, fuseScope).address());
			case CREATE -> fuse_operations.create$set(fuseOps, fuse_operations.create.allocate(this::create, fuseScope).address());
			case DESTROY -> fuse_operations.destroy$set(fuseOps, fuse_operations.destroy.allocate(this::destroy, fuseScope).address());
			case GET_ATTR -> fuse_operations.getattr$set(fuseOps, fuse_operations.getattr.allocate(this::getattr, fuseScope).address());
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
			case TRUNCATE -> fuse_operations.truncate$set(fuseOps, fuse_operations.truncate.allocate(this::truncate, fuseScope).address());
			case UNLINK -> fuse_operations.unlink$set(fuseOps, fuse_operations.unlink.allocate(this::unlink, fuseScope).address());
			case UTIMENS -> fuse_operations.utimens$set(fuseOps, fuse_operations.utimens.allocate(this::utimens, fuseScope).address());
			case WRITE -> fuse_operations.write$set(fuseOps, fuse_operations.write.allocate(this::write, fuseScope).address());
		}
	}

	private int access(MemoryAddress path, int mask) {
		return delegate.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemoryAddress path, int mode, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.chmod(path.getUtf8String(0), mode, new FileInfoImpl(fi, scope));
		}
	}

	private int create(MemoryAddress path, int mode, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.create(path.getUtf8String(0), mode, new FileInfoImpl(fi, scope));
		}
	}

	private void destroy(MemoryAddress addr) {
		delegate.destroy();
	}

	private int getattr(MemoryAddress path, MemoryAddress stat, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.getattr(path.getUtf8String(0), new StatImpl(stat, scope), new FileInfoImpl(fi, scope));
		}
	}

	private int mkdir(MemoryAddress path, int mode) {
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

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi, int flags) { // TODO: readdir plus
		try (var scope = MemorySession.openConfined()) {
			return delegate.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, scope), offset, new FileInfoImpl(fi, scope));
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

	private int rename(MemoryAddress oldpath, MemoryAddress newpath, int flags) {
		return delegate.rename(oldpath.getUtf8String(0), newpath.getUtf8String(0), flags);
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

	private int truncate(MemoryAddress path, long size, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			return delegate.truncate(path.getUtf8String(0), size, new FileInfoImpl(fi, scope));
		}
	}


	private int unlink(MemoryAddress path) {
		return delegate.unlink(path.getUtf8String(0));
	}

	@VisibleForTesting
	int utimens(MemoryAddress path, MemoryAddress times, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			if (MemoryAddress.NULL.equals(times)) {
				// set both times to current time (using on-heap memory segments)
				var segment = MemorySegment.allocateNative(timespec.$LAYOUT().byteSize(), scope);
				timespec.tv_sec$set(segment, 0);
				timespec.tv_nsec$set(segment, stat_h.UTIME_NOW());
				var time = new TimeSpecImpl(segment);
				return delegate.utimens(path.getUtf8String(0), time, time, new FileInfoImpl(fi, scope));
			} else {
				var seq = MemoryLayout.sequenceLayout(2, timespec.$LAYOUT());
				var segment = MemorySegment.ofAddress(times, seq.byteSize(), scope);
				var time0 = segment.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = segment.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
				return delegate.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), new FileInfoImpl(fi, scope));
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
