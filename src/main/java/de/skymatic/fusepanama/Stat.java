package de.skymatic.fusepanama;

import de.skymatic.fusepanama.macfuse.fuse_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class Stat {

	private final MemorySegment segment;

	Stat(MemoryAddress address) {
		this(fuse_h.stat.ofAddressRestricted(address));
	}

	Stat(MemorySegment segment) {
		this.segment = segment;
	}

	public TimeSpec aTime() {
		return new TimeSpec(fuse_h.stat.st_atimespec$slice(segment));
	}

	public TimeSpec cTime() {
		return new TimeSpec(fuse_h.stat.st_ctimespec$slice(segment));
	}

	public TimeSpec mTime() {
		return new TimeSpec(fuse_h.stat.st_mtimespec$slice(segment));
	}

	public TimeSpec birthTime() {
		return new TimeSpec(fuse_h.stat.st_birthtimespec$slice(segment));
	}

	public void setMode(short mode) {
		fuse_h.stat.st_mode$set(segment, mode);
	}

	public short getMode() {
		return fuse_h.stat.st_mode$get(segment);
	}

	public void setNLink(short count) {
		fuse_h.stat.st_nlink$set(segment, count);
	}

	public short getNLink() {
		return fuse_h.stat.st_nlink$get(segment);
	}

	public void setSize(long size) {
		fuse_h.stat.st_size$set(segment, size);
	}

	public long getSize() {
		return fuse_h.stat.st_size$get(segment);
	}

}
