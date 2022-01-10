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

	@Override
	public int ownerRead() {
		return stat_h.S_IRUSR();
	}

	@Override
	public int ownerWrite() {
		return stat_h.S_IWUSR();
	}

	@Override
	public int ownerExecute() {
		return stat_h.S_IXUSR();
	}

	@Override
	public int groupRead() {
		return stat_h.S_IRGRP();
	}

	@Override
	public int groupWrite() {
		return stat_h.S_IWGRP();
	}

	@Override
	public int groupExecute() {
		return stat_h.S_IXGRP();
	}

	@Override
	public int otherRead() {
		return stat_h.S_IROTH();
	}

	@Override
	public int otherWrite() {
		return stat_h.S_IWOTH();
	}

	@Override
	public int otherExecute() {
		return stat_h.S_IXOTH();
	}
}
