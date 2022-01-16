package de.skymatic.fusepanama.linux.amd64;

import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.linux.amd64.lowlevel.errno_h;

public record LinuxErrno() implements Errno {

	@Override
	public int enoent() {
		return errno_h.ENOENT();
	}

	@Override
	public int enosys() {
		return errno_h.ENOSYS();
	}

	@Override
	public int enomem() {
		return errno_h.ENOMEM();
	}

	@Override
	public int eacces() {
		return errno_h.EACCES();
	}

	@Override
	public int eio() {
		return errno_h.EIO();
	}

	@Override
	public int erofs() {
		return errno_h.EROFS();
	}

	@Override
	public int ebadf() {
		return errno_h.EBADF();
	}

	@Override
	public int eexist() {
		return errno_h.EEXIST();
	}

	@Override
	public int enotdir() {
		return errno_h.ENOTDIR();
	}

	@Override
	public int eisdir() {
		return errno_h.EISDIR();
	}

	@Override
	public int enotempty() {
		return errno_h.ENOTEMPTY();
	}

	@Override
	public int einval() {
		return errno_h.EINVAL();
	}
}
