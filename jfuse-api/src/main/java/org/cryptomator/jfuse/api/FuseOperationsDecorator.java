package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Convenience interface to make decorating {@link FuseOperations} easier.
 */
public interface FuseOperationsDecorator extends FuseOperations {

	/**
	 * Get the to-be-decorated FuseOperations object.
	 *
	 * @return the undecorated operations
	 */
	FuseOperations delegate();

	@Override
	default Set<Operation> supportedOperations() {
		return delegate().supportedOperations();
	}

	@Override
	default Errno errno() {
		return delegate().errno();
	}

	@Override
	default int getattr(String path, Stat stat, @Nullable FileInfo fi) {
		return delegate().getattr(path, stat, fi);
	}

	@Override
	default int getxattr(String path, String name, ByteBuffer value) {
		return delegate().getxattr(path, name, value);
	}

	@Override
	default int setxattr(String path, String name, ByteBuffer value, int flags) {
		return delegate().setxattr(path, name, value, flags);
	}

	@Override
	default int listxattr(String path, ByteBuffer list) {
		return delegate().listxattr(path, list);
	}

	@Override
	default int removexattr(String path, String name) {
		return delegate().removexattr(path, name);
	}

	@Override
	default int readlink(String path, ByteBuffer buf, long len) {
		return delegate().readlink(path, buf, len);
	}

	@Override
	default int mknod(String path, short mode, int rdev) {
		return delegate().mknod(path, mode, rdev);
	}

	@Override
	default int mkdir(String path, int mode) {
		return delegate().mkdir(path, mode);
	}

	@Override
	default int unlink(String path) {
		return delegate().unlink(path);
	}

	@Override
	default int rmdir(String path) {
		return delegate().rmdir(path);
	}

	@Override
	default int symlink(String linkname, String target) {
		return delegate().symlink(linkname, target);
	}

	@Override
	default int rename(String oldpath, String newpath, int flags) {
		return delegate().rename(oldpath, newpath, flags);
	}

	@Override
	default int link(String linkname, String target) {
		return delegate().link(linkname, target);
	}

	@Override
	default int chmod(String path, int mode, @Nullable FileInfo fi) {
		return delegate().chmod(path, mode, fi);
	}

	@Override
	default int chown(String path, int uid, int gid, @Nullable FileInfo fi) {
		return delegate().chown(path, uid, gid, fi);
	}

	@Override
	default int truncate(String path, long size, @Nullable FileInfo fi) {
		return delegate().truncate(path, size, fi);
	}

	@Override
	default int open(String path, FileInfo fi) {
		return delegate().open(path, fi);
	}

	@Override
	default int read(String path, ByteBuffer buf, long count, long offset, FileInfo fi) {
		return delegate().read(path, buf, count, offset, fi);
	}

	@Override
	default int write(String path, ByteBuffer buf, long count, long offset, FileInfo fi) {
		return delegate().write(path, buf, count, offset, fi);
	}

	@Override
	default int statfs(String path, Statvfs statvfs) {
		return delegate().statfs(path, statvfs);
	}

	@Override
	default int flush(String path, FileInfo fi) {
		return delegate().flush(path, fi);
	}

	@Override
	default int fsync(String path, int datasync, FileInfo fi) {
		return delegate().fsync(path, datasync, fi);
	}

	@Override
	default int release(String path, FileInfo fi) {
		return delegate().release(path, fi);
	}

	@Override
	default int opendir(String path, FileInfo fi) {
		return delegate().opendir(path, fi);
	}

	@Override
	default int readdir(String path, DirFiller filler, long offset, FileInfo fi, int flags) {
		return delegate().readdir(path, filler, offset, fi, flags);
	}

	@Override
	default int fsyncdir(@Nullable String path, int datasync, FileInfo fi) {
		return delegate().fsyncdir(path, datasync, fi);
	}

	@Override
	default int releasedir(@Nullable String path, FileInfo fi) {
		return delegate().releasedir(path, fi);
	}

	@Override
	default void init(FuseConnInfo conn, @Nullable FuseConfig cfg) {
		delegate().init(conn, cfg);
	}

	@Override
	default void destroy() {
		delegate().destroy();
	}

	@Override
	default int access(String path, int mask) {
		return delegate().access(path, mask);
	}

	@Override
	default int create(String path, int mode, FileInfo fi) {
		return delegate().create(path, mode, fi);
	}

	@Override
	default int utimens(String path, TimeSpec atime, TimeSpec mtime, @Nullable FileInfo fi) {
		return delegate().utimens(path, atime, mtime, fi);
	}

}
