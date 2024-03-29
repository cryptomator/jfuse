package org.cryptomator.jfuse.api;

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

	/**
	 * No such file or directory
	 *
	 * @return error constant {@code ENOENT}
	 */
	int enoent();

	/**
	 * Unsupported operation
	 *
	 * @return error constant {@code ENOSYS}
	 */
	int enosys();

	/**
	 * Not enough memory
	 *
	 * @return error constant {@code ENOMEM}
	 */
	int enomem();

	/**
	 * Permission denied
	 *
	 * @return error constant {@code EACCES}
	 */
	int eacces();

	/**
	 * I/O error
	 *
	 * @return error constant {@code EIO}
	 */
	int eio();

	/**
	 * Read only file system
	 *
	 * @return error constant {@code EROFS}
	 */
	int erofs();

	/**
	 * Bad file number
	 *
	 * @return error constant {@code EBADF}
	 */
	int ebadf();

	/**
	 * File already exists
	 *
	 * @return error constant {@code EEXIST}
	 */
	int eexist();

	/**
	 * Not a directory
	 *
	 * @return error constant {@code ENOTDIR}
	 */
	int enotdir();

	/**
	 * Is a directory
	 *
	 * @return error constant {@code EISDIR}
	 */
	int eisdir();

	/**
	 * Directory not empty
	 *
	 * @return error constant {@code ENOTEMPTY}
	 */
	int enotempty();

	/**
	 * Operation not supported
	 *
	 * @return error constant {@code ENOTSUP}
	 */
	int enotsup();

	/**
	 * Invalid argument
	 *
	 * @return error constant {@code EINVAL}
	 */
	int einval();

	/**
	 * Result too large
	 *
	 * @return error constant {@code ERANGE}
	 */
	int erange();


	/**
	 * No locks available
	 *
	 * @return error constant {@code ENOLCK}
	 */
	int enolck();

	/**
	 * Filename too long
	 *
	 * @return error constant {@code ENAMETOOLONG}
	 */
	int enametoolong();

	/**
	 * The named attribute does not exist, or the process has no access to this attribute;
	 *
	 * @return error constant {@code ENODATA}
	 */
	int enodata();

	/**
	 * The named attribute does not exist, or the process has no access to this attribute;
	 *
	 * @return error constant {@code ENOATTR}
	 */
	int enoattr();

	/**
	 * Argument list too long
	 *
	 * @return error constant {@code E2BIG}
	 */
	int e2big();

}
