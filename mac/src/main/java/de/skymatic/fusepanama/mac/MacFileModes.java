package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.mac.lowlevel.stat_h;

public class MacFileModes implements FileModes {

	@Override
	public int dir() {
		return stat_h.S_IFDIR();
	}

	@Override
	public int reg() {
		return stat_h.S_IFREG();
	}

	@Override
	public int lnk() {
		return stat_h.S_IFLNK();
	}

}
