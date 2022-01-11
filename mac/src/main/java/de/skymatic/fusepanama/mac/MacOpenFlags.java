package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.OpenFlags;
import de.skymatic.fusepanama.mac.lowlevel.fcntl_h;

public class MacOpenFlags implements OpenFlags {

	@Override
	public int readOnly() {
		return fcntl_h.O_RDONLY();
	}

	@Override
	public int writeOnly() {
		return fcntl_h.O_WRONLY();
	}

	@Override
	public int readWrite() {
		return fcntl_h.O_RDWR();
	}

	@Override
	public int append() {
		return fcntl_h.O_APPEND();
	}

	@Override
	public int create() {
		return fcntl_h.O_CREAT();
	}

	@Override
	public int excl() {
		return fcntl_h.O_EXCL();
	}

	@Override
	public int truncate() {
		return fcntl_h.O_TRUNC();
	}

	@Override
	public int sync() {
		return fcntl_h.O_SYNC();
	}

	@Override
	public int dsync() {
		return fcntl_h.O_DSYNC();
	}

}
