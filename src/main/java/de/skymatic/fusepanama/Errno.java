package de.skymatic.fusepanama;

/**
 * Error codes extracted from
 * <a href="https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/include/uapi/asm-generic/errno.h">errno.h (linux)</a>
 * and <a href="https://github.com/apple/darwin-xnu/blob/master/bsd/sys/errno.h">errno.h (darwin)</a>
 */
public final class Errno {

	private Errno() {}
	


	/**
	 * No such file or directory
	 */
	public static int ENOENT = 2;

	/**
	 * Invalid system call number
	 */
	public static int ENOSYS = Platform.IS_MAC ? 78 : 38;
}
