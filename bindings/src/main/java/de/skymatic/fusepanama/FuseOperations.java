package de.skymatic.fusepanama;

import java.foreign.memory.Callback;
import java.foreign.memory.LayoutType;
import java.foreign.memory.Pointer;

import com.github.libfuse.fuse_common_h;
import com.github.libfuse.fuse_h;
import usr.include.sys._types._timespec_h;
import usr.include.sys.fcntl_h;
import usr.include.sys.stat_h;
import usr.include.sys.statvfs_h;
import usr.include.utime_h;

public interface FuseOperations {
	
	long SIZEOF_FUSE_OPERATIONS = LayoutType.ofStruct(fuse_h.fuse_operations.class).bytesSize();

	/**
	 * Get file attributes.
	 * <p>
	 * Similar to stat().  The 'st_dev' and 'st_blksize' fields are
	 * ignored.	 The 'st_ino' field is ignored except if the 'use_ino'
	 * mount option is given.
	 */
	int getattr(Pointer<Byte> path, Pointer<stat_h.stat> buf);

	/**
	 * Read the target of a symbolic link
	 * <p>
	 * The buffer should be filled with a null terminated string.  The
	 * buffer size argument includes the space for the terminating
	 * null character.	If the linkname is too long to fit in the
	 * buffer, it should be truncated.	The return value should be 0
	 * for success.
	 */
	int readlink(Pointer<Byte> path, Pointer<Byte> buf, long len);

	/**
	 * @deprecated use {@link #readdir(Pointer, Pointer, Callback, long, Pointer) readdir()} instead
	 */
	int getdir(Pointer<Byte> path, Pointer<fuse_h.fuse_dirhandle> fuse_dirhandlePointer, Callback<fuse_h.FI2> fi2Callback);

	/**
	 * Create a file node
	 * <p>
	 * This is called for creation of all non-directory, non-symlink
	 * nodes.  If the filesystem defines a create() method, then for
	 * regular files that will be called instead.
	 */
	int mknod(Pointer<Byte> path, short mode, int rdev);

	/**
	 * Create a directory
	 * <p>
	 * Note that the mode argument may not have the type specification
	 * bits set, i.e. S_ISDIR(mode) can be false.  To obtain the
	 * correct directory type bits use  mode|S_IFDIR
	 */
	int mkdir(Pointer<Byte> path, short mode);

	/**
	 * Remove a file
	 */
	int unlink(Pointer<Byte> path);

	/**
	 * Remove a directory
	 */
	int rmdir(Pointer<Byte> path);

	/**
	 * Create a symbolic link
	 */
	int symlink(Pointer<Byte> linkname, Pointer<Byte> target);

	/**
	 * Rename a file
	 */
	int rename(Pointer<Byte> oldpath, Pointer<Byte> newpath);

	/**
	 * Create a hard link to a file
	 */
	int link(Pointer<Byte> linkname, Pointer<Byte> target);

	/**
	 * Change the permission bits of a file
	 */
	int chmod(Pointer<Byte> path, short mode);

	/**
	 * Change the owner and group of a file
	 */
	int chown(Pointer<Byte> path, int uid, int gid);

	/**
	 * Change the size of a file
	 */
	int truncate(Pointer<Byte> path, long size);

	/**
	 * Change the access and/or modification times of a file
	 *
	 * @deprecated use {@link #utimens(Pointer, Pointer) utimens()} instead.
	 */
	int utime(Pointer<Byte> path, Pointer<utime_h.utimbuf> utimbufPointer);

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
	int open(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi);

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
	int read(Pointer<Byte> path, Pointer<Byte> buf, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Write data to an open file
	 * <p>
	 * Write should return exactly the number of bytes requested
	 * except on error.	 An exception to this is when the 'direct_io'
	 * mount option is specified (see read operation).
	 * <p>
	 * Changed in version 2.2
	 */
	int write(Pointer<Byte> path, Pointer<Byte> buf, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Get file system statistics
	 * <p>
	 * The 'f_frsize', 'f_favail', 'f_fsid' and 'f_flag' fields are ignored
	 * <p>
	 * Replaced 'struct statfs' parameter with 'struct statvfs' in
	 * version 2.5
	 */
	int statfs(Pointer<Byte> path, Pointer<statvfs_h.statvfs> buf);

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
	int flush(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi);

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
	int release(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Synchronize file contents
	 * <p>
	 * If the datasync parameter is non-zero, then only the user data
	 * should be flushed, not the meta data.
	 * <p>
	 * Changed in version 2.2
	 */
	int fsync(Pointer<Byte> path, int datasync, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Set extended attributes
	 */
	int setxattr(Pointer<Byte> path, Pointer<Byte> name, Pointer<Byte> value, long size, int flags);

	/**
	 * Get extended attributes
	 */
	int getxattr(Pointer<Byte> path, Pointer<Byte> name, Pointer<Byte> value, long size);

	/**
	 * List extended attributes
	 */
	int listxattr(Pointer<Byte> path, Pointer<Byte> list, long size);

	/**
	 * Remove extended attributes
	 */
	int removexattr(Pointer<Byte> path, Pointer<Byte> name);

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
	int opendir(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Read directory
	 * <p>
	 * This supersedes the old getdir() interface.  New applications
	 * should use this.
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
	int readdir(Pointer<Byte> path, Pointer<?> buf, Callback<fuse_h.FI1> filler, long offset, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Release directory
	 * <p>
	 * Introduced in version 2.3
	 */
	int releasedir(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Synchronize directory contents
	 * <p>
	 * If the datasync parameter is non-zero, then only the user data
	 * should be flushed, not the meta data
	 * <p>
	 * Introduced in version 2.3
	 */
	int fsyncdir(Pointer<Byte> path, int datasync, Pointer<fuse_common_h.fuse_file_info> fi);

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
	Pointer<Void> init(Pointer<fuse_common_h.fuse_conn_info> conn);

	/**
	 * Clean up filesystem
	 * <p>
	 * Called on filesystem exit.
	 * <p>
	 * Introduced in version 2.3
	 */
	void destroy(Pointer<?> pointer);

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
	int access(Pointer<Byte> path, int mask);

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
	int create(Pointer<Byte> path, short mode, Pointer<fuse_common_h.fuse_file_info> fi);

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
	int ftruncate(Pointer<Byte> path, long size, Pointer<fuse_common_h.fuse_file_info> fi);

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
	int fgetattr(Pointer<Byte> path, Pointer<stat_h.stat> buf, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Perform POSIX file locking operation
	 * <p>
	 * The cmd argument will be either F_GETLK, F_SETLK or F_SETLKW.
	 * <p>
	 * For the meaning of fields in 'struct flock' see the man page
	 * for fcntl(2).  The l_whence field will always be set to
	 * SEEK_SET.
	 * <p>
	 * For checking lock ownership, the 'fuse_file_info->owner'
	 * argument must be used.
	 * <p>
	 * For F_GETLK operation, the library will first check currently
	 * held locks, and if a conflicting lock is found it will return
	 * information without calling this method.	 This ensures, that
	 * for local locks the l_pid field is correctly filled in.	The
	 * results may not be accurate in case of race conditions and in
	 * the presence of hard links, but it's unlikely that an
	 * application would rely on accurate GETLK results in these
	 * cases.  If a conflicting lock is not found, this method will be
	 * called, and the filesystem may fill out l_pid by a meaningful
	 * value, or it may leave this field zero.
	 * <p>
	 * For F_SETLK and F_SETLKW the l_pid field will be set to the pid
	 * of the process performing the locking operation.
	 * <p>
	 * Note: if this method is not implemented, the kernel will still
	 * allow file locking to work locally.  Hence it is only
	 * interesting for network filesystems and similar.
	 * <p>
	 * Introduced in version 2.6
	 */
	int lock(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, int cmd, Pointer<fcntl_h.flock> lock);

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
	int utimens(Pointer<Byte> path, Pointer<_timespec_h.timespec> tv);

	/**
	 * Map block index within file to block index within device
	 * <p>
	 * Note: This makes sense only for block device backed filesystems
	 * mounted with the 'blkdev' option
	 * <p>
	 * Introduced in version 2.6
	 */
	int bmap(Pointer<Byte> path, long blocksize, Pointer<Long> idx);

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
	int ioctl(Pointer<Byte> path, int cmd, Pointer<?> arg, Pointer<fuse_common_h.fuse_file_info> fi, int flags, Pointer<?> data);

	/**
	 * Poll for IO readiness events
	 * <p>
	 * Note: If ph is non-NULL, the client should notify
	 * when IO readiness events occur by calling
	 * fuse_notify_poll() with the specified ph.
	 * <p>
	 * Regardless of the number of times poll with a non-NULL ph
	 * is received, single notification is enough to clear all.
	 * Notifying more times incurs overhead but doesn't harm
	 * correctness.
	 * <p>
	 * The callee is responsible for destroying ph with
	 * fuse_pollhandle_destroy() when no longer in use.
	 * <p>
	 * Introduced in version 2.8
	 */
	int poll(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, Pointer<fuse_common_h.fuse_pollhandle> ph, Pointer<Integer> reventsp);

	/**
	 * Write contents of buffer to an open file
	 * <p>
	 * Similar to the write() method, but data is supplied in a
	 * generic buffer.  Use fuse_buf_copy() to transfer data to
	 * the destination.
	 * <p>
	 * Introduced in version 2.9
	 */
	int writeBuf(Pointer<Byte> path, Pointer<fuse_common_h.fuse_bufvec> buf, long offset, Pointer<fuse_common_h.fuse_file_info> fi);

	/**
	 * Store data from an open file in a buffer
	 * <p>
	 * Similar to the read() method, but data is stored and
	 * returned in a generic buffer.
	 * <p>
	 * No actual copying of data has to take place, the source
	 * file descriptor may simply be stored in the buffer for
	 * later data transfer.
	 * <p>
	 * The buffer must be allocated dynamically and stored at the
	 * location pointed to by bufp.  If the buffer contains memory
	 * regions, they too must be allocated using malloc().  The
	 * allocated memory will be freed by the caller.
	 * <p>
	 * Introduced in version 2.9
	 */
	int readBuf(Pointer<Byte> path, Pointer<? extends Pointer<fuse_common_h.fuse_bufvec>> bufp, long size, long offset, Pointer<fuse_common_h.fuse_file_info> fi);

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
	int flock(Pointer<Byte> path, Pointer<fuse_common_h.fuse_file_info> fi, int op);

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
	int fallocate(Pointer<Byte> path, int mode, long offset, long length, Pointer<fuse_common_h.fuse_file_info> fi);
}
