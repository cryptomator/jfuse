package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.linux.aarch64.extr.fcntl.fcntl_h;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_file_info;
import org.jetbrains.annotations.Nullable;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Set;

record FileInfoImpl(MemorySegment segment) implements FileInfo {

	private static final int O_RDONLY = fcntl_h.O_RDONLY();
	private static final int O_WRONLY = fcntl_h.O_WRONLY();
	private static final int O_RDWR = fcntl_h.O_RDWR();
	private static final int O_APPEND = fcntl_h.O_APPEND();
	private static final int O_CREAT = fcntl_h.O_CREAT();
	private static final int O_TRUNC = fcntl_h.O_TRUNC();
	private static final int O_EXCL = fcntl_h.O_EXCL();
	private static final int O_SYNC = fcntl_h.O_SYNC();
	private static final int O_DSYNC = fcntl_h.O_DSYNC();

	/**
	 * Factory method to map native memory to an {@link FileInfo} object
	 *
	 * @param address the {@link MemorySegment} representing the starting address
	 * @param scope   the {@link Arena} in which this object will be alive
	 * @return an {@link FileInfo} object or {@code null} if {@code address} is a NULL pointer
	 */
	@Nullable
	public static FileInfoImpl of(MemorySegment address, Arena scope) {
		return MemorySegment.NULL.equals(address) ? null : new FileInfoImpl(address, scope);
	}

	public FileInfoImpl(MemorySegment address, Arena scope) {
		this(fuse_file_info.ofAddress(address, scope));
	}

	@Override
	public long getFh() {
		return fuse_file_info.fh$get(segment);
	}

	@Override
	public void setFh(long fh) {
		fuse_file_info.fh$set(segment, fh);
	}

	@Override
	public int getFlags() {
		return fuse_file_info.flags$get(segment);
	}

	@Override
	public Set<StandardOpenOption> getOpenFlags() {
		Set<StandardOpenOption> result = EnumSet.noneOf(StandardOpenOption.class);
		int flags = getFlags();
		// read / write / readwrite are mutually exclusive:
		if ((flags & O_RDWR) == O_RDWR) {
			result.add(StandardOpenOption.READ);
			result.add(StandardOpenOption.WRITE);
		} else if ((flags & O_WRONLY) == O_WRONLY) {
			result.add(StandardOpenOption.WRITE);
		} else if ((flags & O_RDONLY) == O_RDONLY) {
			result.add(StandardOpenOption.READ);
		}
		// create / create new:
		if ((flags & O_CREAT) == O_CREAT && (flags & O_EXCL) == O_EXCL) {
			result.add(StandardOpenOption.CREATE_NEW);
		} else if ((flags & O_CREAT) == O_CREAT) {
			result.add(StandardOpenOption.CREATE);
		}
		// append / truncate
		if ((flags & O_APPEND) == O_APPEND) {
			result.add(StandardOpenOption.APPEND);
		}
		if ((flags & O_TRUNC) == O_TRUNC) {
			result.add(StandardOpenOption.TRUNCATE_EXISTING);
		}
		// sync / dsync
		if ((flags & O_SYNC) == O_SYNC) {
			result.add(StandardOpenOption.SYNC);
		} else if ((flags & O_DSYNC) == O_DSYNC) {
			result.add(StandardOpenOption.DSYNC);
		}
		return result;
	}

	@Override
	public long getLockOwner() {
		return fuse_file_info.lock_owner$get(segment);
	}

}
