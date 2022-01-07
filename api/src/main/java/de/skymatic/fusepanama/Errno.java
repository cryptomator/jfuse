package de.skymatic.fusepanama;

import java.util.ServiceLoader;

/**
 * System error numbers from <code>errno.h</code>.
 * <p>
 * Actual numbers are system-dependend and will be provided by implementation modules.
 *
 * @see <a href="https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/tree/include/uapi/asm-generic/errno.h">errno.h (linux)</a>
 * @see <a href="https://github.com/apple/darwin-xnu/blob/master/bsd/sys/errno.h">errno.h (darwin)</a>
 */
public interface Errno {

	ServiceLoader<Errno> SERVICE_LOADER = ServiceLoader.load(Errno.class);

	static Errno instance() {
		return SERVICE_LOADER.findFirst().orElseThrow(() -> new IllegalStateException("No implementation of Errno loaded."));
	}

	/**
	 * No such file or directory
	 */
	int enoent();

	/**
	 * Invalid system call number
	 */
	int enosys();
}
