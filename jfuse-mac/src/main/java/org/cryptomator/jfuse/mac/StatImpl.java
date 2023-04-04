package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.stat;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

record StatImpl(MemorySegment segment) implements Stat {

	public StatImpl(MemorySegment address, SegmentScope scope) {
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
	public void setUid(int uid) {
		stat.st_uid$set(segment, uid);
	}

	@Override
	public int getUid() {
		return stat.st_uid$get(segment);
	}

	@Override
	public void setGid(int gid) {
		stat.st_gid$set(segment, gid);
	}

	@Override
	public int getGid() {
		return stat.st_gid$get(segment);
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
