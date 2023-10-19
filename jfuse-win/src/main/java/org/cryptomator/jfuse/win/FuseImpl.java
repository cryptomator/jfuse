package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.util.MemoryUtils;
import org.cryptomator.jfuse.win.extr.fuse2.fuse2_h;
import org.cryptomator.jfuse.win.extr.fuse2.fuse_args;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_operations;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_h;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_timespec;
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

		var multithreaded = fuseArena.allocate(JAVA_INT, 1);
		var foreground = fuseArena.allocate(JAVA_INT, 1);
		var mountPointPtr = fuseArena.allocate(ValueLayout.ADDRESS);
		int parseResult = fuse2_h.fuse_parse_cmdline(args, mountPointPtr, multithreaded, foreground); //winfsp pecularity due to unsupportd fuse_lowlevel.h
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		var isMultiThreaded = multithreaded.get(JAVA_INT, 0) == 1;
		var mountPoint = mountPointPtr.get(ValueLayout.ADDRESS.withoutTargetLayout().withName("mountpoint"), 0);
		return new FuseArgs(args, mountPoint, isMultiThreaded);
	}

	@Override
	protected void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse3_operations.init$set(fuseOperationsStruct, fuse3_operations.init.allocate(this::init, fuseArena));
			case ACCESS -> fuse3_operations.access$set(fuseOperationsStruct, MemorySegment.NULL);
			case CHMOD -> fuse3_operations.chmod$set(fuseOperationsStruct, fuse3_operations.chmod.allocate(this::chmod, fuseArena));
			case CHOWN -> fuse3_operations.chown$set(fuseOperationsStruct, fuse3_operations.chown.allocate(this::chown, fuseArena));
			case CREATE -> fuse3_operations.create$set(fuseOperationsStruct, fuse3_operations.create.allocate(this::create, fuseArena));
			case DESTROY -> fuse3_operations.destroy$set(fuseOperationsStruct, fuse3_operations.destroy.allocate(this::destroy, fuseArena));
			case FLUSH -> fuse3_operations.flush$set(fuseOperationsStruct, fuse3_operations.flush.allocate(this::flush, fuseArena));
			case FSYNC -> fuse3_operations.fsync$set(fuseOperationsStruct, fuse3_operations.fsync.allocate(this::fsync, fuseArena));
			case FSYNCDIR -> fuse3_operations.fsyncdir$set(fuseOperationsStruct, fuse3_operations.fsyncdir.allocate(this::fsyncdir, fuseArena));
			case GET_ATTR -> fuse3_operations.getattr$set(fuseOperationsStruct, fuse3_operations.getattr.allocate(this::getattr, fuseArena));
			case GET_XATTR -> fuse3_operations.getxattr$set(fuseOperationsStruct, fuse3_operations.getxattr.allocate(this::getxattr, fuseArena));
			case LIST_XATTR -> fuse3_operations.listxattr$set(fuseOperationsStruct, fuse3_operations.listxattr.allocate(this::listxattr, fuseArena));
			case MKDIR -> fuse3_operations.mkdir$set(fuseOperationsStruct, fuse3_operations.mkdir.allocate(this::mkdir, fuseArena));
			case OPEN -> fuse3_operations.open$set(fuseOperationsStruct, fuse3_operations.open.allocate(this::open, fuseArena));
			case OPEN_DIR -> fuse3_operations.opendir$set(fuseOperationsStruct, fuse3_operations.opendir.allocate(this::opendir, fuseArena));
			case READ -> fuse3_operations.read$set(fuseOperationsStruct, fuse3_operations.read.allocate(this::read, fuseArena));
			case READ_DIR -> fuse3_operations.readdir$set(fuseOperationsStruct, fuse3_operations.readdir.allocate(this::readdir, fuseArena));
			case READLINK -> fuse3_operations.readlink$set(fuseOperationsStruct, fuse3_operations.readlink.allocate(this::readlink, fuseArena));
			case RELEASE -> fuse3_operations.release$set(fuseOperationsStruct, fuse3_operations.release.allocate(this::release, fuseArena));
			case RELEASE_DIR -> fuse3_operations.releasedir$set(fuseOperationsStruct, fuse3_operations.releasedir.allocate(this::releasedir, fuseArena));
			case REMOVE_XATTR -> fuse3_operations.removexattr$set(fuseOperationsStruct, fuse3_operations.removexattr.allocate(this::removexattr, fuseArena));
			case RENAME -> fuse3_operations.rename$set(fuseOperationsStruct, fuse3_operations.rename.allocate(this::rename, fuseArena));
			case RMDIR -> fuse3_operations.rmdir$set(fuseOperationsStruct, fuse3_operations.rmdir.allocate(this::rmdir, fuseArena));
			case SET_XATTR -> fuse3_operations.setxattr$set(fuseOperationsStruct, fuse3_operations.setxattr.allocate(this::setxattr, fuseArena));
			case STATFS -> fuse3_operations.statfs$set(fuseOperationsStruct, fuse3_operations.statfs.allocate(this::statfs, fuseArena));
			case SYMLINK -> fuse3_operations.symlink$set(fuseOperationsStruct, fuse3_operations.symlink.allocate(this::symlink, fuseArena));
			case TRUNCATE -> fuse3_operations.truncate$set(fuseOperationsStruct, fuse3_operations.truncate.allocate(this::truncate, fuseArena));
			case UNLINK -> fuse3_operations.unlink$set(fuseOperationsStruct, fuse3_operations.unlink.allocate(this::unlink, fuseArena));
			case UTIMENS -> fuse3_operations.utimens$set(fuseOperationsStruct, fuse3_operations.utimens.allocate(this::utimens, fuseArena));
			case WRITE -> fuse3_operations.write$set(fuseOperationsStruct, fuse3_operations.write.allocate(this::write, fuseArena));
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
			return fuseOperations.fsyncdir(MemoryUtils.toUtf8StringOrNull(path), datasync, new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int getattr(MemorySegment path, MemorySegment stat, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.getattr(path.getUtf8String(0), new StatImpl(stat, arena), new FileInfoImpl(fi, arena));
		}
	}

	@VisibleForTesting
	int getxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.getxattr(path.getUtf8String(0), name.getUtf8String(0), val);
	}

	@VisibleForTesting
	int setxattr(MemorySegment path, MemorySegment name, MemorySegment value, long size, int flags) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.setxattr(path.getUtf8String(0), name.getUtf8String(0), val, flags);
	}

	@VisibleForTesting
	int listxattr(MemorySegment path, MemorySegment value, long size) {
		var val = value.reinterpret(size).asByteBuffer();
		return fuseOperations.listxattr(path.getUtf8String(0), val);
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
			var buffer = buf.reinterpret(size).asByteBuffer();
			return fuseOperations.read(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena));
		}
	}

	private int readdir(MemorySegment path, MemorySegment buf, MemorySegment filler, long offset, MemorySegment fi, int flags) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, arena), offset, new FileInfoImpl(fi, arena), flags);
		}
	}

	private int readlink(MemorySegment path, MemorySegment buf, long len) {
		var buffer = buf.reinterpret(len).asByteBuffer();
		return fuseOperations.readlink(path.getUtf8String(0), buffer, len);
	}

	private int release(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.release(path.getUtf8String(0), new FileInfoImpl(fi, arena));
		}
	}

	private int releasedir(MemorySegment path, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			return fuseOperations.releasedir(MemoryUtils.toUtf8StringOrNull(path), new FileInfoImpl(fi, arena));
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

	@VisibleForTesting
	int truncate(MemorySegment path, long size, MemorySegment fi) {
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
			// On Windows we know for sure that WinFSP will call this function only with
			// valid times: https://github.com/winfsp/winfsp/discussions/445
			var seq = MemoryLayout.sequenceLayout(2, fuse_timespec.$LAYOUT());
			var segment = times.reinterpret(seq.byteSize());
			var time0 = segment.asSlice(0, fuse_timespec.$LAYOUT().byteSize());
			var time1 = segment.asSlice(fuse_timespec.$LAYOUT().byteSize(), fuse_timespec.$LAYOUT().byteSize());
			return fuseOperations.utimens(path.getUtf8String(0), new TimeSpecImpl(time0), new TimeSpecImpl(time1), new FileInfoImpl(fi, arena));
		}
	}

	private int write(MemorySegment path, MemorySegment buf, long size, long offset, MemorySegment fi) {
		try (var arena = Arena.ofConfined()) {
			var buffer = buf.reinterpret(size).asByteBuffer();
			return fuseOperations.write(path.getUtf8String(0), buffer, size, offset, new FileInfoImpl(fi, arena));
		}
	}

}
