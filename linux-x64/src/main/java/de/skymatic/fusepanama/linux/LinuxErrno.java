package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.linux.lowlevel.errno_h;

public record LinuxErrno() implements Errno {

	@Override
	public int enoent() {
		return errno_h.ENOENT();
	}

	@Override
	public int enosys() {
		return errno_h.ENOSYS();
	}
}
