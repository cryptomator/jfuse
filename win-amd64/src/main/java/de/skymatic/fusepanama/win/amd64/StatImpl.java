package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.win.amd64.lowlevel.stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record StatImpl(MemorySegment segment) implements Stat {

	public StatImpl(MemoryAddress address, ResourceScope scope) {
		this(stat.ofAddress(address, scope));
	}

	@Override
	public TimeSpec aTime() {
		return new MacTimeSpec(stat.st_atimespec$slice(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new MacTimeSpec(stat.st_ctimespec$slice(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new MacTimeSpec(stat.st_mtimespec$slice(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new MacTimeSpec(stat.st_birthtimespec$slice(segment));
	}

	@Override
	public void setMode(short mode) {
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
