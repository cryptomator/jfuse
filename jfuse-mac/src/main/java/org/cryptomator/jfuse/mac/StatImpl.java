package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.stat;
import org.cryptomator.jfuse.mac.extr.stat_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record StatImpl(MemorySegment segment) implements Stat {

	public StatImpl(MemoryAddress address, ResourceScope scope) {
		this(stat.ofAddress(address, scope));
	}

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(stat.st_atimespec$slice(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(stat.st_ctimespec$slice(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(stat.st_mtimespec$slice(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(stat.st_birthtimespec$slice(segment));
	}

	@Override
	public void setMode(int mode) {
		stat.st_mode$set(segment, (short) mode);
	}

	@Override
	public int getMode() {
		return stat.st_mode$get(segment);
	}

	@Override
	public void setNLink(short count) {
		stat.st_nlink$set(segment, count);
	}

	@Override
	public long getNLink() {
		return stat.st_nlink$get(segment);
	}

	@Override
	public void setSize(long size) {
		stat.st_size$set(segment, size);
	}

	@Override
	public long getSize() {
		return stat.st_size$get(segment);
	}

	@Override
	public boolean isDir() {
		return (getMode() & stat_h.S_IFDIR()) == stat_h.S_IFDIR();
	}

	@Override
	public void toggleDir(boolean isDir) {
		if (isDir) {
			setMode(getMode() | stat_h.S_IFDIR());
		} else {
			setMode(getMode() & ~stat_h.S_IFDIR());
		}
	}

	@Override
	public boolean isReg() {
		return (getMode() & stat_h.S_IFREG()) == stat_h.S_IFREG();
	}

	@Override
	public void toggleReg(boolean isReg) {
		if (isReg) {
			setMode(getMode() | stat_h.S_IFREG());
		} else {
			setMode(getMode() & ~stat_h.S_IFREG());
		}
	}

	@Override
	public boolean isLnk() {
		return (getMode() & stat_h.S_IFLNK()) == stat_h.S_IFLNK();
	}

	@Override
	public void toggleLnk(boolean isLnk) {
		if (isLnk) {
			setMode(getMode() | stat_h.S_IFLNK());
		} else {
			setMode(getMode() & ~stat_h.S_IFLNK());
		}
	}

}
