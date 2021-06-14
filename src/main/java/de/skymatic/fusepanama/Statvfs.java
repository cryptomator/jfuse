package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.statvfs;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public record Statvfs(MemorySegment segment) {

	Statvfs(MemoryAddress address, ResourceScope scope) {
		this(statvfs.ofAddress(address, scope));
	}

	public long getBsize() {
		return statvfs.f_bsize$get(segment);
	}

	public void setBsize(long bsize) {
		statvfs.f_bsize$set(segment, bsize);
	}

	public int getBlocks() {
		return statvfs.f_blocks$get(segment);
	}

	public void setBlocks(int blocks) {
		statvfs.f_blocks$set(segment, blocks);
	}

	public int getBfree() {
		return statvfs.f_bfree$get(segment);
	}

	public void setBfree(int bfree) {
		statvfs.f_bfree$set(segment, bfree);
	}

	public int getBavail() {
		return statvfs.f_bavail$get(segment);
	}

	public void setBavail(int bavail) {
		statvfs.f_bavail$set(segment, bavail);
	}

	public long getNameMax() {
		return statvfs.f_namemax$get(segment);
	}

	public void setNameMax(long namemax) {
		statvfs.f_namemax$set(segment, namemax);
	}

}
