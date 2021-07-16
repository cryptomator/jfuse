package de.skymatic.fusepanama;

import de.skymatic.fusepanama.mac.MacErrno;

/**
 * Error codes extracted from
 * <a href="https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/include/uapi/asm-generic/errno.h">errno.h (linux)</a>
 * and <a href="https://github.com/apple/darwin-xnu/blob/master/bsd/sys/errno.h">errno.h (darwin)</a>
 */
public interface Errno {

	Errno ERRNO = switch (Platform.CURRENT) {
		case MAC -> new MacErrno();
		default -> null;
	};

	/**
	 * No such file or directory
	 */
	default int enoent() {
		return 2;
	}

	/**
	 * Invalid system call number
	 */
	default int enosys() {
		return 38;
	}
}
