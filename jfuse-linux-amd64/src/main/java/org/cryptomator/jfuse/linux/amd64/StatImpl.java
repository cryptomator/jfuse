package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatImpl(MemorySegment segment) implements Stat {

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(stat.st_atim(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(stat.st_ctim(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(stat.st_mtim(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(stat.st_ctim(segment));
	}

	@Override
	public void setMode(int mode) {
		stat.st_mode(segment, mode);
	}

	@Override
	public int getMode() {
		return stat.st_mode(segment);
	}

	@Override
	public void setUid(int uid) {
		stat.st_uid(segment, uid);
	}

	@Override
	public int getUid() {
		return stat.st_uid(segment);
	}

	@Override
	public void setGid(int gid) {
		stat.st_gid(segment, gid);
	}

	@Override
	public int getGid() {
		return stat.st_gid(segment);
	}

	@Override
	public void setNLink(short count) {
		stat.st_nlink(segment, count);
	}

	@Override
	public long getNLink() {
		return stat.st_nlink(segment);
	}

	@Override
	public void setSize(long size) {
		stat.st_size(segment, size);
	}

	@Override
	public long getSize() {
		return stat.st_size(segment);
	}

}
