package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.win.amd64.lowlevel.fuse_stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record StatImpl(MemorySegment segment) implements Stat {

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

}
