package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.util.MemoryUtils;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_args;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_h;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_operations;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.timespec;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3_lowlevel.fuse_cmdline_opts;
import org.cryptomator.jfuse.linux.aarch64.extr.stat.stat_h;
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
		var argv = fuseArena.allocate(ValueLayout.ADDRESS, argc + 1L);
		for (int i = 0; i < argc; i++) {
			var cString = fuseArena.allocateFrom(cmdLineArgs.get(i));
			argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
		}
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemorySegment.NULL);
		fuse_args.argc(args, argc);
		fuse_args.argv(args, argv);
		fuse_args.allocated(args, 0);

		var opts = fuse_cmdline_opts.allocate(fuseArena);
		int parseResult = FuseFunctions.fuse_parse_cmdline(args, opts);
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		if (fuse_cmdline_opts.show_help(opts) == 1) {
			fuse_h.fuse_lib_help(args);
			throw new IllegalArgumentException("Flags contained -h or --help. Processing cancelled after printing help");
		}
		return new FuseArgs(args, opts);
	}

	@Override
	protected void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse_operations.init(fuseOperationsStruct, fuse_operations.init.allocate(this::init, fuseArena));
			case ACCESS -> fuse_operations.access(fuseOperationsStruct, fuse_operations.access.allocate(this::access, fuseArena));
			case CHMOD -> fuse_operations.chmod(fuseOperationsStruct, fuse_operations.chmod.allocate(this::chmod, fuseArena));
			case CHOWN -> fuse_operations.chown(fuseOperationsStruct, fuse_operations.chown.allocate(this::chown, fuseArena));
			case CREATE -> fuse_operations.create(fuseOperationsStruct, fuse_operations.create.allocate(this::create, fuseArena));
			case DESTROY -> fuse_operations.destroy(fuseOperationsStruct, fuse_operations.destroy.allocate(this::destroy, fuseArena));
			case FLUSH -> fuse_operations.flush(fuseOperationsStruct, fuse_operations.flush.allocate(this::flush, fuseArena));
			case FSYNC -> fuse_operations.fsync(fuseOperationsStruct, fuse_operations.fsync.allocate(this::fsync, fuseArena));
			case FSYNCDIR -> fuse_operations.fsyncdir(fuseOperationsStruct, fuse_operations.fsyncdir.allocate(this::fsyncdir, fuseArena));
			case GET_ATTR -> fuse_operations.getattr(fuseOperationsStruct, fuse_operations.getattr.allocate(this::getattr, fuseArena));
			case GET_XATTR -> fuse_operations.getxattr(fuseOperationsStruct, fuse_operations.getxattr.allocate(this::getxattr, fuseArena));
			case LIST_XATTR -> fuse_operations.listxattr(fuseOperationsStruct, fuse_operations.listxattr.allocate(this::listxattr, fuseArena));
			case MKDIR -> fuse_operations.mkdir(fuseOperationsStruct, fuse_operations.mkdir.allocate(this::mkdir, fuseArena));
			case OPEN -> fuse_operations.open(fuseOperationsStruct, fuse_operations.open.allocate(this::open, fuseArena));
			case OPEN_DIR -> fuse_operations.opendir(fuseOperationsStruct, fuse_operations.opendir.allocate(this::opendir, fuseArena));
			case READ -> fuse_operations.read(fuseOperationsStruct, fuse_operations.read.allocate(this::read, fuseArena));
			case READ_DIR -> fuse_operations.readdir(fuseOperationsStruct, fuse_operations.readdir.allocate(this::readdir, fuseArena));
			case READLINK -> fuse_operations.readlink(fuseOperationsStruct, fuse_operations.readlink.allocate(this::readlink, fuseArena));
			case RELEASE -> fuse_operations.release(fuseOperationsStruct, fuse_operations.release.allocate(this::release, fuseArena));
			case RELEASE_DIR -> fuse_operations.releasedir(fuseOperationsStruct, fuse_operations.releasedir.allocate(this::releasedir, fuseArena));
			case REMOVE_XATTR -> fuse_operations.removexattr(fuseOperationsStruct, fuse_operations.removexattr.allocate(this::removexattr, fuseArena));
			case RENAME -> fuse_operations.rename(fuseOperationsStruct, fuse_operations.rename.allocate(this::rename, fuseArena));
			case RMDIR -> fuse_operations.rmdir(fuseOperationsStruct, fuse_operations.rmdir.allocate(this::rmdir, fuseArena));
			case SET_XATTR -> fuse_operations.setxattr(fuseOperationsStruct, fuse_operations.setxattr.allocate(this::setxattr, fuseArena));
			case STATFS -> fuse_operations.statfs(fuseOperationsStruct, fuse_operations.statfs.allocate(this::statfs, fuseArena));
			case SYMLINK -> fuse_operations.symlink(fuseOperationsStruct, fuse_operations.symlink.allocate(this::symlink, fuseArena));
			case TRUNCATE -> fuse_operations.truncate(fuseOperationsStruct, fuse_operations.truncate.allocate(this::truncate, fuseArena));
			case UNLINK -> fuse_operations.unlink(fuseOperationsStruct, fuse_operations.unlink.allocate(this::unlink, fuseArena));
			case UTIMENS -> fuse_operations.utimens(fuseOperationsStruct, fuse_operations.utimens.allocate(this::utimens, fuseArena));
			case WRITE -> fuse_operations.write(fuseOperationsStruct, fuse_operations.write.allocate(this::write, fuseArena));
		}
	}

	@VisibleForTesting
	MemorySegment init(MemorySegment conn, MemorySegment cfg) {
		var connInfo = new FuseConnInfoImpl(conn);
		connInfo.setWant(connInfo.want() | FuseConnInfo.FUSE_CAP_READDIRPLUS);
		var config = new FuseConfigImpl(cfg);
		fuseOperations.init(connInfo, config);
		return MemorySegment.NULL;
	}

	private int access(MemorySegment path, int mask) {
		return fuseOperations.access(path.getString(0), mask);
	}

	private int chmod(MemorySegment path, int mode, MemorySegment fi) {
		return fuseOperations.chmod(path.getString(0), mode, FileInfoImpl.ofNullable(fi));
	}

	@VisibleForTesting
	int chown(MemorySegment path, int uid, int gid, MemorySegment fi) {
		return fuseOperations.chown(path.getString(0), uid, gid, FileInfoImpl.ofNullable(fi));
	}

	private int create(MemorySegment path, int mode, MemorySegment fi) {
		return fuseOperations.create(path.getString(0), mode, new FileInfoImpl(fi));
	}

	private void destroy(MemorySegment addr) {
		fuseOperations.destroy();
	}

	@VisibleForTesting
	int flush(MemorySegment path, MemorySegment fi) {
		return fuseOperations.flush(path.getString(0), new FileInfoImpl(fi));
	}

	@VisibleForTesting
	int fsync(MemorySegment path, int datasync, MemorySegment fi) {
		return fuseOperations.fsync(path.getString(0), datasync, new FileInfoImpl(fi));
	}

	@VisibleForTesting
	int fsyncdir(MemorySegment path, int datasync, MemorySegment fi) {
		return fuseOperations.fsyncdir(MemoryUtils.toUtf8StringOrNull(path), datasync, new FileInfoImpl(fi));
	}

	private int getattr(MemorySegment path, MemorySegment stat, MemorySegment fi) {
		return fuseOperations.getattr(path.getString(0), new StatImpl(stat), FileInfoImpl.ofNullable(fi));
	}

	@VisibleForTesting
	int getxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.getxattr(path.getString(0), name.getString(0), val);
	}

	@VisibleForTesting
	int setxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size, int flags) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.setxattr(path.getString(0), name.getString(0), val, flags);
	}

	@VisibleForTesting
	int listxattr(MemorySegment path, MemorySegment value, long size) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.listxattr(path.getString(0), val);
	}

	@VisibleForTesting
	int removexattr(MemorySegment path, MemorySegment name) {
		return fuseOperations.removexattr(path.getString(0), name.getString(0));
	}

	private int mkdir(MemorySegment path, int mode) {
		return fuseOperations.mkdir(path.getString(0), mode);
	}

	private int open(MemorySegment path, MemorySegment fi) {
		return fuseOperations.open(path.getString(0), new FileInfoImpl(fi));
	}

	private int opendir(MemorySegment path, MemorySegment fi) {
		return fuseOperations.opendir(path.getString(0), new FileInfoImpl(fi));
	}

	private int read(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		var buffer = buf.reinterpret(size).asByteBuffer();
		return fuseOperations.read(path.getString(0), buffer, size, offset, new FileInfoImpl(fi));
	}

	private int readdir(MemorySegment path, MemorySegment buf, MemorySegment filler, long offset, MemorySegment fi, int flags) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.readdir(path.getString(0), new DirFillerImpl(buf, filler, arena), offset, new FileInfoImpl(fi), flags);
		}
	}

	private int readlink(MemorySegment path, MemorySegment buf, long len) {
		var buffer = buf.reinterpret(len).asByteBuffer();
		return fuseOperations.readlink(path.getString(0), buffer, len);
	}

	private int release(MemorySegment path, MemorySegment fi) {
		return fuseOperations.release(path.getString(0), new FileInfoImpl(fi));

	}

	private int releasedir(MemorySegment path, MemorySegment fi) {
		return fuseOperations.releasedir(MemoryUtils.toUtf8StringOrNull(path), new FileInfoImpl(fi));
	}

	private int rename(MemorySegment oldpath, MemorySegment newpath, int flags) {
		return fuseOperations.rename(oldpath.getString(0), newpath.getString(0), flags);
	}

	private int rmdir(MemorySegment path) {
		return fuseOperations.rmdir(path.getString(0));
	}

	private int statfs(MemorySegment path, MemorySegment statvfs) {
		return fuseOperations.statfs(path.getString(0), new StatvfsImpl(statvfs));
	}

	private int symlink(MemorySegment linkname, MemorySegment target) {
		return fuseOperations.symlink(linkname.getString(0), target.getString(0));
	}

	private int truncate(MemorySegment path, long size, MemorySegment fi) {
		return fuseOperations.truncate(path.getString(0), size, FileInfoImpl.ofNullable(fi));
	}


	private int unlink(MemorySegment path) {
		return fuseOperations.unlink(path.getString(0));
	}

	@VisibleForTesting
	int utimens(MemorySegment path, MemorySegment times, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			if (MemorySegment.NULL.equals(times)) {
				// set both times to current time
				var segment = timespec.allocate(arena);
				timespec.tv_sec(segment, 0);
				timespec.tv_nsec(segment, stat_h.UTIME_NOW());
				var time = new TimeSpecImpl(segment);
				return fuseOperations.utimens(path.getString(0), time, time, FileInfoImpl.ofNullable(fi));
			} else {
				var time0 = timespec.asSlice(times, 0);
				var time1 = timespec.asSlice(times, 1);
				return fuseOperations.utimens(path.getString(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), FileInfoImpl.ofNullable(fi));
			}
		}
	}

	private int write(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		var buffer = buf.reinterpret(size).asByteBuffer();
		return fuseOperations.write(path.getString(0), buffer, size, offset, new FileInfoImpl(fi));
	}

}
