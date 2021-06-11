package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.stat;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public class Stat {

	private final MemorySegment segment;

	Stat(MemoryAddress address, ResourceScope scope) {
		this(stat.ofAddress(address, scope));
	}

	Stat(MemorySegment segment) {
		this.segment = segment;
	}

	public TimeSpec aTime() {
		return new TimeSpec(stat.st_atimespec$slice(segment));
	}

	public TimeSpec cTime() {
		return new TimeSpec(stat.st_ctimespec$slice(segment));
	}

	public TimeSpec mTime() {
		return new TimeSpec(stat.st_mtimespec$slice(segment));
	}

	public TimeSpec birthTime() {
		return new TimeSpec(stat.st_birthtimespec$slice(segment));
	}

	public void setMode(short mode) {
		stat.st_mode$set(segment, mode);
	}

	public short getMode() {
		return stat.st_mode$get(segment);
	}

	public void setNLink(short count) {
		stat.st_nlink$set(segment, count);
	}

	public short getNLink() {
		return stat.st_nlink$get(segment);
	}

	public void setSize(long size) {
		stat.st_size$set(segment, size);
	}

	public long getSize() {
		return stat.st_size$get(segment);
	}

}
