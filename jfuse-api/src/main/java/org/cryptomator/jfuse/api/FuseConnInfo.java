package org.cryptomator.jfuse.api;

/**
 * Fuse connection info struct used in {@link FuseOperations#init(FuseConnInfo)} method.
 * <p>
 * This struct is a union of the libfuse2 and libfuse3 definition. If an accessor method or macro is not supported by used libfuse version, it is documented in the javadoc.
 * See also @code{fuse_common.h} of libfuse2 or 3.
 */
public interface FuseConnInfo {

	@SuppressWarnings("PointlessBitwiseExpression")
	int FUSE_CAP_ASYNC_READ = (1 << 0);
	int FUSE_CAP_POSIX_LOCKS = (1 << 1);
	int FUSE_CAP_ATOMIC_O_TRUNC = (1 << 3);
	int FUSE_CAP_EXPORT_SUPPORT = (1 << 4);
	/**
	 * Libfuse2 only
	 */
	int FUSE_CAP_BIG_WRITES = (1 << 5);
	int FUSE_CAP_DONT_MASK = (1 << 6);
	int FUSE_CAP_SPLICE_WRITE = (1 << 7);
	int FUSE_CAP_SPLICE_MOVE = (1 << 8);
	int FUSE_CAP_SPLICE_READ = (1 << 9);
	int FUSE_CAP_IOCTL_DIR = (1 << 11);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_AUTO_INVAL_DATA = (1 << 12);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_READDIRPLUS = (1 << 13);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_READDIRPLUS_AUTO = (1 << 14);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_ASYNC_DIO = (1 << 15);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_WRITEBACK_CACHE = (1 << 16);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_NO_OPEN_SUPPORT = (1 << 17);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_PARALLEL_DIROPS = (1 << 18);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_POSIX_ACL = (1 << 19);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_HANDLE_KILLPRIV = (1 << 20);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_CACHE_SYMLINKS = (1 << 23);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_NO_OPENDIR_SUPPORT = (1 << 24);
	/**
	 * Libfuse3 only
	 */
	int FUSE_CAP_EXPLICIT_INVAL_DATA = (1 << 25);

	int protoMajor();

	int protoMinor();

	int capable();

	int want();

	void setWant(int wanted);

	int maxWrite();

	void setMaxWrite(int maxWrite);

	/**
	 * Libfuse3 only
	 *
	 * @return maxRead value of fuse_conn_info or 0 if not supported
	 */
	default int maxRead() {
		return 0;
	}

	/**
	 * Libfuse3 only
	 */
	default void setMaxRead(int maxRead) {
		//no-op
	}

	int maxReadahead();

	void setMaxReadahead(int maxReadahead);

	int maxBackground();

	void setMaxBackground(int maxBackground);

	int congestionThreshold();

	void setCongestionThreshold(int congestionThreshold);

	/**
	 * Libfuse3 only
	 *
	 * @return time_gran value of fuse_conn_info or 0 if not supported
	 */
	default int timeGran() {
		return 0;
	}

	/**
	 * Libfuse3 only
	 */
	default void setTimeGran(int timeGran) {
		//no-op
	}

	/**
	 * Libfuse2 only
	 *
	 * @return async_read value of fuse_conn_info or 0 if not supported
	 */
	default int asyncRead() {
		return 0;
	}

	/**
	 * Libfuse2 only
	 */
	default void setAsyncRead(int asyncRead) {
		//no-op
	}
}
