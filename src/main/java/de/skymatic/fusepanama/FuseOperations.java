package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_operations;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.nio.ByteBuffer;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface FuseOperations {

	enum Operation {
		ACCESS((struct, ops, scope) -> fuse_operations.access$set(struct, fuse_operations.access.allocate(ops::access, scope))),
		DESTROY((struct, ops, scope) -> { /* handled by Fuse.java */ }),
		GET_ATTR((struct, ops, scope) -> fuse_operations.getattr$set(struct, fuse_operations.getattr.allocate(ops::getattr, scope))),
		INIT((struct, ops, scope) -> { /* handled by Fuse.java */ }),
		OPEN((struct, ops, scope) -> fuse_operations.open$set(struct, fuse_operations.open.allocate(ops::open, scope))),
		OPEN_DIR((struct, ops, scope) -> fuse_operations.opendir$set(struct, fuse_operations.opendir.allocate(ops::opendir, scope))),
		READ((struct, ops, scope) -> fuse_operations.read$set(struct, fuse_operations.read.allocate(ops::read, scope))),
		READ_DIR((struct, ops, scope) -> fuse_operations.readdir$set(struct, fuse_operations.readdir.allocate(ops::readdir, scope))),
		RELEASE((struct, ops, scope) -> fuse_operations.release$set(struct, fuse_operations.release.allocate(ops::release, scope))),
		RELEASE_DIR((struct, ops, scope) -> fuse_operations.releasedir$set(struct, fuse_operations.releasedir.allocate(ops::releasedir, scope))),
		STATFS((struct, ops, scope) -> fuse_operations.statfs$set(struct, fuse_operations.statfs.allocate(ops::statfs, scope))),
		;

		private final Binder binder;

		Operation(Binder binder) {
			this.binder = binder;
		}

		void bind(MemorySegment struct, FuseOperations ops, ResourceScope scope) {
			binder.bind(struct, ops, scope);
		}

		private interface Binder {
			/**
			 * Binds function pointers within the given <code>struct</code> to the corresponding methods defined in <code>ops</code>.
			 *
			 * @param struct A memory segment containing a <code>fuse_operations</code> struct
			 * @param ops    The FuseOperations instance, whose methods should be referenced
			 * @param scope  The scope of the upcall stub that needs to be allocated (should not be closed before the <code>struct</code>'s scope)
			 */
			void bind(MemorySegment struct, FuseOperations ops, ResourceScope scope);
		}

	}

	/**
	 * @return The set of supported operations.
	 */
	Set<Operation> supportedOperations();

	/**
	 * Get file attributes.
	 * <p>
	 * Similar to stat().  The 'st_dev' and 'st_blksize' fields are
	 * ignored.	 The 'st_ino' field is ignored except if the 'use_ino'
	 * mount option is given.
	 */
	default int getattr(String path, Stat stat) {
		return -Errno.ENOSYS;
	}

	private int getattr(MemoryAddress path, MemoryAddress stat) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return getattr(CLinker.toJavaString(path, UTF_8), new Stat(stat, scope));
		}
	}

	/**
	 * Read the target of a symbolic link
	 * <p>
	 * The buffer should be filled with a null terminated string.  The
	 * buffer size argument includes the space for the terminating
	 * null character.	If the linkname is too long to fit in the
	 * buffer, it should be truncated.	The return value should be 0
	 * for success.
	 */
	default int readlink(String path, ByteBuffer buf, long len) {
		return -Errno.ENOSYS;
	}

//	/**
//	 * @deprecated use {@link #readdir(String, Pointer, Callback, long, Pointer)} instead
//	 */
//	@Deprecated
//	default int getdir(String path, Pointer<fuse_h.fuse_dirhandle> fuse_dirhandlePointer, Callback<fuse_h.FI2> fi2Callback) {
//		return -Errno.ENOSYS;
//	}

	/**
	 * Create a file node
	 * <p>
	 * This is called for creation of all non-directory, non-symlink
	 * nodes.  If the filesystem defines a create() method, then for
	 * regular files that will be called instead.
	 */
	default int mknod(String path, short mode, int rdev) {
		return -Errno.ENOSYS;
	}

	/**
	 * Create a directory
	 * <p>
	 * Note that the mode argument may not have the type specification
	 * bits set, i.e. S_ISDIR(mode) can be false.  To obtain the
	 * correct directory type bits use  mode|S_IFDIR
	 */
	default int mkdir(String path, short mode) {
		return -Errno.ENOSYS;
	}

	/**
	 * Remove a file
	 */
	default int unlink(String path) {
		return -Errno.ENOSYS;
	}

	/**
	 * Remove a directory
	 */
	default int rmdir(String path) {
		return -Errno.ENOSYS;
	}

	/**
	 * Create a symbolic link
	 */
	default int symlink(String linkname, String target) {
		return -Errno.ENOSYS;
	}

	/**
	 * Rename a file
	 */
	default int rename(String oldpath, String newpath) {
		return -Errno.ENOSYS;
	}

	/**
	 * Create a hard link to a file
	 */
	default int link(String linkname, String target) {
		return -Errno.ENOSYS;
	}

	/**
	 * Change the permission bits of a file
	 */
	default int chmod(String path, short mode) {
		return -Errno.ENOSYS;
	}

	/**
	 * Change the owner and group of a file
	 */
	default int chown(String path, int uid, int gid) {
		return -Errno.ENOSYS;
	}

	/**
	 * Change the size of a file
	 */
	default int truncate(String path, long size) {
		return -Errno.ENOSYS;
	}

//	/**
//	 * Change the access and/or modification times of a file
//	 *
//	 * @deprecated use {@link #utimens(String, TimeSpec) utimens()} instead.
//	 */
//	@Deprecated
//	default int utime(String path, Pointer<utime_h.utimbuf> utimbufPointer) {
//		return -Errno.ENOSYS;
//	}

	/**
	 * File open operation
	 * <p>
	 * No creation (O_CREAT, O_EXCL) and by default also no
	 * truncation (O_TRUNC) flags will be passed to open(). If an
	 * application specifies O_TRUNC, fuse first calls truncate()
	 * and then open(). Only if 'atomic_o_trunc' has been
	 * specified and kernel version is 2.6.24 or later, O_TRUNC is
	 * passed on to open.
	 * <p>
	 * Unless the 'default_permissions' mount option is given,
	 * open should check if the operation is permitted for the
	 * given flags. Optionally open may also return an arbitrary
	 * filehandle in the fuse_file_info structure, which will be
	 * passed to all file operations.
	 * <p>
	 * Changed in version 2.2
	 */
	default int open(String path, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	private int open(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return open(CLinker.toJavaString(path, UTF_8), new FileInfo(fi, scope));
		}
	}

	/**
	 * Read data from an open file
	 * <p>
	 * Read should return exactly the number of bytes requested except
	 * on EOF or error, otherwise the rest of the data will be
	 * substituted with zeroes.	 An exception to this is when the
	 * 'direct_io' mount option is specified, in which case the return
	 * value of the read system call will reflect the return value of
	 * this operation.
	 * <p>
	 * Changed in version 2.2
	 */
	default int read(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	private int read(MemoryAddress path, MemoryAddress buf, long size, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var buffer = buf.asSegment(size, scope).asByteBuffer();
			return read(CLinker.toJavaString(path, UTF_8), buffer, size, offset, new FileInfo(fi, scope));
		}
	}

	/**
	 * Write data to an open file
	 * <p>
	 * Write should return exactly the number of bytes requested
	 * except on error.	 An exception to this is when the 'direct_io'
	 * mount option is specified (see read operation).
	 * <p>
	 * Changed in version 2.2
	 */
	default int write(String path, ByteBuffer buf, long size, long offset, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Get file system statistics
	 * <p>
	 * The 'f_frsize', 'f_favail', 'f_fsid' and 'f_flag' fields are ignored
	 * <p>
	 * Replaced 'struct statfs' parameter with 'struct statvfs' in
	 * version 2.5
	 */
	default int statfs(String path, Statvfs statvfs) {
		return -Errno.ENOSYS;
	}

	private int statfs(MemoryAddress path, MemoryAddress statvfs) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return statfs(CLinker.toJavaString(path, UTF_8), new Statvfs(statvfs, scope));
		}
	}

	/**
	 * Possibly flush cached data
	 * <p>
	 * BIG NOTE: This is not equivalent to fsync().  It's not a
	 * request to sync dirty data.
	 * <p>
	 * Flush is called on each close() of a file descriptor.  So if a
	 * filesystem wants to return write errors in close() and the file
	 * has cached dirty data, this is a good place to write back data
	 * and return any errors.  Since many applications ignore close()
	 * errors this is not always useful.
	 * <p>
	 * NOTE: The flush() method may be called more than once for each
	 * open().	This happens if more than one file descriptor refers
	 * to an opened file due to dup(), dup2() or fork() calls.	It is
	 * not possible to determine if a flush is final, so each flush
	 * should be treated equally.  Multiple write-flush sequences are
	 * relatively rare, so this shouldn't be a problem.
	 * <p>
	 * Filesystems shouldn't assume that flush will always be called
	 * after some writes, or that if will be called at all.
	 * <p>
	 * Changed in version 2.2
	 */
	default int flush(String path, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Release an open file
	 * <p>
	 * Release is called when there are no more references to an open
	 * file: all file descriptors are closed and all memory mappings
	 * are unmapped.
	 * <p>
	 * For every open() call there will be exactly one release() call
	 * with the same flags and file descriptor.	 It is possible to
	 * have a file opened more than once, in which case only the last
	 * release will mean, that no more reads/writes will happen on the
	 * file.  The return value of release is ignored.
	 * <p>
	 * Changed in version 2.2
	 */
	default int release(String path, FileInfo fi) {
		return 0;
	}

	private int release(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return release(CLinker.toJavaString(path, UTF_8), new FileInfo(fi, scope));
		}
	}

	/**
	 * Synchronize file contents
	 * <p>
	 * If the datasync parameter is non-zero, then only the user data
	 * should be flushed, not the meta data.
	 * <p>
	 * Changed in version 2.2
	 */
	default int fsync(String path, int datasync, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Set extended attributes
	 */
	default int setxattr(String path, String name, ByteBuffer value, long size, int flags) {
		return -Errno.ENOSYS;
	}

	/**
	 * Get extended attributes
	 */
	default int getxattr(String path, String name, ByteBuffer value, long size) {
		return -Errno.ENOSYS;
	}

	/**
	 * List extended attributes
	 */
	default int listxattr(String path, ByteBuffer list, long size) {
		return -Errno.ENOSYS;
	}

	/**
	 * Remove extended attributes
	 */
	default int removexattr(String path, String name) {
		return -Errno.ENOSYS;
	}

	/**
	 * Open directory
	 * <p>
	 * Unless the 'default_permissions' mount option is given,
	 * this method should check if opendir is permitted for this
	 * directory. Optionally opendir may also return an arbitrary
	 * filehandle in the fuse_file_info structure, which will be
	 * passed to readdir, closedir and fsyncdir.
	 * <p>
	 * Introduced in version 2.3
	 */
	default int opendir(String path, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	private int opendir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return opendir(CLinker.toJavaString(path, UTF_8), new FileInfo(fi, scope));
		}
	}

	/**
	 * Read directory
	 * <p>
	 * This supersedes the old getdir interface.
	 * New applications should use this.
	 * <p>
	 * The filesystem may choose between two modes of operation:
	 * <p>
	 * 1) The readdir implementation ignores the offset parameter, and
	 * passes zero to the filler function's offset.  The filler
	 * function will not return '1' (unless an error happens), so the
	 * whole directory is read in a single readdir operation.  This
	 * works just like the old getdir() method.
	 * <p>
	 * 2) The readdir implementation keeps track of the offsets of the
	 * directory entries.  It uses the offset parameter and always
	 * passes non-zero offset to the filler function.  When the buffer
	 * is full (or an error happens) the filler function will return
	 * '1'.
	 * <p>
	 * Introduced in version 2.3
	 */
	default int readdir(String path, DirFiller filler, long offset, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	private int readdir(MemoryAddress path, MemoryAddress buf, MemoryAddress filler, long offset, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return readdir(CLinker.toJavaString(path, UTF_8), new DirFiller(buf, filler), offset, new FileInfo(fi, scope));
		}
	}

	/**
	 * Release directory
	 * <p>
	 * Introduced in version 2.3
	 */
	default int releasedir(String path, FileInfo fi) {
		return 0;
	}

	private int releasedir(MemoryAddress path, MemoryAddress fi) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return releasedir(CLinker.toJavaString(path, UTF_8), new FileInfo(fi, scope));
		}
	}

	/**
	 * Synchronize directory contents
	 * <p>
	 * If the datasync parameter is non-zero, then only the user data
	 * should be flushed, not the meta data
	 * <p>
	 * Introduced in version 2.3
	 */
	default int fsyncdir(String path, int datasync, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Initialize filesystem
	 * <p>
	 * The return value will passed in the private_data field of
	 * fuse_context to all file operations and as a parameter to the
	 * destroy() method.
	 * <p>
	 * Introduced in version 2.3
	 * Changed in version 2.6
	 */
	default void init(FuseConnInfo conn) {
		// no-op
	}

	/**
	 * Clean up filesystem
	 * <p>
	 * Called on filesystem exit.
	 * <p>
	 * Introduced in version 2.3
	 */
	default void destroy() {
		// no-op
	}

	/**
	 * Check file access permissions
	 * <p>
	 * This will be called for the access() system call.  If the
	 * 'default_permissions' mount option is given, this method is not
	 * called.
	 * <p>
	 * This method is not called under Linux kernel versions 2.4.x
	 * <p>
	 * Introduced in version 2.5
	 */
	default int access(String path, int mask) {
		return -Errno.ENOSYS;
	}

	private int access(MemoryAddress path, int mask) {
		return access(CLinker.toJavaString(path, UTF_8), mask);
	}

	/**
	 * Create and open a file
	 * <p>
	 * If the file does not exist, first create it with the specified
	 * mode, and then open it.
	 * <p>
	 * If this method is not implemented or under Linux kernel
	 * versions earlier than 2.6.15, the mknod() and open() methods
	 * will be called instead.
	 * <p>
	 * Introduced in version 2.5
	 */
	default int create(String path, short mode, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Change the size of an open file
	 * <p>
	 * This method is called instead of the truncate() method if the
	 * truncation was invoked from an ftruncate() system call.
	 * <p>
	 * If this method is not implemented or under Linux kernel
	 * versions earlier than 2.6.15, the truncate() method will be
	 * called instead.
	 * <p>
	 * Introduced in version 2.5
	 */
	default int ftruncate(String path, long size, FileInfo fi) {
		return -Errno.ENOSYS;
	}

	/**
	 * Get attributes from an open file
	 * <p>
	 * This method is called instead of the getattr() method if the
	 * file information is available.
	 * <p>
	 * Currently this is only called after the create() method if that
	 * is implemented (see above).  Later it may be called for
	 * invocations of fstat() too.
	 * <p>
	 * Introduced in version 2.5
	 */
	default int fgetattr(String path, Stat stat, FileInfo fi) {
		return -Errno.ENOSYS;
	}

//	/**
//	 * Perform POSIX file locking operation
//	 * <p>
//	 * The cmd argument will be either F_GETLK, F_SETLK or F_SETLKW.
//	 * <p>
//	 * For the meaning of fields in 'struct flock' see the man page
//	 * for fcntl(2).  The l_whence field will always be set to
//	 * SEEK_SET.
//	 * <p>
//	 * For checking lock ownership, the 'fuse_file_info->owner'
//	 * argument must be used.
//	 * <p>
//	 * For F_GETLK operation, the library will first check currently
//	 * held locks, and if a conflicting lock is found it will return
//	 * information without calling this method.	 This ensures, that
//	 * for local locks the l_pid field is correctly filled in.	The
//	 * results may not be accurate in case of race conditions and in
//	 * the presence of hard links, but it's unlikely that an
//	 * application would rely on accurate GETLK results in these
//	 * cases.  If a conflicting lock is not found, this method will be
//	 * called, and the filesystem may fill out l_pid by a meaningful
//	 * value, or it may leave this field zero.
//	 * <p>
//	 * For F_SETLK and F_SETLKW the l_pid field will be set to the pid
//	 * of the process performing the locking operation.
//	 * <p>
//	 * Note: if this method is not implemented, the kernel will still
//	 * allow file locking to work locally.  Hence it is only
//	 * interesting for network filesystems and similar.
//	 * <p>
//	 * Introduced in version 2.6
//	 */
//	default int lock(String path, FileInfo fi, int cmd, Pointer<fcntl_h.flock> lock) {
//		return -Errno.ENOSYS;
//	}

	/**
	 * Change the access and modification times of a file with
	 * nanosecond resolution
	 * <p>
	 * This supersedes the old utime() interface.  New applications
	 * should use this.
	 * <p>
	 * See the utimensat(2) man page for details.
	 * <p>
	 * Introduced in version 2.6
	 */
	default int utimens(String path, TimeSpec tv) {
		return -Errno.ENOSYS;
	}

//	/**
//	 * Map block index within file to block index within device
//	 * <p>
//	 * Note: This makes sense only for block device backed filesystems
//	 * mounted with the 'blkdev' option
//	 * <p>
//	 * Introduced in version 2.6
//	 */
//	default int bmap(String path, long blocksize, Pointer<Long> idx) {
//		return -Errno.ENOSYS;
//	}

	/**
	 * Ioctl
	 * <p>
	 * flags will have FUSE_IOCTL_COMPAT set for 32bit ioctls in
	 * 64bit environment.  The size and direction of data is
	 * determined by _IOC_*() decoding of cmd.  For _IOC_NONE,
	 * data will be NULL, for _IOC_WRITE data is out area, for
	 * _IOC_READ in area and if both are set in/out area.  In all
	 * non-NULL cases, the area is of _IOC_SIZE(cmd) bytes.
	 * <p>
	 * If flags has FUSE_IOCTL_DIR then the fuse_file_info refers to a
	 * directory file handle.
	 * <p>
	 * Introduced in version 2.8
	 */
	default int ioctl(String path, int cmd, ByteBuffer arg, FileInfo fi, int flags, ByteBuffer data) {
		return -Errno.ENOSYS;
	}

//	/**
//	 * Poll for IO readiness events
//	 * <p>
//	 * Note: If ph is non-NULL, the client should notify
//	 * when IO readiness events occur by calling
//	 * fuse_notify_poll() with the specified ph.
//	 * <p>
//	 * Regardless of the number of times poll with a non-NULL ph
//	 * is received, single notification is enough to clear all.
//	 * Notifying more times incurs overhead but doesn't harm
//	 * correctness.
//	 * <p>
//	 * The callee is responsible for destroying ph with
//	 * fuse_pollhandle_destroy() when no longer in use.
//	 * <p>
//	 * Introduced in version 2.8
//	 */
//	default int poll(String path, FileInfo fi, Pointer<fuse_common_h.fuse_pollhandle> ph, Pointer<Integer> reventsp) {
//		return -Errno.ENOSYS;
//	}

//	/**
//	 * Write contents of buffer to an open file
//	 * <p>
//	 * Similar to the write() method, but data is supplied in a
//	 * generic buffer.  Use fuse_buf_copy() to transfer data to
//	 * the destination.
//	 * <p>
//	 * Introduced in version 2.9
//	 */
//	default int writeBuf(String path, Pointer<fuse_common_h.fuse_bufvec> buf, long offset, FileInfo fi) {
//		return -Errno.ENOSYS;
//	}

//	/**
//	 * Store data from an open file in a buffer
//	 * <p>
//	 * Similar to the read() method, but data is stored and
//	 * returned in a generic buffer.
//	 * <p>
//	 * No actual copying of data has to take place, the source
//	 * file descriptor may simply be stored in the buffer for
//	 * later data transfer.
//	 * <p>
//	 * The buffer must be allocated dynamically and stored at the
//	 * location pointed to by bufp.  If the buffer contains memory
//	 * regions, they too must be allocated using malloc().  The
//	 * allocated memory will be freed by the caller.
//	 * <p>
//	 * Introduced in version 2.9
//	 */
//	default int readBuf(String path, Pointer<? extends Pointer<fuse_common_h.fuse_bufvec>> bufp, long size, long offset, FileInfo fi) {
//		return -Errno.ENOSYS;
//	}

	/**
	 * Perform BSD file locking operation
	 * <p>
	 * The op argument will be either LOCK_SH, LOCK_EX or LOCK_UN
	 * <p>
	 * Nonblocking requests will be indicated by ORing LOCK_NB to
	 * the above operations
	 * <p>
	 * For more information see the flock(2) manual page.
	 * <p>
	 * Additionally fi->owner will be set to a value unique to
	 * this open file.  This same value will be supplied to
	 * ->release() when the file is released.
	 * <p>
	 * Note: if this method is not implemented, the kernel will still
	 * allow file locking to work locally.  Hence it is only
	 * interesting for network filesystems and similar.
	 * <p>
	 * Introduced in version 2.9
	 */
	default int flock(String path, FileInfo fi, int op) {
		return -Errno.ENOSYS;
	}

	/**
	 * Allocates space for an open file
	 * <p>
	 * This function ensures that required space is allocated for specified
	 * file.  If this function returns success then any subsequent write
	 * request to specified range is guaranteed not to fail because of lack
	 * of space on the file system media.
	 * <p>
	 * Introduced in version 2.9.1
	 */
	default int fallocate(String path, int mode, long offset, long length, FileInfo fi) {
		return -Errno.ENOSYS;
	}
}
