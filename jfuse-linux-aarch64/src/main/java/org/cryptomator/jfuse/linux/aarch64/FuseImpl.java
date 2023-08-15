package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_args;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3_lowlevel.fuse_cmdline_opts;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_h;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_operations;
import org.cryptomator.jfuse.linux.aarch64.extr.stat.stat_h;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.timespec;
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
			case INIT -> fuse_operations.init$set(fuseOperationsStruct, fuse_operations.init.allocate(this::init, fuseArena));
			case ACCESS -> fuse_operations.access$set(fuseOperationsStruct, fuse_operations.access.allocate(this::access, fuseArena));
			case CHMOD -> fuse_operations.chmod$set(fuseOperationsStruct, fuse_operations.chmod.allocate(this::chmod, fuseArena));
			case CHOWN -> fuse_operations.chown$set(fuseOperationsStruct, fuse_operations.chown.allocate(this::chown, fuseArena));
			case CREATE -> fuse_operations.create$set(fuseOperationsStruct, fuse_operations.create.allocate(this::create, fuseArena));
			case DESTROY -> fuse_operations.destroy$set(fuseOperationsStruct, fuse_operations.destroy.allocate(this::destroy, fuseArena));
			case FLUSH -> fuse_operations.flush$set(fuseOperationsStruct, fuse_operations.flush.allocate(this::flush, fuseArena));
			case FSYNC -> fuse_operations.fsync$set(fuseOperationsStruct, fuse_operations.fsync.allocate(this::fsync, fuseArena));
			case FSYNCDIR -> fuse_operations.fsyncdir$set(fuseOperationsStruct, fuse_operations.fsyncdir.allocate(this::fsyncdir, fuseArena));
			case GET_ATTR -> fuse_operations.getattr$set(fuseOperationsStruct, fuse_operations.getattr.allocate(this::getattr, fuseArena));
			case GET_XATTR -> fuse_operations.getxattr$set(fuseOperationsStruct, fuse_operations.getxattr.allocate(this::getxattr, fuseArena));
			case LIST_XATTR -> fuse_operations.listxattr$set(fuseOperationsStruct, fuse_operations.listxattr.allocate(this::listxattr, fuseArena));
			case MKDIR -> fuse_operations.mkdir$set(fuseOperationsStruct, fuse_operations.mkdir.allocate(this::mkdir, fuseArena));
			case OPEN -> fuse_operations.open$set(fuseOperationsStruct, fuse_operations.open.allocate(this::open, fuseArena));
			case OPEN_DIR -> fuse_operations.opendir$set(fuseOperationsStruct, fuse_operations.opendir.allocate(this::opendir, fuseArena));
			case READ -> fuse_operations.read$set(fuseOperationsStruct, fuse_operations.read.allocate(this::read, fuseArena));
			case READ_DIR -> fuse_operations.readdir$set(fuseOperationsStruct, fuse_operations.readdir.allocate(this::readdir, fuseArena));
			case READLINK -> fuse_operations.readlink$set(fuseOperationsStruct, fuse_operations.readlink.allocate(this::readlink, fuseArena));
			case RELEASE -> fuse_operations.release$set(fuseOperationsStruct, fuse_operations.release.allocate(this::release, fuseArena));
			case RELEASE_DIR -> fuse_operations.releasedir$set(fuseOperationsStruct, fuse_operations.releasedir.allocate(this::releasedir, fuseArena));
			case REMOVE_XATTR -> fuse_operations.removexattr$set(fuseOperationsStruct, fuse_operations.removexattr.allocate(this::removexattr, fuseArena));
			case RENAME -> fuse_operations.rename$set(fuseOperationsStruct, fuse_operations.rename.allocate(this::rename, fuseArena));
			case RMDIR -> fuse_operations.rmdir$set(fuseOperationsStruct, fuse_operations.rmdir.allocate(this::rmdir, fuseArena));
			case SET_XATTR -> fuse_operations.setxattr$set(fuseOperationsStruct, fuse_operations.setxattr.allocate(this::setxattr, fuseArena));
			case STATFS -> fuse_operations.statfs$set(fuseOperationsStruct, fuse_operations.statfs.allocate(this::statfs, fuseArena));
			case SYMLINK -> fuse_operations.symlink$set(fuseOperationsStruct, fuse_operations.symlink.allocate(this::symlink, fuseArena));
			case TRUNCATE -> fuse_operations.truncate$set(fuseOperationsStruct, fuse_operations.truncate.allocate(this::truncate, fuseArena));
			case UNLINK -> fuse_operations.unlink$set(fuseOperationsStruct, fuse_operations.unlink.allocate(this::unlink, fuseArena));
			case UTIMENS -> fuse_operations.utimens$set(fuseOperationsStruct, fuse_operations.utimens.allocate(this::utimens, fuseArena));
			case WRITE -> fuse_operations.write$set(fuseOperationsStruct, fuse_operations.write.allocate(this::write, fuseArena));
		}
	}

	@VisibleForTesting
	MemorySegment init(MemorySegment conn, MemorySegment cfg) {
		try (var arena = Arena.ofConfined()) {
			var connInfo = new FuseConnInfoImpl(conn, arena);
			connInfo.setWant(connInfo.want() | FuseConnInfo.FUSE_CAP_READDIRPLUS);
			var config = new FuseConfigImpl(cfg, arena);
			fuseOperations.init(connInfo, config);
		}
		return MemorySegment.NULL;
	}

	private int access(MemorySegment path, int mask) {
		return fuseOperations.access(path.getUtf8String(0), mask);
	}

	private int chmod(MemorySegment path, int mode, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.chmod(path.getUtf8String(0), mode, new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int chown(MemorySegment path, int uid, int gid, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.chown(path.getUtf8String(0), uid, gid, new FileInfoImpl(fi, arena));
		}
	}

	private int create(MemorySegment path, int mode, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.create(path.getUtf8String(0), mode, new FileInfoImpl(fi, arena));
		}
	}

	private void destroy(MemorySegment addr) {
		fuseOperations.destroy();
	}

	@VisibleForTesting
	int flush(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.flush(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int fsync(MemorySegment path, int datasync, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.fsync(path.getUtf8String(0), datasync, new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int fsyncdir(MemorySegment path, int datasync, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.fsyncdir(path.getUtf8String(0), datasync, new FileInfoImpl(fi, arena));
		}
	}

	private int getattr(MemorySegment path, MemorySegment stat, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.getattr(path.getUtf8String(0), new StatImpl(stat, arena), new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int getxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size) {
		try (var arena = Arena.ofConfined()) {
			var val = value.reinterpret(size, arena, null).asByteBuffer();
			return fuseOperations.getxattr(path.getUtf8String(0), name.getUtf8String(0), val);
		}
	}

	@VisibleForTesting
	int setxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size, int flags) {
		try (var arena = Arena.ofConfined()) {
			var val = value.reinterpret(size, arena, null).asByteBuffer();
			return fuseOperations.setxattr(path.getUtf8String(0), name.getUtf8String(0), val, flags);
		}
	}

	@VisibleForTesting
	int listxattr(MemorySegment path, MemorySegment value, long size) {
		try (var arena = Arena.ofConfined()) {
			var val = value.reinterpret(size, arena, null).asByteBuffer();
			return fuseOperations.listxattr(path.getUtf8String(0), val);
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
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.open(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	private int opendir(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.opendir(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	private int read(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			var buffer = buf.reinterpret(size, arena, null).asByteBuffer();
			return fuseOperations.read(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena));
		}
	}

	private int readdir(MemorySegment path, MemorySegment buf, MemorySegment filler, long offset, MemorySegment fi, int flags) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, arena), offset, new FileInfoImpl(fi, arena), flags);
		}
	}

	private int readlink(MemorySegment path, MemorySegment buf, long len) {
		try (var arena = Arena.ofConfined()) {
			var buffer = buf.reinterpret(len, arena, null).asByteBuffer();
			return fuseOperations.readlink(path.getUtf8String(0), buffer, len);
		}
	}

	private int release(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.release(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	private int releasedir(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.releasedir(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	private int rename(MemorySegment oldpath, MemorySegment newpath, int flags) {
		return fuseOperations.rename(oldpath.getUtf8String(0), newpath.getUtf8String(0), flags);
	}

	private int rmdir(MemorySegment path) {
		return fuseOperations.rmdir(path.getUtf8String(0));
	}

	private int statfs(MemorySegment path, MemorySegment statvfs) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.statfs(path.getUtf8String(0), new StatvfsImpl(statvfs, arena));
		}
	}

	private int symlink(MemorySegment linkname, MemorySegment target) {
		return fuseOperations.symlink(linkname.getUtf8String(0), target.getUtf8String(0));
	}

	private int truncate(MemorySegment path, long size, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.truncate(path.getUtf8String(0), size, new FileInfoImpl(fi, arena));
		}
	}


	private int unlink(MemorySegment path) {
		return fuseOperations.unlink(path.getUtf8String(0));
	}

	@VisibleForTesting
	int utimens(MemorySegment path, MemorySegment times, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			if (MemorySegment.NULL.equals(times)) {
				// set both times to current time
				var segment = arena.allocate(timespec.$LAYOUT());
				timespec.tv_sec$set(segment, 0);
				timespec.tv_nsec$set(segment, stat_h.UTIME_NOW());
				var time = new TimeSpecImpl(segment);
				return fuseOperations.utimens(path.getUtf8String(0), time, time, new FileInfoImpl(fi, arena));
			} else {
				var time0 = times.asSlice(0, timespec.$LAYOUT().byteSize());
				var time1 = times.asSlice(timespec.$LAYOUT().byteSize(), timespec.$LAYOUT().byteSize());
				return fuseOperations.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), new FileInfoImpl(fi, arena));
			}
		}
	}

	private int write(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			var buffer = buf.reinterpret(size, arena, null).asByteBuffer();
			return fuseOperations.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena));
		}
	}

}
