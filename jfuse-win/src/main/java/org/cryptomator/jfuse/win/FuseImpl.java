package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.win.extr.fuse2.fuse2_h;
import org.cryptomator.jfuse.win.extr.fuse2.fuse_args;
import org.cryptomator.jfuse.win.extr.fuse3_operations;
import org.cryptomator.jfuse.win.extr.fuse_h;
import org.cryptomator.jfuse.win.extr.fuse_timespec;
import org.jetbrains.annotations.VisibleForTesting;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;
import java.util.List;

import static java.lang.foreign.ValueLayout.JAVA_INT;

class FuseImpl extends Fuse {

	public FuseImpl(FuseOperations fuseOperations) {
		super(fuseOperations, fuse3_operations::allocate);
	}

	@Override
	public synchronized void mount(String progName, Path mountPoint, String... flags) throws FuseMountFailedException {
		var adjustedMP = mountPoint;
		if (mountPoint.equals(mountPoint.getRoot()) && mountPoint.isAbsolute()) {
			//winfsp accepts only drive letters written in drive relative notation
			adjustedMP = Path.of(mountPoint.toString().charAt(0) + ":");
		}
		super.mount(progName, adjustedMP, flags);
	}

	@Override
	protected FuseMount mount(List<String> args) throws FuseMountFailedException {
		var fuseArgs = parseArgs(args);
		var fuse = fuse_h.fuse3_new(fuseArgs.args(), fuseOperationsStruct, fuseOperationsStruct.byteSize(), MemorySegment.NULL);
		if (MemorySegment.NULL.equals(fuse)) {
			throw new FuseMountFailedException("fuse_new failed");
		}
		if (fuse_h.fuse3_mount(fuse, fuseArgs.mountPoint()) != 0) {
			throw new FuseMountFailedException("fuse_mount failed");
		}
		return new FuseMountImpl(fuse, fuseArgs);
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
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemorySegment.NULL);
		fuse_args.argc$set(args, argc);
		fuse_args.argv$set(args, argv);
		fuse_args.allocated$set(args, 0);

		var multithreaded = fuseScope.allocate(JAVA_INT, 1);
		var foreground = fuseScope.allocate(JAVA_INT, 1);
		var mountPointPtr = fuseScope.allocate(ValueLayout.ADDRESS);
		int parseResult = fuse2_h.fuse_parse_cmdline(args, mountPointPtr, multithreaded, foreground); //winfsp pecularity due to unsupportd fuse_lowlevel.h
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		var isMultiThreaded = multithreaded.get(JAVA_INT, 0) == 1;
		var mountPoint = mountPointPtr.get(ValueLayout.ADDRESS.asUnbounded(), 0);
		return new FuseArgs(args, mountPoint, isMultiThreaded);
	}

	@Override
	protected void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse3_operations.init$set(fuseOperationsStruct, fuse3_operations.init.allocate(this::init, fuseScope.scope()));
			case ACCESS -> fuse3_operations.access$set(fuseOperationsStruct, MemorySegment.NULL);
			case CHMOD -> fuse3_operations.chmod$set(fuseOperationsStruct, fuse3_operations.chmod.allocate(this::chmod, fuseScope.scope()));
			case CHOWN -> fuse3_operations.chown$set(fuseOperationsStruct, fuse3_operations.chown.allocate(this::chown, fuseScope.scope()));
			case CREATE -> fuse3_operations.create$set(fuseOperationsStruct, fuse3_operations.create.allocate(this::create, fuseScope.scope()));
			case DESTROY -> fuse3_operations.destroy$set(fuseOperationsStruct, fuse3_operations.destroy.allocate(this::destroy, fuseScope.scope()));
			case FLUSH -> fuse3_operations.flush$set(fuseOperationsStruct, fuse3_operations.flush.allocate(this::flush, fuseScope.scope()));
			case FSYNC -> fuse3_operations.fsync$set(fuseOperationsStruct, fuse3_operations.fsync.allocate(this::fsync, fuseScope.scope()));
			case FSYNCDIR -> fuse3_operations.fsyncdir$set(fuseOperationsStruct, fuse3_operations.fsyncdir.allocate(this::fsyncdir, fuseScope.scope()));
			case GET_ATTR -> fuse3_operations.getattr$set(fuseOperationsStruct, fuse3_operations.getattr.allocate(this::getattr, fuseScope.scope()));
			case GET_XATTR -> fuse3_operations.getxattr$set(fuseOperationsStruct, fuse3_operations.getxattr.allocate(this::getxattr, fuseScope.scope()));
			case LIST_XATTR -> fuse3_operations.listxattr$set(fuseOperationsStruct, fuse3_operations.listxattr.allocate(this::listxattr, fuseScope.scope()));
			case MKDIR -> fuse3_operations.mkdir$set(fuseOperationsStruct, fuse3_operations.mkdir.allocate(this::mkdir, fuseScope.scope()));
			case OPEN -> fuse3_operations.open$set(fuseOperationsStruct, fuse3_operations.open.allocate(this::open, fuseScope.scope()));
			case OPEN_DIR -> fuse3_operations.opendir$set(fuseOperationsStruct, fuse3_operations.opendir.allocate(this::opendir, fuseScope.scope()));
			case READ -> fuse3_operations.read$set(fuseOperationsStruct, fuse3_operations.read.allocate(this::read, fuseScope.scope()));
			case READ_DIR -> fuse3_operations.readdir$set(fuseOperationsStruct, fuse3_operations.readdir.allocate(this::readdir, fuseScope.scope()));
			case READLINK -> fuse3_operations.readlink$set(fuseOperationsStruct, fuse3_operations.readlink.allocate(this::readlink, fuseScope.scope()));
			case RELEASE -> fuse3_operations.release$set(fuseOperationsStruct, fuse3_operations.release.allocate(this::release, fuseScope.scope()));
			case RELEASE_DIR -> fuse3_operations.releasedir$set(fuseOperationsStruct, fuse3_operations.releasedir.allocate(this::releasedir, fuseScope.scope()));
			case REMOVE_XATTR -> fuse3_operations.removexattr$set(fuseOperationsStruct, fuse3_operations.removexattr.allocate(this::removexattr, fuseScope.scope()));
			case RENAME -> fuse3_operations.rename$set(fuseOperationsStruct, fuse3_operations.rename.allocate(this::rename, fuseScope.scope()));
			case RMDIR -> fuse3_operations.rmdir$set(fuseOperationsStruct, fuse3_operations.rmdir.allocate(this::rmdir, fuseScope.scope()));
			case SET_XATTR -> fuse3_operations.setxattr$set(fuseOperationsStruct, fuse3_operations.setxattr.allocate(this::setxattr, fuseScope.scope()));
			case STATFS -> fuse3_operations.statfs$set(fuseOperationsStruct, fuse3_operations.statfs.allocate(this::statfs, fuseScope.scope()));
			case SYMLINK -> fuse3_operations.symlink$set(fuseOperationsStruct, fuse3_operations.symlink.allocate(this::symlink, fuseScope.scope()));
			case TRUNCATE -> fuse3_operations.truncate$set(fuseOperationsStruct, fuse3_operations.truncate.allocate(this::truncate, fuseScope.scope()));
			case UNLINK -> fuse3_operations.unlink$set(fuseOperationsStruct, fuse3_operations.unlink.allocate(this::unlink, fuseScope.scope()));
			case UTIMENS -> fuse3_operations.utimens$set(fuseOperationsStruct, fuse3_operations.utimens.allocate(this::utimens, fuseScope.scope()));
			case WRITE -> fuse3_operations.write$set(fuseOperationsStruct, fuse3_operations.write.allocate(this::write, fuseScope.scope()));
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

	@VisibleForTesting
	int getattr(MemorySegment path, MemorySegment stat, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.getattr(path.getUtf8String(0), new StatImpl(stat, arena.scope()), new FileInfoImpl(fi, arena.scope()));
		}
	}

	@VisibleForTesting
	int getxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.getxattr(path.getUtf8String(0), name.getUtf8String(0), value.asSlice(0, size).asByteBuffer());
		}
	}

	@VisibleForTesting
	int setxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size, int flags) {
		try (var arena = Arena.openConfined()) {

			return fuseOperations.setxattr(path.getUtf8String(0), name.getUtf8String(0), value.asSlice(0, size).asByteBuffer(), flags);
		}
	}

	@VisibleForTesting
	int listxattr(MemorySegment path, MemorySegment value, long size) {
		try (var arena = Arena.openConfined()) {
			return fuseOperations.listxattr(path.getUtf8String(0), value.asSlice(0, size).asByteBuffer());
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

	@VisibleForTesting
	int truncate(MemorySegment path, long size, MemorySegment fi) {
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
			// On Windows we know for sure that WinFSP will call this function only with
			// valid times: https://github.com/winfsp/winfsp/discussions/445
			var seq = MemoryLayout.sequenceLayout(2, fuse_timespec.$LAYOUT());
			var segment = MemorySegment.ofAddress(times.address(), seq.byteSize(), arena.scope());
			var time0 = segment.asSlice(0, fuse_timespec.$LAYOUT().byteSize());
			var time1 = segment.asSlice(fuse_timespec.$LAYOUT().byteSize(), fuse_timespec.$LAYOUT().byteSize());
			return fuseOperations.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), new FileInfoImpl(fi, arena.scope()));
		}
	}

	private int write(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.openConfined()) {
			var buffer = MemorySegment.ofAddress(buf.address(), size, arena.scope()).asByteBuffer();
			return fuseOperations.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena.scope()));
		}
	}

}
