package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatImpl(MemorySegment segment) implements Stat {

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(fuse_stat.st_atim(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(fuse_stat.st_ctim(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(fuse_stat.st_mtim(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(fuse_stat.st_birthtim(segment));
	}

	@Override
	public void setMode(int mode) {
		fuse_stat.st_mode(segment, mode);
	}

	@Override
	public int getMode() {
		return fuse_stat.st_mode(segment);
	}

	@Override
	public void setUid(int uid) {
		fuse_stat.st_uid(segment, uid);
	}

	@Override
	public int getUid() {
		return fuse_stat.st_uid(segment);
	}

	@Override
	public void setGid(int gid) {
		fuse_stat.st_gid(segment, gid);
	}

	@Override
	public int getGid() {
		return fuse_stat.st_gid(segment);
	}

	@Override
	public void setNLink(short count) {
		fuse_stat.st_nlink(segment, count);
	}

	@Override
	public long getNLink() {
		return fuse_stat.st_nlink(segment);
	}

	@Override
	public void setSize(long size) {
		fuse_stat.st_size(segment, size);
	}

	@Override
	public long getSize() {
		return fuse_stat.st_size(segment);
	}

}
