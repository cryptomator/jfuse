package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.FileModes;

/**
 * Windows modes from <code>stat.h</code> are incorrect. Winfsp expects POSIX values.
 * @see <a href="https://github.com/billziss-gh/winfsp/blob/751eaa69dfbb203035208e99aa92693223dac5fe/src/dll/fuse/fuse_intf.c#L494-L521">WinFSP implementation</a>
 */
public class WinFileModes implements FileModes {

	@SuppressWarnings("OctalInteger")
	private static final int S_IFDIR = 0040000;

	@SuppressWarnings("OctalInteger")
	private static final int S_IFREG = 0100000;

	@SuppressWarnings("OctalInteger")
	private static final int S_IFLNK = 0120000;

	@Override
	public int dir() {
		return S_IFDIR;
	}

	@Override
	public int reg() {
		return S_IFREG;
	}

	@Override
	public int lnk() {
		return S_IFLNK;
	}

}
