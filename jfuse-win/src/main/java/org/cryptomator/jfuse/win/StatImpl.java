package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatImpl(MemorySegment segment) implements Stat {

	public StatImpl(MemorySegment address, Arena scope) {
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
	public void setUid(int uid) {
		fuse_stat.st_uid$set(segment, uid);
	}

	@Override
	public int getUid() {
		return fuse_stat.st_uid$get(segment);
	}

	@Override
	public void setGid(int gid) {
		fuse_stat.st_gid$set(segment, gid);
	}

	@Override
	public int getGid() {
		return fuse_stat.st_gid$get(segment);
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

}
