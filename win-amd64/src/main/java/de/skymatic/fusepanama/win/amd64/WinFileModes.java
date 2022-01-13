package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.win.amd64.lowlevel.stat_h;

public class WinFileModes implements FileModes {

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
		// there is no S_IFLNK on Windows.
		// https://github.com/billziss-gh/winfsp/blob/751eaa69dfbb203035208e99aa92693223dac5fe/src/dll/fuse/fuse_intf.c#L507
		return stat_h.S_IFREG() | stat_h.S_IFCHR();
	}

}
