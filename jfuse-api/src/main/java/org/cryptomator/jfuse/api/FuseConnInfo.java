package org.cryptomator.jfuse.api;

/**
 * Fuse connection info struct used in {@link FuseOperations#init(FuseConnInfo, FuseConfig)} method.
 * <p>
 * This struct is a union of the libfuse2 and libfuse3 definition. If an accessor method or macro is not supported by used libfuse version, it is documented in the javadoc.
 * See also @code{fuse_common.h} of libfuse2 or 3.
 */
public interface FuseConnInfo {

	/**
	 * Indicates that the filesystem supports asynchronous read requests.
	 * <p>
	 * If this capability is not requested/available, the kernel will
	 * ensure that there is at most one pending read request per
	 * file-handle at any time, and will attempt to order read requests by
	 * increasing offset.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 */
	@SuppressWarnings("PointlessBitwiseExpression")
	int FUSE_CAP_ASYNC_READ = (1 << 0);

	/**
	 * Indicates that the filesystem supports "remote" locking.
	 * <p>
	 * This feature is enabled by default when supported by the kernel,
	 * and if getlk() and setlk() handlers are implemented.
	 */
	int FUSE_CAP_POSIX_LOCKS = (1 << 1);

	/**
	 * Indicates that the filesystem supports the O_TRUNC open flag.  If
	 * disabled, and an application specifies O_TRUNC, fuse first calls
	 * truncate() and then open() with O_TRUNC filtered out.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 */
	int FUSE_CAP_ATOMIC_O_TRUNC = (1 << 3);

	/**
	 * Indicates that the filesystem supports lookups of "." and "..".
	 * <p>
	 * This feature is disabled by default.
	 */
	int FUSE_CAP_EXPORT_SUPPORT = (1 << 4);

	/**
	 * Indicates that the filesystem can handle write size larger than 4kB
	 * <p>
	 * libFUSE 2.x only
	 */
	int FUSE_CAP_BIG_WRITES = (1 << 5);

	/**
	 * Indicates that the kernel should not apply the umask to the
	 * file mode on create operations.
	 * <p>
	 * This feature is disabled by default.
	 */
	int FUSE_CAP_DONT_MASK = (1 << 6);

	/**
	 * Indicates that libfuse should try to use splice() when writing to
	 * the fuse device. This may improve performance.
	 * <p>
	 * This feature is disabled by default.
	 */
	int FUSE_CAP_SPLICE_WRITE = (1 << 7);

	/**
	 * Indicates that libfuse should try to move pages instead of copying when
	 * writing to / reading from the fuse device. This may improve performance.
	 * <p>
	 * This feature is disabled by default.
	 */
	int FUSE_CAP_SPLICE_MOVE = (1 << 8);

	/**
	 * Indicates that libfuse should try to use splice() when reading from
	 * the fuse device. This may improve performance.
	 * <p>
	 * This feature is enabled by default when supported by the kernel and
	 * if the filesystem implements a write_buf() handler.
	 */
	int FUSE_CAP_SPLICE_READ = (1 << 9);

	/**
	 * If set, the calls to flock(2) will be emulated using POSIX locks and must
	 * then be handled by the filesystem's setlock() handler.
	 * <p>
	 * If not set, flock(2) calls will be handled by the FUSE kernel module
	 * internally (so any access that does not go through the kernel cannot be taken
	 * into account).
	 * <p>
	 * This feature is enabled by default when supported by the kernel and
	 * if the filesystem implements a flock() handler.
	 */
	int FUSE_CAP_FLOCK_LOCKS = (1 << 10);

	/**
	 * Indicates that the filesystem supports ioctl's on directories.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 */
	int FUSE_CAP_IOCTL_DIR = (1 << 11);

	/**
	 * Traditionally, while a file is open the FUSE kernel module only
	 * asks the filesystem for an update of the file's attributes when a
	 * client attempts to read beyond EOF. This is unsuitable for
	 * e.g. network filesystems, where the file contents may change
	 * without the kernel knowing about it.
	 * <p>
	 * If this flag is set, FUSE will check the validity of the attributes
	 * on every read. If the attributes are no longer valid (i.e., if the
	 * *attr_timeout* passed to fuse_reply_attr() or set in `struct
	 * fuse_entry_param` has passed), it will first issue a `getattr`
	 * request. If the new mtime differs from the previous value, any
	 * cached file *contents* will be invalidated as well.
	 * <p>
	 * This flag should always be set when available. If all file changes
	 * go through the kernel, *attr_timeout* should be set to a very large
	 * number to avoid unnecessary getattr() calls.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_AUTO_INVAL_DATA = (1 << 12);

	/**
	 * Indicates that the filesystem supports readdirplus.
	 * <p>
	 * This feature is enabled by default when supported by the kernel and if the
	 * filesystem implements a readdirplus() handler.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_READDIRPLUS = (1 << 13);

	/**
	 * Indicates that the filesystem supports adaptive readdirplus.
	 * <p>
	 * If FUSE_CAP_READDIRPLUS is not set, this flag has no effect.
	 * <p>
	 * If FUSE_CAP_READDIRPLUS is set and this flag is not set, the kernel
	 * will always issue readdirplus() requests to retrieve directory
	 * contents.
	 * <p>
	 * If FUSE_CAP_READDIRPLUS is set and this flag is set, the kernel
	 * will issue both readdir() and readdirplus() requests, depending on
	 * how much information is expected to be required.
	 * <p>
	 * As of Linux 4.20, the algorithm is as follows: when userspace
	 * starts to read directory entries, issue a READDIRPLUS request to
	 * the filesystem. If any entry attributes have been looked up by the
	 * time userspace requests the next batch of entries continue with
	 * READDIRPLUS, otherwise switch to plain READDIR.  This will reasult
	 * in eg plain "ls" triggering READDIRPLUS first then READDIR after
	 * that because it doesn't do lookups.  "ls -l" should result in all
	 * READDIRPLUS, except if dentries are already cached.
	 * <p>
	 * This feature is enabled by default when supported by the kernel and
	 * if the filesystem implements both a readdirplus() and a readdir()
	 * handler.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_READDIRPLUS_AUTO = (1 << 14);

	/**
	 * Indicates that the filesystem supports asynchronous direct I/O submission.
	 * <p>
	 * If this capability is not requested/available, the kernel will ensure that
	 * there is at most one pending read and one pending write request per direct
	 * I/O file-handle at any time.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_ASYNC_DIO = (1 << 15);

	/**
	 * Indicates that writeback caching should be enabled. This means that
	 * individual write request may be buffered and merged in the kernel
	 * before they are send to the filesystem.
	 * <p>
	 * This feature is disabled by default.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_WRITEBACK_CACHE = (1 << 16);

	/**
	 * Indicates support for zero-message opens. If this flag is set in
	 * the `capable` field of the `fuse_conn_info` structure, then the
	 * filesystem may return `ENOSYS` from the open() handler to indicate
	 * success. Further attempts to open files will be handled in the
	 * kernel. (If this flag is not set, returning ENOSYS will be treated
	 * as an error and signaled to the caller).
	 * <p>
	 * Setting (or unsetting) this flag in the `want` field has *no
	 * effect*.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_NO_OPEN_SUPPORT = (1 << 17);

	/**
	 * Indicates support for parallel directory operations. If this flag
	 * is unset, the FUSE kernel module will ensure that lookup() and
	 * readdir() requests are never issued concurrently for the same
	 * directory.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_PARALLEL_DIROPS = (1 << 18);

	/**
	 * Indicates support for POSIX ACLs.
	 * <p>
	 * If this feature is enabled, the kernel will cache and have
	 * responsibility for enforcing ACLs. ACL will be stored as xattrs and
	 * passed to userspace, which is responsible for updating the ACLs in
	 * the filesystem, keeping the file mode in sync with the ACL, and
	 * ensuring inheritance of default ACLs when new filesystem nodes are
	 * created. Note that this requires that the file system is able to
	 * parse and interpret the xattr representation of ACLs.
	 * <p>
	 * Enabling this feature implicitly turns on the {@code default_permissions}
	 * mount option (even if it was not passed to mount(2)).
	 * <p>
	 * This feature is disabled by default.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_POSIX_ACL = (1 << 19);

	/**
	 * Indicates that the filesystem is responsible for unsetting
	 * setuid and setgid bits when a file is written, truncated, or
	 * its owner is changed.
	 * <p>
	 * This feature is enabled by default when supported by the kernel.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_HANDLE_KILLPRIV = (1 << 20);

	/**
	 * Indicates that the kernel supports caching symlinks in its page cache.
	 * <p>
	 * When this feature is enabled, symlink targets are saved in the page cache.
	 * You can invalidate a cached link by calling:
	 * `fuse_lowlevel_notify_inval_inode(se, ino, 0, 0);`
	 * <p>
	 * This feature is disabled by default.
	 * If the kernel supports it (>= 4.20), you can enable this feature by
	 * setting this flag in the `want` field of the `fuse_conn_info` structure.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_CACHE_SYMLINKS = (1 << 23);

	/**
	 * Indicates support for zero-message opendirs. If this flag is set in
	 * the `capable` field of the `fuse_conn_info` structure, then the filesystem
	 * may return `ENOSYS` from the opendir() handler to indicate success. Further
	 * opendir and releasedir messages will be handled in the kernel. (If this
	 * flag is not set, returning ENOSYS will be treated as an error and signalled
	 * to the caller.)
	 * <p>
	 * Setting (or unsetting) this flag in the `want` field has *no effect*.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_NO_OPENDIR_SUPPORT = (1 << 24);

	/**
	 * Indicates support for invalidating cached pages only on explicit request.
	 * <p>
	 * If this flag is set in the `capable` field of the `fuse_conn_info` structure,
	 * then the FUSE kernel module supports invalidating cached pages only on
	 * explicit request by the filesystem through fuse_lowlevel_notify_inval_inode()
	 * or fuse_invalidate_path().
	 * <p>
	 * By setting this flag in the `want` field of the `fuse_conn_info` structure,
	 * the filesystem is responsible for invalidating cached pages through explicit
	 * requests to the kernel.
	 * <p>
	 * Note that setting this flag does not prevent the cached pages from being
	 * flushed by OS itself and/or through user actions.
	 * <p>
	 * Note that if both FUSE_CAP_EXPLICIT_INVAL_DATA and FUSE_CAP_AUTO_INVAL_DATA
	 * are set in the `capable` field of the `fuse_conn_info` structure then
	 * FUSE_CAP_AUTO_INVAL_DATA takes precedence.
	 * <p>
	 * This feature is disabled by default.
	 *
	 * @since libFUSE 3.0
	 */
	int FUSE_CAP_EXPLICIT_INVAL_DATA = (1 << 25);

	/**
	 * Major version of the protocol (read-only)
	 *
	 * @return {@code proto_major} value
	 */
	int protoMajor();

	/**
	 * Minor version of the protocol (read-only)
	 *
	 * @return {@code proto_minor} value
	 */
	int protoMinor();

	/**
	 * Capability flags that the kernel supports (read-only)
	 *
	 * @return {@code capable} value
	 */
	int capable();

	/**
	 * Capability flags that the filesystem wants to enable.
	 * <p>
	 * libfuse attempts to initialize this field with
	 * reasonable default values before calling the init() handler.
	 *
	 * @return {@code want} value
	 */
	int want();

	/**
	 * Sets the {@link #want()} value.
	 *
	 * @param wanted {@code want} value
	 */
	void setWant(int wanted);

	/**
	 * Maximum size of the write buffer.
	 *
	 * @return {@code max_write} buffer
	 */
	int maxWrite();

	/**
	 * Sets the {@link #maxWrite()} value.
	 *
	 * @param maxWrite {@code max_write} value
	 */
	void setMaxWrite(int maxWrite);

	/**
	 * Maximum size of read requests. A value of zero indicates no limit. However, even if the filesystem does not specify a
	 * limit, the maximum size of read requests will still be limited by the kernel.
	 * <p>
	 * NOTE: For the time being, the maximum size of read requests must be set both here *and* passed to fuse_session_new()
	 * using the {@code -o max_read=<n>} mount option. At some point in the future, specifying the mount option will no longer
	 * be necessary.
	 *
	 * @return {@code max_read} value or 0 if not supported
	 * @since libFUSE 3.0
	 */
	default int maxRead() {
		return 0;
	}

	/**
	 * Sets the {@link #maxRead()} value.
	 *
	 * @param maxRead {@code max_read} value
	 * @since libFUSE 3.0
	 */
	default void setMaxRead(int maxRead) {
		//no-op
	}

	/**
	 * Maximum readahead.
	 *
	 * @return {@code max_readahead} value
	 */
	int maxReadahead();

	/**
	 * Sets the {@link #maxReadahead()} value.
	 *
	 * @param maxReadahead {@code max_readahead} value
	 */
	void setMaxReadahead(int maxReadahead);

	/**
	 * Maximum number of pending "background" requests. A
	 * background request is any type of request for which the
	 * total number is not limited by other means. As of kernel
	 * 4.8, only two types of requests fall into this category:
	 * <ol>
	 * 	<li>Read-ahead requests</li>
	 * 	<li>Asynchronous direct I/O requests</li>
	 * </ol>
	 * Read-ahead requests are generated (if max_readahead is
	 * non-zero) by the kernel to preemptively fill its caches
	 * when it anticipates that userspace will soon read more
	 * data.
	 * <p>
	 * Asynchronous direct I/O requests are generated if
	 * FUSE_CAP_ASYNC_DIO is enabled and userspace submits a large
	 * direct I/O request. In this case the kernel will internally
	 * split it up into multiple smaller requests and submit them
	 * to the filesystem concurrently.
	 * <p>
	 * Note that the following requests are *not* background
	 * requests: writeback requests (limited by the kernel's
	 * flusher algorithm), regular (i.e., synchronous and
	 * buffered) userspace read/write requests (limited to one per
	 * thread), asynchronous read requests (Linux's io_submit(2)
	 * call actually blocks, so these are also limited to one per
	 * thread).
	 *
	 * @return {@code max_background} value
	 */
	int maxBackground();

	/**
	 * Sets the {@link #maxBackground()} value.
	 *
	 * @param maxBackground {@code max_background} value
	 */
	void setMaxBackground(int maxBackground);

	/**
	 * Kernel congestion threshold parameter. If the number of pending
	 * background requests exceeds this number, the FUSE kernel module will
	 * mark the filesystem as "congested". This instructs the kernel to
	 * expect that queued requests will take some time to complete, and to
	 * adjust its algorithms accordingly (e.g. by putting a waiting thread
	 * to sleep instead of using a busy-loop).
	 *
	 * @return {@code congestion_threshold} value
	 */
	int congestionThreshold();

	/**
	 * Sets the {@link #congestionThreshold()} value.
	 *
	 * @param congestionThreshold {@code congestion_threshold} value
	 */
	void setCongestionThreshold(int congestionThreshold);

	/**
	 * When {@link #FUSE_CAP_WRITEBACK_CACHE} is enabled, the kernel is responsible for updating mtime and ctime when
	 * write requests are received. The updated values are passed to the filesystem with setattr() requests.
	 * However, if the filesystem does not support the full resolution of the kernel timestamps (nanoseconds),
	 * the mtime and ctime values used by kernel and filesystem will differ (and result in an apparent change of times
	 * after a cache flush).
	 * <p>
	 * To prevent this problem, this variable can be used to inform the kernel about the timestamp granularity supported
	 * by the file-system. The value should be power of 10.  The default is 1, i.e. full nano-second resolution.
	 * Filesystems supporting only second resolution should set this to 1000000000.
	 *
	 * @return {@code time_gran} value or 0 if not supported
	 * @since libFUSE 3.0
	 */
	default int timeGran() {
		return 0;
	}

	/**
	 * Sets the {@link #timeGran()} value.
	 *
	 * @param timeGran {@code time_gran} value
	 * @since libFUSE 3.0
	 */
	default void setTimeGran(int timeGran) {
		//no-op
	}

	/**
	 * Is asynchronous read supported (read-write).
	 *
	 * @return {@code async_read} value or 0 if not supported
	 */
	default int asyncRead() {
		return 0;
	}

	/**
	 * Sets the {@link #asyncRead()} value.
	 *
	 * @param asyncRead {@code async_read} value
	 */
	default void setAsyncRead(int asyncRead) {
		//no-op
	}
}
