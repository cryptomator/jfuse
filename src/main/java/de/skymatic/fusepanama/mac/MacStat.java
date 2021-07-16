package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.mac.lowlevel.stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public record MacStat(MemorySegment segment) implements Stat {

	public MacStat(MemoryAddress address, ResourceScope scope) {
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
	public short getMode() {
		return stat.st_mode$get(segment);
	}

	@Override
	public void setNLink(short count) {
		stat.st_nlink$set(segment, count);
	}

	@Override
	public short getNLink() {
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
