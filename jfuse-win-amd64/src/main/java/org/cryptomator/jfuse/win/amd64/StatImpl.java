package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.Stat;
import org.cryptomator.jfuse.TimeSpec;
import org.cryptomator.jfuse.win.amd64.extr.fuse_stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record StatImpl(MemorySegment segment) implements Stat {

	/*
	 * Windows modes from <code>stat.h</code> are incorrect. Winfsp expects POSIX values.
	 * @see <a href="https://github.com/billziss-gh/winfsp/blob/751eaa69dfbb203035208e99aa92693223dac5fe/src/dll/fuse/fuse_intf.c#L494-L521">WinFSP implementation</a>
	 */

	@SuppressWarnings("OctalInteger")
	private static final int S_IFDIR = 0040000;

	@SuppressWarnings("OctalInteger")
	private static final int S_IFREG = 0100000;

	@SuppressWarnings("OctalInteger")
	private static final int S_IFLNK = 0120000;

	public StatImpl(MemoryAddress address, ResourceScope scope) {
		this(fuse_stat.ofAddress(address, scope));
	}

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(fuse_stat.st_atim$slice(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(fuse_stat.st_ctim$slice(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(fuse_stat.st_mtim$slice(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(fuse_stat.st_birthtim$slice(segment));
	}

	@Override
	public void setMode(int mode) {
		fuse_stat.st_mode$set(segment, mode);
	}

	@Override
	public int getMode() {
		return fuse_stat.st_mode$get(segment);
	}

	@Override
	public void setNLink(short count) {
		fuse_stat.st_nlink$set(segment, count);
	}

	@Override
	public long getNLink() {
		return fuse_stat.st_nlink$get(segment);
	}

	@Override
	public void setSize(long size) {
		fuse_stat.st_size$set(segment, size);
	}

	@Override
	public long getSize() {
		return fuse_stat.st_size$get(segment);
	}

	@Override
	public boolean isDir() {
		return (getMode() & S_IFDIR) == S_IFDIR;
	}

	@Override
	public void toggleDir(boolean isDir) {
		if (isDir) {
			setMode(getMode() | S_IFDIR);
		} else {
			setMode(getMode() & ~S_IFDIR);
		}
	}

	@Override
	public boolean isReg() {
		return (getMode() & S_IFREG) == S_IFREG;
	}

	@Override
	public void toggleReg(boolean isReg) {
		if (isReg) {
			setMode(getMode() | S_IFREG);
		} else {
			setMode(getMode() & ~S_IFREG);
		}
	}

	@Override
	public boolean isLnk() {
		return (getMode() & S_IFLNK) == S_IFLNK;
	}

	@Override
	public void toggleLnk(boolean isLnk) {
		if (isLnk) {
			setMode(getMode() | S_IFLNK);
		} else {
			setMode(getMode() & ~S_IFLNK);
		}
	}

}
