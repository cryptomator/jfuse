package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.fuse.stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatImpl(MemorySegment segment) implements Stat {

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(stat.st_atimespec(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(stat.st_ctimespec(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(stat.st_mtimespec(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(stat.st_birthtimespec(segment));
	}

	@Override
	public void setMode(int mode) {
		stat.st_mode(segment, (short) mode);
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
