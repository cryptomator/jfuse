package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.linux.lowlevel.stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record LinuxStat(MemorySegment segment) implements Stat {

	public LinuxStat(MemoryAddress address, ResourceScope scope) {
		this(stat.ofAddress(address, scope));
	}

	@Override
	public TimeSpec aTime() {
		return new LinuxTimeSpec(stat.st_atim$slice(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new LinuxTimeSpec(stat.st_ctim$slice(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new LinuxTimeSpec(stat.st_mtim$slice(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new LinuxTimeSpec(stat.st_ctim$slice(segment));
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
