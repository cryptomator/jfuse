package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Errno;

/**
 * Error codes extracted from <a href="https://github.com/apple/darwin-xnu/blob/master/bsd/sys/errno.h">errno.h (darwin)</a>
 */
public final class MacErrno implements Errno {
	@Override
	public int enosys() {
		return 78;
	}
}
