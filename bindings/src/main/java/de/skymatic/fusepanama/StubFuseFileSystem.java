package de.skymatic.fusepanama;

import java.foreign.memory.Callback;
import java.foreign.memory.Pointer;

import com.github.libfuse.fuse_common_h;
import com.github.libfuse.fuse_h;
import usr.include.sys._types._timespec_h;
import usr.include.sys.fcntl_h;
import usr.include.sys.stat_h;
import usr.include.sys.statvfs_h;
import usr.include.utime_h;

/**
 * Implements all fuse operations with a default implementation, that returns -{@link Errno#ENOSYS},
 * with the exception of {@link #open(Pointer, Pointer) open}, {@link #release(Pointer, Pointer) release},
 * {@link #opendir(Pointer, Pointer) opendir}, {@link #releasedir(Pointer, Pointer) releasedir}
 * and {@link #statfs(Pointer, Pointer) statfs}, which return 0.
 */
public class StubFuseFileSystem extends AbstractFuseFileSystem {

	@Override
	@NotImplemented
	public int getattr(Pointer<Byte> path, Pointer<stat_h.stat> buf) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int readlink(Pointer<Byte> path, Pointer<Byte> buf, long len) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int getdir(Pointer<Byte> path, Pointer<fuse_h.fuse_dirhandle> fuse_dirhandlePointer, Callback<fuse_h.FI2> fi2Callback) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int mknod(Pointer<Byte> path, short mode, int rdev) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int mkdir(Pointer<Byte> path, short mode) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int unlink(Pointer<Byte> path) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int rmdir(Pointer<Byte> path) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int symlink(Pointer<Byte> linkname, Pointer<Byte> target) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int rename(Pointer<Byte> oldpath, Pointer<Byte> newpath) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int link(Pointer<Byte> linkname, Pointer<Byte> target) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int chmod(Pointer<Byte> path, short mode) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int chown(Pointer<Byte> path, int uid, int gid) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int truncate(Pointer<Byte> path, long size) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int utime(Pointer<Byte> path, Pointer<utime_h.utimbuf> utimbufPointer) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int open(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		return 0;
	}

	@Override
	@NotImplemented
	public int read(Pointer<Byte> path, Pointer<Byte> buf, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int write(Pointer<Byte> path, Pointer<Byte> buf, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int statfs(Pointer<Byte> path, Pointer<statvfs_h.statvfs> buf) {
		return 0;
	}

	@Override
	@NotImplemented
	public int flush(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int release(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		return 0;
	}

	@Override
	@NotImplemented
	public int fsync(Pointer<Byte> path, int datasync, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int setxattr(Pointer<Byte> path, Pointer<Byte> name, Pointer<Byte> value, long size, int flags) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int getxattr(Pointer<Byte> path, Pointer<Byte> name, Pointer<Byte> value, long size) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int listxattr(Pointer<Byte> path, Pointer<Byte> list, long size) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int removexattr(Pointer<Byte> path, Pointer<Byte> name) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int opendir(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		return 0;
	}

	@Override
	@NotImplemented
	public int readdir(Pointer<Byte> path, Pointer<?> buf, Callback<fuse_h.FI1> filler, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int releasedir(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi) {
		return 0;
	}

	@Override
	@NotImplemented
	public int fsyncdir(Pointer<Byte> path, int datasync, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int access(Pointer<Byte> path, int mask) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int create(Pointer<Byte> path, short mode, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int ftruncate(Pointer<Byte> path, long size, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int fgetattr(Pointer<Byte> path, Pointer<stat_h.stat> buf, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int lock(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, int cmd, Pointer<fcntl_h.flock> lock) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int utimens(Pointer<Byte> path, Pointer<_timespec_h.timespec> tv) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int bmap(Pointer<Byte> path, long blocksize, Pointer<Long> idx) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int ioctl(Pointer<Byte> path, int cmd, Pointer<?> arg, Pointer<fuse_common_h.fuse_file_info> fi, int flags, Pointer<?> data) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int poll(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, Pointer<fuse_common_h.fuse_pollhandle> ph, Pointer<Integer> reventsp) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int writeBuf(Pointer<Byte> path, Pointer<fuse_common_h.fuse_bufvec> buf, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int readBuf(Pointer<Byte> path, Pointer<? extends Pointer<fuse_common_h.fuse_bufvec>> bufp, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int flock(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, int op) {
		return -Errno.ENOSYS;
	}

	@Override
	@NotImplemented
	public int fallocate(Pointer<Byte> path, int mode, long offset, long length, Pointer<fuse_common_h.fuse_file_info> fi) {
		return -Errno.ENOSYS;
	}
}
