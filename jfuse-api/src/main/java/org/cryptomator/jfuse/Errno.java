package org.cryptomator.jfuse;

import java.util.ServiceLoader;

/**
 * System error numbers from <code>errno.h</code>.
 * <p>
 * Actual numbers are system-dependend and will be provided by implementation modules.
 *
 * @see <a href="https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/include/uapi/asm-generic/errno.h">errno.h (linux)</a>
 * @see <a href="https://github.com/apple/darwin-xnu/blob/master/bsd/sys/errno.h">errno.h (darwin)</a>
 * @see <a href="https://docs.microsoft.com/en-us/cpp/c-runtime-library/errno-constants">errno.h (windows)</a>
 */
public interface Errno {

	ServiceLoader<Errno> SERVICE_LOADER = ServiceLoader.load(Errno.class);

	static Errno instance() {
		// TODO: filter for current platform?
		return SERVICE_LOADER.findFirst().orElseThrow(() -> new IllegalStateException("No implementation of Errno loaded."));
	}

	/**
	 * @return code representing: No such file or directory
	 */
	int enoent();

	/**
	 * @return code representing: Unsupported operation
	 */
	int enosys();

	/**
	 * @return code representing: Not enough memory
	 */
	int enomem();

	/**
	 * @return code representing: Permission denied
	 */
	int eacces();

	/**
	 * @return code representing: I/O error
	 */
	int eio();

	/**
	 * @return code representing: Read only file system
	 */
	int erofs();

	/**
	 * @return code representing: Bad file number
	 */
	int ebadf();

	/**
	 * @return code representing: File already exists
	 */
	int eexist();

	/**
	 * @return code representing: Not a directory
	 */
	int enotdir();

	/**
	 * @return code representing: Is a directory
	 */
	int eisdir();

	/**
	 * @return code representing: Directory not empty
	 */
	int enotempty();

	/**
	 * @return code representing: Invalid argument
	 */
	int einval();

}
