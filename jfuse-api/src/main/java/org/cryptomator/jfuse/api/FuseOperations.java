package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Set;

/**
 * The file system operations.
 *
 * @see <a href="https://libfuse.github.io/doxygen/structfuse__operations.html">fuse_operations</a>
 */
@SuppressWarnings("unused")
public interface FuseOperations {

	enum Operation {
		ACCESS,
		CHMOD,
		CREATE,
		DESTROY,
		GET_ATTR,
		INIT,
		MKDIR,
		OPEN,
		OPEN_DIR,
		READ,
		READLINK,
		READ_DIR,
		RELEASE,
		RELEASE_DIR,
		RENAME,
		RMDIR,
		STATFS,
		SYMLINK,
		TRUNCATE,
		UNLINK,
		UTIMENS,
		WRITE
	}

	/**
	 * "Plus" mode.
	 * <p>
	 * The kernel wants to prefill the inode cache during readdir. The filesystem may honour this by filling in the
	 * attributes and setting {@link DirFiller#FUSE_FILL_DIR_PLUS FUSE_FILL_DIR_PLUS} for the filler function.
	 * The filesystem may also just ignore this flag completely.
	 */
	@SuppressWarnings("PointlessBitwiseExpression")
	int FUSE_READDIR_PLUS = 1 << 0;

	/**
	 * @return The error codes from <code>errno.h</code> for the current platform.
	 * @see FuseBuilder#errno()
	 */
	Errno errno();

	/**
	 * @return The set of supported operations.
	 */
	Set<Operation> supportedOperations();

	/**
	 * Get file attributes.
	 * <p>
	 * Similar to stat().  The 'st_dev' and 'st_blksize' fields are
	 * ignored. The 'st_ino' field is ignored except if the 'use_ino'
	 * mount option is given. In that case it is passed to userspace,
	 * but libfuse and the kernel will still assign a different
	 * inode for internal use (called the "nodeid").
	 * <p>
	 * This method doubles as <code>fgetattr</code> in libfuse2
	 *
	 * @param path file path
	 * @param stat file information
	 * @param fi   will always be <code>null</code> if the file is not currently open,
	 *             but may also be <code>null</code> if the file is open.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int getattr(String path, Stat stat, @Nullable FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Read the target of a symbolic link.
	 *
	 * @param path file path
	 * @param buf  The buffer should be filled with a null terminated string.
	 * @param len  The buffer size (including the terminating null character). If the linkname is too long to fit in the
	 *             buffer, it should be truncated.
	 * @return The return value should be 0
	 */
	default int readlink(String path, ByteBuffer buf, long len) {
		return -errno().enosys();
	}

	/**
	 * Create a file node.
	 * <p>
	 * This is called for creation of all non-directory, non-symlink
	 * nodes.  If the filesystem defines a create() method, then for
	 * regular files that will be called instead.
	 *
	 * @param path file path
	 * @param mode file mode and type of node to create (using bitwise OR)
	 * @param rdev ignored if mode does not contain <code>S_IFCHR</code> or <code>S_IFBLK</code>
	 * @return 0 on success or negated error code (-errno)
	 */
	default int mknod(String path, short mode, int rdev) {
		return -errno().enosys();
	}

	/**
	 * Create a directory.
	 * <p>
	 * Note that the mode argument may not have the type specification
	 * bits set, i.e. S_ISDIR(mode) can be false.  To obtain the
	 * correct directory type bits use  mode|S_IFDIR
	 *
	 * @param path file path
	 * @param mode file mode (using bitwise OR)
	 * @return 0 on success or negated error code (-errno)
	 */
	default int mkdir(String path, int mode) {
		return -errno().enosys();
	}

	/**
	 * Remove a file
	 *
	 * @param path file path
	 * @return 0 on success or negated error code (-errno)
	 */
	default int unlink(String path) {
		return -errno().enosys();
	}

	/**
	 * Remove a directory
	 *
	 * @param path file path
	 * @return 0 on success or negated error code (-errno)
	 */
	default int rmdir(String path) {
		return -errno().enosys();
	}

	/**
	 * Create a symbolic link
	 *
	 * @param linkname file path
	 * @param target   content of the link
	 * @return 0 on success or negated error code (-errno)
	 */
	default int symlink(String linkname, String target) {
		return -errno().enosys();
	}

	/**
	 * Rename a file
	 *
	 * @param oldpath old file path
	 * @param newpath new file path
	 * @param flags   may be `RENAME_EXCHANGE` or `RENAME_NOREPLACE`. If
	 *                RENAME_NOREPLACE is specified, the filesystem must not
	 *                overwrite *newname* if it exists and return an error
	 *                instead. If `RENAME_EXCHANGE` is specified, the filesystem
	 *                must atomically exchange the two files, i.e. both must
	 *                exist and neither may be deleted.
	 * @return 0 on success or negated error code (-errno)
	 */
	// TODO create enum for flags
	default int rename(String oldpath, String newpath, int flags) {
		return -errno().enosys();
	}

	/**
	 * Create a hard link to a file
	 *
	 * @param linkname file path
	 * @param target   content of the link
	 * @return 0 on success or negated error code (-errno)
	 */
	default int link(String linkname, String target) {
		return -errno().enosys();
	}

	/**
	 * Change the permission bits of a file
	 *
	 * @param path file path
	 * @param mode file mode (using bitwise OR)
	 * @param fi   will always be <code>null</code> if the file is not currently open,
	 *             but may also be <code>null</code> if the file is open.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int chmod(String path, int mode, @Nullable FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Change the owner and group of a file
	 *
	 * @param path file path
	 * @param uid  user id
	 * @param gid  group id
	 * @param fi   will always be <code>null</code> if the file is not currently open,
	 *             but may also be <code>null</code> if the file is open.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int chown(String path, int uid, int gid, @Nullable FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Change the size of a file.
	 * <p>
	 * Unless FUSE_CAP_HANDLE_KILLPRIV is disabled, this method is
	 * expected to reset the setuid and setgid bits.
	 * <p>
	 * This method doubles as <code>ftruncate</code> in libfuse2
	 *
	 * @param path file path
	 * @param size new size of the file
	 * @param fi   will always be <code>null</code> if the file is not currently open,
	 *             but may also be <code>null</code> if the file is open.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int truncate(String path, long size, @Nullable FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Open a file.
	 * <p>
	 * Open flags are available in fi->flags. The following rules apply.
	 * <ul>
	 *     <li>Creation (O_CREAT, O_EXCL, O_NOCTTY) flags will be filtered out / handled by the kernel.</li>
	 *     <li>Access modes (O_RDONLY, O_WRONLY, O_RDWR, O_EXEC, O_SEARCH) should be used by the filesystem to check if
	 *     the operation is permitted.  If the ``-o default_permissions`` mount option is given, this check is already
	 *     done by the kernel before calling open() and may thus be omitted by the filesystem.</li>
	 * 	  <li>When writeback caching is enabled, the kernel may send read requests even for files opened with O_WRONLY.
	 * 	  Thefilesystem should be prepared to handle this.</li>
	 * 	  <li>When writeback caching is disabled, the filesystem is expected to properly handle the O_APPEND flag and
	 * 	  ensurethat each write is appending to the end of the file.</li>
	 * 	  <li>When writeback caching is enabled, the kernel will handle O_APPEND. However, unless all changes to the
	 * 	  file come through the kernel this will not work reliably. The filesystem should thus either ignore the
	 * 	  O_APPEND flag (and let the kernel handle it), or return an error (indicating that reliably O_APPEND is
	 * 	  not available).</li>
	 * </ul>
	 * Filesystem may store an arbitrary file handle (pointer, index, etc) in {@link FileInfo#setFh(long) fi->fh},
	 * and use this in other all other file operations (read, write, flush, release, fsync).
	 * <p>
	 * Filesystem may also implement stateless file I/O and not store anything in {@link FileInfo#setFh(long) fi->fh}.
	 * <p>
	 * There are also some flags (direct_io, keep_cache) which the filesystem may set in fi, to change the way the file
	 * is opened. See fuse_file_info structure in fuse_common.h for more details.
	 * <p>
	 * If this request is answered with an error code of ENOSYS and FUSE_CAP_NO_OPEN_SUPPORT is set in
	 * `fuse_conn_info.capable`, this is treated as success and future calls to open will also succeed without being
	 * send to the filesystem process.
	 *
	 * @param path file path
	 * @param fi   file info, which may be used to store a file handle
	 * @return 0 on success or negated error code (-errno)
	 */
	default int open(String path, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Read data from an open file.
	 * <p>
	 * Read should return exactly the number of bytes requested except
	 * on EOF or error, otherwise the rest of the data will be
	 * substituted with zeroes.	 An exception to this is when the
	 * 'direct_io' mount option is specified, in which case the return
	 * value of the read system call will reflect the return value of
	 * this operation.
	 *
	 * @param path   file path
	 * @param buf    the buffer to read into
	 * @param count  number of bytes to read
	 * @param offset position in the file to start reading
	 * @param fi     file info
	 * @return number of bytes read or negated error code (-errno)
	 */
	default int read(String path, ByteBuffer buf, long count, long offset, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Write data to an open file.
	 * <p>
	 * Write should return exactly the number of bytes requested
	 * except on error.	 An exception to this is when the 'direct_io'
	 * mount option is specified (see read operation).
	 * <p>
	 * Unless FUSE_CAP_HANDLE_KILLPRIV is disabled, this method is
	 * expected to reset the setuid and setgid bits.
	 *
	 * @param path   file path
	 * @param buf    the buffer containing the data to be written
	 * @param count  number of bytes to write
	 * @param offset position in the file to write to
	 * @param fi     file info
	 * @return number of bytes written or negated error code (-errno)
	 */
	default int write(String path, ByteBuffer buf, long count, long offset, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Get file system statistics.
	 * <p>
	 * The 'f_favail', 'f_fsid' and 'f_flag' fields are ignored
	 *
	 * @param path    file path of any file within the file system
	 * @param statvfs The statistics object to be filled with data
	 * @return 0 on success or negated error code (-errno)
	 */
	default int statfs(String path, Statvfs statvfs) {
		return -errno().enosys();
	}

	/**
	 * Possibly flush cached data.
	 * <p>
	 * BIG NOTE: This is not equivalent to fsync().  It's not a
	 * request to sync dirty data.
	 * <p>
	 * Flush is called on each close() of a file descriptor, as opposed to
	 * release which is called on the close of the last file descriptor for
	 * a file.  Under Linux, errors returned by flush() will be passed to
	 * userspace as errors from close(), so flush() is a good place to write
	 * back any cached dirty data. However, many applications ignore errors
	 * on close(), and on non-Linux systems, close() may succeed even if flush()
	 * returns an error. For these reasons, filesystems should not assume
	 * that errors returned by flush will ever be noticed or even
	 * delivered.
	 * <p>
	 * NOTE: The flush() method may be called more than once for each
	 * open().  This happens if more than one file descriptor refers to an
	 * open file handle, e.g. due to dup(), dup2() or fork() calls.  It is
	 * not possible to determine if a flush is final, so each flush should
	 * be treated equally.  Multiple write-flush sequences are relatively
	 * rare, so this shouldn't be a problem.
	 * <p>
	 * Filesystems shouldn't assume that flush will be called at any
	 * particular point.  It may be called more times than expected, or not
	 * at all.
	 *
	 * @param path file path
	 * @param fi   file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int flush(String path, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Release an open file.
	 * <p>
	 * Release is called when there are no more references to an open
	 * file: all file descriptors are closed and all memory mappings
	 * are unmapped.
	 * <p>
	 * For every open() call there will be exactly one release() call
	 * with the same flags and file handle.  It is possible to
	 * have a file opened more than once, in which case only the last
	 * release will mean, that no more reads/writes will happen on the
	 * file.  The return value of release is ignored.
	 *
	 * @param path file path
	 * @param fi   file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int release(String path, FileInfo fi) {
		return 0;
	}

	/**
	 * Synchronize file contents
	 *
	 * @param path     file path
	 * @param datasync if non-zero, then only the user data should be flushed, not the meta data.
	 * @param fi       file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int fsync(String path, int datasync, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Set extended attributes
	 *
	 * @param path  file path
	 * @param name  attribute name
	 * @param value attribute value
	 * @param size  number of bytes of the value TODO: can this be combined with the value buffer?
	 * @param flags defaults to zero, which creates <em>or</em> replaces the attribute. For fine-grained atomic control,
	 *              <code>XATTR_CREATE</code> or <code>XATTR_REPLACE</code> may be used.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int setxattr(String path, String name, ByteBuffer value, long size, int flags) {
		return -errno().enosys();
	}

	/**
	 * Get extended attributes
	 *
	 * @param path  file path
	 * @param name  attribute name
	 * @param value attribute value
	 * @param size  size of the value buffer TODO: can this be combined with the value buffer?
	 * @return the non-negative value size or negated error code (-errno)
	 */
	default int getxattr(String path, String name, ByteBuffer value, long size) {
		return -errno().enosys();
	}

	/**
	 * List extended attributes
	 *
	 * @param path file path
	 * @param list consecutive list of null-terminated attribute names (as many as fit into the buffer)
	 * @param size size of the list buffer TODO: can this be combined with the value buffer?
	 * @return the non-negative list size or negated error code (-errno)
	 */
	default int listxattr(String path, ByteBuffer list, long size) {
		return -errno().enosys();
	}

	/**
	 * Remove extended attributes
	 *
	 * @param path file path
	 * @param name attribute name
	 * @return 0 on success or negated error code (-errno)
	 */
	default int removexattr(String path, String name) {
		return -errno().enosys();
	}

	/**
	 * Open directory.
	 * <p>
	 * Unless the 'default_permissions' mount option is given,
	 * this method should check if opendir is permitted for this
	 * directory. Optionally opendir may also return an arbitrary
	 * {@link FileInfo#setFh(long) filehandle in the fuse_file_info structure},
	 * which will be passed to readdir, releasedir and fsyncdir.
	 *
	 * @param path directory path
	 * @param fi   file info, which may be used to store a file handle
	 * @return 0 on success or negated error code (-errno)
	 */
	default int opendir(String path, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Read directory.
	 * <p>
	 * The filesystem may choose between two modes of operation:
	 * <ol>
	 *     <li>The readdir implementation ignores the offset parameter, and
	 * 	  passes zero to the filler function's offset.  The filler
	 * 	  function will not return '1' (unless an error happens), so the
	 * 	  whole directory is read in a single readdir operation.</li>
	 *     <li>The readdir implementation keeps track of the offsets of the
	 * 	  directory entries.  It uses the offset parameter and always
	 * 	  passes non-zero offset to the filler function.  When the buffer
	 * 	  is full (or an error happens) the filler function will return
	 * 	  '1'.</li>
	 * </ol>
	 *
	 * @param path   directory path
	 * @param filler the fill function
	 * @param offset the offset
	 * @param fi     file info
	 * @param flags  When {@link #FUSE_READDIR_PLUS} is not set, only some parameters of the
	 *               fill function (the {@code filler} parameter) are actually used:
	 *               The file type (which is part of {@link Stat#getMode()}) is used. And if
	 *               fuse_config::use_ino is set, the inode (stat::st_ino) is also
	 *               used. The other fields are ignored when {@code READ_DIR_PLUS} is not
	 *               set.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int readdir(String path, DirFiller filler, long offset, FileInfo fi, int flags) {
		return -errno().enosys();
	}

	/**
	 * Release directory
	 *
	 * @param path directory path. If the directory has been removed after the call to opendir, the
	 *             path parameter will be <code>null</code>
	 * @param fi   file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int releasedir(@Nullable String path, FileInfo fi) {
		return 0;
	}

	/**
	 * Synchronize directory contents
	 *
	 * @param path     directory path. If the directory has been removed after the call to opendir, the
	 *                 path parameter will be <code>null</code>
	 * @param datasync if non-zero, then only the user data should be flushed, not the meta data
	 * @param fi       file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int fsyncdir(@Nullable String path, int datasync, FileInfo fi) {
		return -errno().enosys();
	}

	/**
	 * Initialize filesystem
	 *
	 * @param conn FUSE information
	 */
	default void init(FuseConnInfo conn, @Nullable FuseConfig cfg) {
		// no-op
	}

	/**
	 * Clean up filesystem
	 * <p>
	 * Called on filesystem exit.
	 */
	default void destroy() {
		// no-op
	}

	/**
	 * Check file access permissions.
	 * <p>
	 * This will be called for the access() system call.  If the
	 * 'default_permissions' mount option is given, this method is not
	 * called.
	 * <p>
	 * This method is not called under Linux kernel versions 2.4.x
	 *
	 * @param path file path
	 * @param mask bitwise OR of file access checks
	 * @return 0 on success or negated error code (-errno)
	 */
	default int access(String path, int mask) {
		return -errno().enosys();
	}

	/**
	 * Create and open a file.
	 * <p>
	 * If the file does not exist, first create it with the specified
	 * mode, and then open it.
	 * <p>
	 * If this method is not implemented or under Linux kernel
	 * versions earlier than 2.6.15, the mknod() and open() methods
	 * will be called instead.
	 *
	 * @param path file path
	 * @param mode mode used to create the file if it does not exist yet
	 * @param fi   file info, which may be used to store a file handle
	 * @return 0 on success or negated error code (-errno)
	 */
	default int create(String path, int mode, FileInfo fi) {
		return -errno().enosys();
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
//		return -errno().enosys();
//	}

	/**
	 * Change the access and modification times of a file with
	 * nanosecond resolution.
	 * <p>
	 * This supersedes the old utime() interface.  New applications
	 * should use this.
	 * <p>
	 * See the utimensat(2) man page for details.
	 *
	 * @param path  file path
	 * @param atime last access time
	 * @param mtime last modified time
	 * @param fi    will always be <code>null</code> if the file is not currently open,
	 *              but may also be <code>null</code> if the file is open.
	 * @return 0 on success or negated error code (-errno)
	 */
	default int utimens(String path, TimeSpec atime, TimeSpec mtime, @Nullable FileInfo fi) {
		return -errno().enosys();
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
//		return -errno().enosys();
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
	 * Note: the unsigned long request submitted by the application
	 * is truncated to 32 bits.
	 *
	 * @param path  file path
	 * @param cmd   the request
	 * @param arg   any arguments
	 * @param fi    file info
	 * @param flags if containing FUSE_IOCTL_DIR then the <code>fi</code> refers to a directory file handle.
	 * @param data  data (depends on <code>cmd</code> and may be <code>null</code>)
	 * @return 0 on success or negated error code (-errno)
	 */
	default int ioctl(String path, int cmd, ByteBuffer arg, FileInfo fi, int flags, @Nullable ByteBuffer data) {
		return -errno().enosys();
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
//		return -errno().enosys();
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
//		return -errno().enosys();
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
//		return -errno().enosys();
//	}

	/**
	 * Perform BSD file locking operation.
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
	 *
	 * @param path file path
	 * @param fi   file info
	 * @param op   one of <code>LOCK_SH</code>, <code>LOCK_EX</code> or <code>LOCK_UN</code>
	 * @return 0 on success or negated error code (-errno)
	 */
	default int flock(String path, FileInfo fi, int op) {
		return -errno().enosys();
	}

	/**
	 * Allocates space for an open file.
	 * <p>
	 * This function ensures that required space is allocated for specified
	 * file.  If this function returns success then any subsequent write
	 * request to specified range is guaranteed not to fail because of lack
	 * of space on the file system media.
	 *
	 * @param path   file path
	 * @param mode   the operation to be performed
	 * @param offset start of the allocated region
	 * @param length number of bytes to allocate in this file
	 * @param fi     file info
	 * @return 0 on success or negated error code (-errno)
	 */
	default int fallocate(String path, int mode, long offset, long length, FileInfo fi) {
		return -errno().enosys();
	}
}
