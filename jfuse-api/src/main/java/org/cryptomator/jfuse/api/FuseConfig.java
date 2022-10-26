package org.cryptomator.jfuse.api;

/**
 * Configuration of the high-level API
 * <p>
 * This structure is initialized from the arguments passed to fuse_new(), and then passed to the file system's
 * {@link FuseOperations#init(FuseConnInfo, FuseConfig) init()} handler which should ensure that the configuration
 * is compatible with the file system implementation.
 *
 * @since libFUSE 3.0
 */
public interface FuseConfig {

	/**
	 * If {@code set_gid} is non-zero, the {@link Stat#getGid() st_gid} attribute of each file is overwritten with the value of {@link #gid()}.
	 *
	 * @return {@code set_gid} value
	 * @see #gid()
	 */
	int setGid();

	/**
	 * The value used to overwrite {@link Stat#getGid() st_gid} during {@link FuseOperations#getattr(String, Stat, FileInfo) getattr} calls.
	 *
	 * @return {@code gid} value
	 * @see #setGid()
	 */
	int gid();

	/**
	 * If {@code set_uid} is non-zero, the {@link Stat#getUid() st_uid} attribute of each file is overwritten with the value of {@link #uid()}.
	 *
	 * @return {@code set_uid} value
	 * @see #uid()
	 */
	int setUid();

	/**
	 * The value used to overwrite {@link Stat#getUid() st_uid} during {@link FuseOperations#getattr(String, Stat, FileInfo) getattr} calls.
	 *
	 * @return {@code gid} value
	 * @see #setUid()
	 */
	int uid();

	/**
	 * If {@code set_mode} is non-zero, the any permissions bits set in {@link #umask()} are unset in the {@link Stat#getMode() st_mode} attribute of each file.
	 *
	 * @return {@code set_mode} value
	 * @see #umask()
	 */
	int setMode();

	/**
	 * The bits to unset in {@link Stat#getMode() st_mode}
	 *
	 * @return {@code umask} value
	 * @see #setMode()
	 */
	int umask();

	/**
	 * The timeout in seconds for which name lookups will be cached.
	 *
	 * @return {@code entry_timeout} value
	 */
	double entryTimeout();

	/**
	 * The timeout in seconds for which a negative lookup will be cached. This means, that if file did not exist (lookup
	 * returned {@link Errno#enoent() ENOENT}), the lookup will only be redone after the timeout, and the file/directory
	 * will be assumed to not exist until then. A value of zero means that negative lookups are not cached.
	 *
	 * @return {@code negative_timeout} value
	 */
	double negativeTimeout();

	/**
	 * The timeout in seconds for which file/directory attributes (as returned by e.g. the
	 * {@link FuseOperations#getattr(String, Stat, FileInfo) getattr} handler) are cached.
	 *
	 * @return {@code attr_timeout} value
	 */
	double attrTimeout();

	/**
	 * Allow requests to be interrupted
	 *
	 * @return {@code intr} value
	 */
	int intr();

	/**
	 * Specify which signal number to send to the filesystem when a request is interrupted.
	 * <p>
	 * The default is hardcoded to {@code USR1}.
	 *
	 * @return {@code intr_signal} value
	 */
	int intrSignal();

	/**
	 * Normally, FUSE assigns inodes to paths only for as long as the kernel is aware of them. With this option inodes are
	 * instead remembered for at least this many seconds. This will require more memory, but may be necessary when using
	 * applications that make use of inode numbers.
	 * <p>
	 * A number of -1 means that inodes will be remembered for the entire life-time of the file-system process.
	 *
	 * @return {@code remember} value
	 */
	int remember();

	/**
	 * The default behavior is that if an open file is deleted, the file is renamed to a hidden file (.fuse_hiddenXXX), and
	 * only removed when the file is finally released.  This relieves the filesystem implementation of having to deal
	 * with this problem. This option disables the hiding behavior, and files are removed immediately in an unlink
	 * operation (or in a rename operation which overwrites an existing file).
	 * <p>
	 * It is recommended that you not use the hard_remove option. When hard_remove is set, the following libc
	 * functions fail on unlinked files (returning errno of {@link Errno#enoent() ENOENT}): read(2), write(2), fsync(2),
	 * close(2), f*xattr(2), ftruncate(2), fstat(2), fchmod(2), fchown(2)
	 *
	 * @return {@code hard_remove} value
	 */
	int hardRemove();

	/**
	 * Honor the st_ino field in the functions {@link FuseOperations#getattr(String, Stat, FileInfo) getattr()} and
	 * {@link DirFiller#fill(String, java.util.function.Consumer, long, int) fill_dir()}. This value is used to fill in the st_ino field
	 * in the stat(2), lstat(2), fstat(2) functions and the d_ino field in the readdir(2) function. The filesystem does not
	 * have to guarantee uniqueness, however some applications rely on this value being unique for the whole filesystem.
	 * <p>
	 * Note that this does *not* affect the inode that libfuse and the kernel use internally (also called the "nodeid").
	 *
	 * @return {@code use_ino} value
	 */
	int useIno();

	/**
	 * If use_ino option is not given, still try to fill in the d_ino field in readdir(2). If the name was previously
	 * looked up, and is still in the cache, the inode number found there will be used.  Otherwise it will be set to -1.
	 * If use_ino option is given, this option is ignored.
	 *
	 * @return {@code readdir_ino} value
	 */
	int readdirIno();

	/**
	 * This option disables the use of page cache (file content cache) in the kernel for this filesystem. This has several affects:
	 * <ol>
	 * 	<li>Each read(2) or write(2) system call will initiate one or more read or write operations, data will not be
	 * 		cached in the kernel.</li>
	 * 	<li>The return value of the {@link FuseOperations#read(String, java.nio.ByteBuffer, long, long, FileInfo) read()} and
	 *      {@link FuseOperations#write(String, java.nio.ByteBuffer, long, long, FileInfo) write()} system calls will correspond
	 * 		to the return values of the read and write operations. This is useful for example if the file size is not
	 * 		known in advance (before reading it).</li>
	 * </ol>
	 * Internally, enabling this option causes fuse to set the {@code direct_io} field of
	 * {@link FileInfo fuse_file_info} - overwriting any value that was put there by the file system.
	 *
	 * @return {@code direct_io} value
	 */
	int directIo();

	/**
	 * This option disables flushing the cache of the file contents on every open(2).  This should only be enabled on
	 * filesystems where the file data is never changed externally (not through the mounted FUSE filesystem).  Thus
	 * it is not suitable for network filesystems and other intermediate filesystems.
	 * <p>
	 * NOTE: if this option is not specified (and neither direct_io) data is still cached after the open(2), so a
	 * read(2) system call will not always initiate a read operation.
	 * <p>
	 * Internally, enabling this option causes fuse to set the {@code keep_cache} field of
	 * {@link FileInfo fuse_file_info} - overwriting any value that was put there by the file system.
	 *
	 * @return {@code kernel_cache} value
	 */
	int kernelCache();

	/**
	 * This option is an alternative to {@link #kernelCache() kernel_cache}. Instead of unconditionally keeping cached
	 * data, the cached data is invalidated on open(2) if if the modification time or the size of the file has changed
	 * since it was last opened.
	 *
	 * @return {@code auto_cache} value
	 */
	int autoCache();

	/**
	 * By default, fuse waits for all pending writes to complete and calls the FLUSH operation on close(2) of every fuse fd.
	 * With this option, wait and FLUSH are not done for read-only fuse fd, similar to the behavior of NFS/SMB clients.
	 *
	 * @return {@code no_rofd_flush} value or 0 if not supported
	 * @since libFUSE 3.3
	 */
	int noRofdFlush();

	/**
	 * If {@code ac_attr_timeout_set} is non-zero, the {@link #acAttrTimeout()} ac_attr_timeout} is applied.
	 *
	 * @return {@code ac_attr_timeout_set} value
	 * @see #acAttrTimeout()
	 */
	int acAttrTimeoutSet();

	/**
	 * The timeout in seconds for which file attributes are cached for the purpose of checking if auto_cache should
	 * flush the file data on open.
	 *
	 * @return {@code ac_attr_timeout} value
	 * @see #acAttrTimeoutSet()
	 */
	double acAttrTimeout();

	/**
	 * If this option is given the file-system handlers for the
	 * following operations will not receive path information:
	 * read, write, flush, release, fallocate, fsync, readdir,
	 * releasedir, fsyncdir, lock, ioctl and poll.
	 * <p>
	 * For the truncate, getattr, chmod, chown and utimens
	 * operations the path will be provided only if the struct
	 * fuse_file_info argument is NULL.
	 *
	 * @return {@code nullpath_ok} value
	 */
	int nullpathOk();

}
