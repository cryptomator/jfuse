package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_args;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_cmdline_opts;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_operations;
import org.cryptomator.jfuse.linux.amd64.extr.stat_h;
import org.cryptomator.jfuse.linux.amd64.extr.timespec;
import org.jetbrains.annotations.VisibleForTesting;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;

final class FuseImpl extends Fuse {

	public FuseImpl(FuseOperations fuseOperations) {
		super(fuseOperations, fuse_operations::allocate);
	}

	@Override
	protected FuseMount mount(List<String> args) throws FuseMountFailedException {
		var fuseArgs = parseArgs(args);
		var fuse = fuse_h.fuse_new(fuseArgs.args(), fuseOperationsStruct, fuseOperationsStruct.byteSize(), MemorySegment.NULL);
		if (MemorySegment.NULL.equals(fuse)) {
			throw new FuseMountFailedException("fuse_new failed");
		}
		if (fuse_h.fuse_mount(fuse, fuseArgs.mountPoint()) != 0) {
			throw new FuseMountFailedException("fuse_mount failed");
		}
		return new FuseMountImpl(fuse, fuseArgs);
	}

	@VisibleForTesting
	FuseArgs parseArgs(List<String> cmdLineArgs) throws IllegalArgumentException {
		var args = fuse_args.allocate(fuseArena);
		var argc = cmdLineArgs.size();
		var argv = fuseArena.allocateArray(ValueLayout.ADDRESS, argc + 1L);
		for (int i = 0; i < argc; i++) {
			var cString = fuseArena.allocateUtf8String(cmdLineArgs.get(i));
			argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
		}
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemorySegment.NULL);
		fuse_args.argc$set(args, argc);
		fuse_args.argv$set(args, argv);
		fuse_args.allocated$set(args, 0);

		var opts = fuse_cmdline_opts.allocate(fuseArena);
		int parseResult = FuseFunctions.fuse_parse_cmdline(args, opts);
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		if (fuse_cmdline_opts.show_help$get(opts) == 1) {
			fuse_h.fuse_lib_help(args);
			throw new IllegalArgumentException("Flags contained -h or --help. Processing cancelled after printing help");
		}
		return new FuseArgs(args, opts);
	}

	@Override
	protected void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse_operations.init$set(fuseOperationsStruct, fuse_operations.init.allocate(this::init, fuseArena.scope()));
			case ACCESS -> fuse_operations.access$set(fuseOperationsStruct, fuse_operations.access.allocate(this::access, fuseArena.scope()));
			case CHMOD -> fuse_operations.chmod$set(fuseOperationsStruct, fuse_operations.chmod.allocate(this::chmod, fuseArena.scope()));
			case CHOWN -> fuse_operations.chown$set(fuseOperationsStruct, fuse_operations.chown.allocate(this::chown, fuseArena.scope()));
			case CREATE -> fuse_operations.create$set(fuseOperationsStruct, fuse_operations.create.allocate(this::create, fuseArena.scope()));
			case DESTROY -> fuse_operations.destroy$set(fuseOperationsStruct, fuse_operations.destroy.allocate(this::destroy, fuseArena.scope()));
			case FLUSH -> fuse_operations.flush$set(fuseOperationsStruct, fuse_operations.flush.allocate(this::flush, fuseArena.scope()));
			case FSYNC -> fuse_operations.fsync$set(fuseOperationsStruct, fuse_operations.fsync.allocate(this::fsync, fuseArena.scope()));
			case FSYNCDIR -> fuse_operations.fsyncdir$set(fuseOperationsStruct, fuse_operations.fsyncdir.allocate(this::fsyncdir, fuseArena.scope()));
			case GET_ATTR -> fuse_operations.getattr$set(fuseOperationsStruct, fuse_operations.getattr.allocate(this::getattr, fuseArena.scope()));
			case GET_XATTR -> fuse_operations.getxattr$set(fuseOperationsStruct, fuse_operations.getxattr.allocate(this::getxattr, fuseArena.scope()));
			case LIST_XATTR -> fuse_operations.listxattr$set(fuseOperationsStruct, fuse_operations.listxattr.allocate(this::listxattr, fuseArena.scope()));
			case MKDIR -> fuse_operations.mkdir$set(fuseOperationsStruct, fuse_operations.mkdir.allocate(this::mkdir, fuseArena.scope()));
			case OPEN -> fuse_operations.open$set(fuseOperationsStruct, fuse_operations.open.allocate(this::open, fuseArena.scope()));
			case OPEN_DIR -> fuse_operations.opendir$set(fuseOperationsStruct, fuse_operations.opendir.allocate(this::opendir, fuseArena.scope()));
			case READ -> fuse_operations.read$set(fuseOperationsStruct, fuse_operations.read.allocate(this::read, fuseArena.scope()));
			case READ_DIR -> fuse_operations.readdir$set(fuseOperationsStruct, fuse_operations.readdir.allocate(this::readdir, fuseArena.scope()));
			case READLINK -> fuse_operations.readlink$set(fuseOperationsStruct, fuse_operations.readlink.allocate(this::readlink, fuseArena.scope()));
			case RELEASE -> fuse_operations.release$set(fuseOperationsStruct, fuse_operations.release.allocate(this::release, fuseArena.scope()));
			case RELEASE_DIR -> fuse_operations.releasedir$set(fuseOperationsStruct, fuse_operations.releasedir.allocate(this::releasedir, fuseArena.scope()));
			case REMOVE_XATTR -> fuse_operations.removexattr$set(fuseOperationsStruct, fuse_operations.removexattr.allocate(this::removexattr, fuseArena.scope()));
			case RENAME -> fuse_operations.rename$set(fuseOperationsStruct, fuse_operations.rename.allocate(this::rename, fuseArena.scope()));
			case RMDIR -> fuse_operations.rmdir$set(fuseOperationsStruct, fuse_operations.rmdir.allocate(this::rmdir, fuseArena.scope()));
			case SET_XATTR -> fuse_operations.setxattr$set(fuseOperationsStruct, fuse_operations.setxattr.allocate(this::setxattr, fuseArena.scope()));
			case STATFS -> fuse_operations.statfs$set(fuseOperationsStruct, fuse_operations.statfs.allocate(this::statfs, fuseArena.scope()));
			case SYMLINK -> fuse_operations.symlink$set(fuseOperationsStruct, fuse_operations.symlink.allocate(this::symlink, fuseArena.scope()));
			case TRUNCATE -> fuse_operations.truncate$set(fuseOperationsStruct, fuse_operations.truncate.allocate(this::truncate, fuseArena.scope()));
			case UNLINK -> fuse_operations.unlink$set(fuseOperationsStruct, fuse_operations.unlink.allocate(this::unlink, fuseArena.scope()));
			case UTIMENS -> fuse_operations.utimens$set(fuseOperationsStruct, fuse_operations.utimens.allocate(this::utimens, fuseArena.scope()));
			case WRITE -> fuse_operations.write$set(fuseOperationsStruct, fuse_operations.write.allocate(this::write, fuseArena.scope()));
		}
	}

	@VisibleForTesting
	MemorySegment init(MemorySegment conn, MemorySegment cfg) {
		try (var arena = Arena.openConfined()) {
			var connInfo = new FuseConnInfoImpl(conn, arena.scope());
			connInfo.setWant(connInfo.want() | FuseConnInfo.FUSE_CAP_READDIRPLUS);
			var config = new FuseConfigImpl(cfg, arena.scope());
			fuseOperations.init(connInfo, config);
		}
		return MemorySegment.NULL;
	}

	private int access(MemorySegment path, int mask) {
		return fuseOperations.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemorySegment path, int mode, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.chmod(path.getUtf8String(0), mode, new FileInfoImpl(fi, arena.scope()));
		}
	}

	@VisibleForTesting
	int chown(MemorySegment path, int uid, int gid, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.chown(path.getUtf8String(0), uid, gid, new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int create(MemorySegment path, int mode, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.create(path.getUtf8String(0), mode, new FileInfoImpl(fi, arena.scope()));
		}
	}

	private void destroy(MemorySegment addr) {
		fuseOperations.destroy();
	}

	@VisibleForTesting
	int flush(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.flush(path.getUtf8String(0), new FileInfoImpl(fi, arena.scope()));
		}
	}

	@VisibleForTesting
	int fsync(MemorySegment path, int datasync, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.fsync(path.getUtf8String(0), datasync, new FileInfoImpl(fi, arena.scope()));
		}
	}

	@VisibleForTesting
	int fsyncdir(MemorySegment path, int datasync, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.fsyncdir(path.getUtf8String(0), datasync, new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int getattr(MemorySegment path, MemorySegment stat, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.getattr(path.getUtf8String(0), new StatImpl(stat, arena.scope()), new FileInfoImpl(fi, arena.scope()));
		}
	}

	@VisibleForTesting
	int getxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(value.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.getxattr(path.getUtf8String(0), name.getUtf8String(0), buffer);
		}
	}

	@VisibleForTesting
	int setxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size, int flags) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(value.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.setxattr(path.getUtf8String(0), name.getUtf8String(0), buffer, flags);
		}
	}

	@VisibleForTesting
	int listxattr(MemorySegment path, MemorySegment value, long size) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(value.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.listxattr(path.getUtf8String(0), buffer);
		}
	}

	@VisibleForTesting
	int removexattr(MemorySegment path, MemorySegment name) {
		return fuseOperations.removexattr(path.getUtf8String(0), name.getUtf8String(0));
	}

	private int mkdir(MemorySegment path, int mode) {
		return fuseOperations.mkdir(path.getUtf8String(0), mode);
	}

	private int open(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.open(path.getUtf8String(0), new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int opendir(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.opendir(path.getUtf8String(0), new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int read(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.read(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int readdir(MemorySegment path, MemorySegment buf, MemorySegment filler, long offset, MemorySegment fi, int flags) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, arena), offset, new FileInfoImpl(fi, arena.scope()), flags);
		}
	}

	private int readlink(MemorySegment path, MemorySegment buf, long len) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf.address(), len, arena.scope()).asByteBuffer();
			return fuseOperations.readlink(path.getUtf8String(0), buffer, len);
		}
	}

	private int release(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.release(path.getUtf8String(0), new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int releasedir(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.releasedir(path.getUtf8String(0), new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int rename(MemorySegment oldpath, MemorySegment newpath, int flags) {
		return fuseOperations.rename(oldpath.getUtf8String(0), newpath.getUtf8String(0), flags);
	}

	private int rmdir(MemorySegment path) {
		return fuseOperations.rmdir(path.getUtf8String(0));
	}

	private int statfs(MemorySegment path, MemorySegment statvfs) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.statfs(path.getUtf8String(0), new StatvfsImpl(statvfs, arena.scope()));
		}
	}

	private int symlink(MemorySegment linkname, MemorySegment target) {
		return fuseOperations.symlink(linkname.getUtf8String(0), target.getUtf8String(0));
	}

	private int truncate(MemorySegment path, long size, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.truncate(path.getUtf8String(0), size, new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int unlink(MemorySegment path) {
		return fuseOperations.unlink(path.getUtf8String(0));
	}

	@VisibleForTesting
	int utimens(MemorySegment path, MemorySegment times, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			if (MemorySegment.NULL.equals(times)) {
				// set both times to current time (using on-heap memory segments)
				var segment = MemorySegment.allocateNative(timespec.$LAYOUT().byteSize(), arena.scope());
				timespec.tv_sec$set(segment, 0);
				timespec.tv_nsec$set(segment, stat_h.UTIME_NOW());
				var time = new TimeSpecImpl(segment);
				return fuseOperations.utimens(path.getUtf8String(0), time, time, new FileInfoImpl(fi, arena.scope()));
			} else {
				var time0 = times.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = times.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
				return fuseOperations.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), new FileInfoImpl(fi, arena.scope()));
			}
		}
	}

	private int write(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena.scope()));
		}
	}

}