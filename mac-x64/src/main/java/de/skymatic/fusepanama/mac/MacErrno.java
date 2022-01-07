package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.mac.lowlevel.errno_h;

public record MacErrno() implements Errno {

	@Override
	public int enoent() {
		return errno_h.ENOENT();
	}

	@Override
	public int enosys() {
		return errno_h.ENOSYS();
	}
}
