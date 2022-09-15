package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.MountFailedException;
import org.cryptomator.jfuse.win.amd64.extr.fuse2.fuse2_h;
import org.cryptomator.jfuse.win.amd64.extr.fuse2.fuse_args;
import org.cryptomator.jfuse.win.amd64.extr.fuse3_operations;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;
import org.cryptomator.jfuse.win.amd64.extr.fuse_timespec;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class FuseImpl extends Fuse {

	//Used to check, if the mounted fs is actually accessible, see https://github.com/winfsp/winfsp/discussions/440
	private static final String MOUNT_PROBE = "/jfuse_windows_mount_probe";
	private final FuseOperations delegate;
	private final MemorySegment fuseOps;

	private final CountDownLatch mountProbeSucceeded = new CountDownLatch(1);

	public FuseImpl(FuseOperations fuseOperations) {
		this.fuseOps = fuse3_operations.allocate(fuseScope);
		this.delegate = fuseOperations;
		fuseOperations.supportedOperations().forEach(this::bind);
	}

	@Override
	public void mount(String progName, Path mountPoint, String... flags) throws MountFailedException {
		var adjustedMP = mountPoint;
		if (mountPoint.equals(mountPoint.getRoot()) && mountPoint.isAbsolute()) {
			//winfsp accepts only drive letters written in drive relative notation
			adjustedMP = Path.of(mountPoint.toString().charAt(0) + ":");
		}
		super.mount(progName, adjustedMP, flags);
		try {
			waitForMountingToComplete(mountPoint);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new MountFailedException("Interrupted while waiting for mounting to finish");
		}
	}

	@VisibleForTesting
	void waitForMountingToComplete(Path mountPoint) throws InterruptedException {
		var probe = Files.getFileAttributeView(mountPoint.resolve(MOUNT_PROBE.substring(1)), BasicFileAttributeView.class);
		do {
			try {
				probe.readAttributes(); // we don't care about the result, we just want to trigger a getattr call
			} catch (IOException e) {
				//noop
			}
		} while (!mountProbeSucceeded.await(333, TimeUnit.MILLISECONDS));
	}


	@Override
	protected FuseMount mount(List<String> args) throws MountFailedException {
		var fuseArgs = parseArgs(args);
		var fuse = fuse_h.fuse3_new(fuseArgs.args(), fuseOps, fuseOps.byteSize(), MemoryAddress.NULL);
		if (MemoryAddress.NULL.equals(fuse)) {
			throw new MountFailedException("fuse_new failed");
		}
		if (fuse_h.fuse3_mount(fuse, fuseArgs.mountPoint()) != 0) {
			throw new MountFailedException("fuse_mount failed");
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
		argv.setAtIndex(ValueLayout.ADDRESS, argc, MemoryAddress.NULL);
		fuse_args.argc$set(args, argc);
		fuse_args.argv$set(args, argv.address());
		fuse_args.allocated$set(args, 0);

		var multithreaded = fuseScope.allocate(JAVA_INT, 1);
		var foreground = fuseScope.allocate(JAVA_INT, 1);
		var mountPointPtr = fuseScope.allocate(ValueLayout.ADDRESS);
		int parseResult = fuse2_h.fuse_parse_cmdline(args, mountPointPtr, multithreaded, foreground); //winfsp pecularity due to unsupportd fuse_lowlevel.h
		if (parseResult != 0) {
			throw new IllegalArgumentException("fuse_parse_cmdline failed to parse " + String.join(" ", cmdLineArgs));
		}
		var isMultiThreaded = multithreaded.get(JAVA_INT, 0) == 1;
		var mountPoint = mountPointPtr.get(ValueLayout.ADDRESS, 0);
		return new FuseArgs(args, mountPoint, isMultiThreaded);
	}

	/**
	 * Sets a fuse callback. For supported callbacks, see winfsp/inc/fuse3/fuse_h
	 * @param operation The fuse operation enum, indicating which operation to set.
	 */
	private void bind(FuseOperations.Operation operation) {
		switch (operation) {
			case INIT -> fuse3_operations.init$set(fuseOps, fuse3_operations.init.allocate(this::init, fuseScope).address());
			case ACCESS -> fuse3_operations.access$set(fuseOps, MemoryAddress.NULL);
			case CHMOD -> fuse3_operations.chmod$set(fuseOps, fuse3_operations.chmod.allocate(this::chmod, fuseScope).address());
			case CREATE -> fuse3_operations.create$set(fuseOps, fuse3_operations.create.allocate(this::create, fuseScope).address());
			case DESTROY -> fuse3_operations.destroy$set(fuseOps, fuse3_operations.destroy.allocate(this::destroy, fuseScope).address());
			case GET_ATTR -> fuse3_operations.getattr$set(fuseOps, fuse3_operations.getattr.allocate(this::getattr, fuseScope).address());
			case MKDIR -> fuse3_operations.mkdir$set(fuseOps, fuse3_operations.mkdir.allocate(this::mkdir, fuseScope).address());
			case OPEN -> fuse3_operations.open$set(fuseOps, fuse3_operations.open.allocate(this::open, fuseScope).address());
			case OPEN_DIR -> fuse3_operations.opendir$set(fuseOps, fuse3_operations.opendir.allocate(this::opendir, fuseScope).address());
			case READ -> fuse3_operations.read$set(fuseOps, fuse3_operations.read.allocate(this::read, fuseScope).address());
			case READ_DIR -> fuse3_operations.readdir$set(fuseOps, fuse3_operations.readdir.allocate(this::readdir, fuseScope).address());
			case READLINK -> fuse3_operations.readlink$set(fuseOps, fuse3_operations.readlink.allocate(this::readlink, fuseScope).address());
			case RELEASE -> fuse3_operations.release$set(fuseOps, fuse3_operations.release.allocate(this::release, fuseScope).address());
			case RELEASE_DIR -> fuse3_operations.releasedir$set(fuseOps, fuse3_operations.releasedir.allocate(this::releasedir, fuseScope).address());
			case RENAME -> fuse3_operations.rename$set(fuseOps, fuse3_operations.rename.allocate(this::rename, fuseScope).address());
			case RMDIR -> fuse3_operations.rmdir$set(fuseOps, fuse3_operations.rmdir.allocate(this::rmdir, fuseScope).address());
			case STATFS -> fuse3_operations.statfs$set(fuseOps, fuse3_operations.statfs.allocate(this::statfs, fuseScope).address());
			case SYMLINK -> fuse3_operations.symlink$set(fuseOps, fuse3_operations.symlink.allocate(this::symlink, fuseScope).address());
			case TRUNCATE -> fuse3_operations.truncate$set(fuseOps, fuse3_operations.truncate.allocate(this::truncate, fuseScope).address());
			case UNLINK -> fuse3_operations.unlink$set(fuseOps, fuse3_operations.unlink.allocate(this::unlink, fuseScope).address());
			case UTIMENS -> fuse3_operations.utimens$set(fuseOps, fuse3_operations.utimens.allocate(this::utimens, fuseScope).address());
			case WRITE -> fuse3_operations.write$set(fuseOps, fuse3_operations.write.allocate(this::write, fuseScope).address());
		}
	}

	private Addressable init(MemoryAddress conn, MemoryAddress fuseConfig) {
		try (var scope = MemorySession.openConfined()) {
			delegate.init(new FuseConnInfoImpl(conn, scope));
		}
		return MemoryAddress.NULL;
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

	@VisibleForTesting
	int getattr(MemoryAddress path, MemoryAddress stat, MemoryAddress fi) {
		try (var scope = MemorySession.openConfined()) {
			var pathObj = path.getUtf8String(0);
			if(mountProbeSucceeded.getCount() > 0 && pathObj.equals(MOUNT_PROBE) ) {
				mountProbeSucceeded.countDown();
			}
			return delegate.getattr(pathObj, new StatImpl(stat, scope), new FileInfoImpl(fi, scope));
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

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi, int flags) {
		try (var scope = MemorySession.openConfined()) {
			var parsedFlags = FuseOperations.ReadDirFlags.parse(flags, fuse_h.FUSE_READDIR_PLUS());
			return delegate.readdir(path.getUtf8String(0), new DirFillerImpl(buf, filler, scope), offset, new FileInfoImpl(fi, scope), parsedFlags);
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

	@VisibleForTesting
	int truncate(MemoryAddress path, long size, MemoryAddress fi) {
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
				var segment = MemorySegment.allocateNative(fuse_timespec.$LAYOUT().byteSize(), scope);
				fuse_timespec.tv_sec$set(segment, 0);
				fuse_timespec.tv_nsec$set(segment, 0); //FIXME: use hardcoded UTIME_NOW
				var time = new TimeSpecImpl(segment);
				return delegate.utimens(path.getUtf8String(0), time, time, new FileInfoImpl(fi, scope));
			} else {
				var seq = MemoryLayout.sequenceLayout(2, fuse_timespec.$LAYOUT());
				var segment = MemorySegment.ofAddress(times, seq.byteSize(), scope);
				var time0 = segment.asSlice(0, fuse_timespec.$LAYOUT().byteSize());
				var time1 = segment.asSlice(fuse_timespec.$LAYOUT().byteSize(), fuse_timespec.$LAYOUT().byteSize());
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
