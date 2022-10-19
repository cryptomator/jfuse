package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.linux.amd64.extr.stat;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record StatImpl(MemorySegment segment) implements Stat {

	public StatImpl(MemoryAddress address, MemorySession scope) {
		this(stat.ofAddress(address, scope));
	}

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(stat.st_atim$slice(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(stat.st_ctim$slice(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(stat.st_mtim$slice(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(stat.st_ctim$slice(segment));
	}

	@Override
	public void setMode(int mode) {
		stat.st_mode$set(segment, mode);
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

}
