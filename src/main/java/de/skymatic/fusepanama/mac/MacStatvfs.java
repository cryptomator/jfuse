package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Statvfs;
import de.skymatic.fusepanama.mac.lowlevel.statvfs;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public record MacStatvfs(MemorySegment segment) implements Statvfs {

	public MacStatvfs(MemoryAddress address, ResourceScope scope) {
		this(statvfs.ofAddress(address, scope));
	}

	@Override
	public long getBsize() {
		return statvfs.f_bsize$get(segment);
	}

	@Override
	public void setBsize(long bsize) {
		statvfs.f_bsize$set(segment, bsize);
	}

	@Override
	public int getBlocks() {
		return statvfs.f_blocks$get(segment);
	}

	@Override
	public void setBlocks(int blocks) {
		statvfs.f_blocks$set(segment, blocks);
	}

	@Override
	public int getBfree() {
		return statvfs.f_bfree$get(segment);
	}

	@Override
	public void setBfree(int bfree) {
		statvfs.f_bfree$set(segment, bfree);
	}

	@Override
	public int getBavail() {
		return statvfs.f_bavail$get(segment);
	}

	@Override
	public void setBavail(int bavail) {
		statvfs.f_bavail$set(segment, bavail);
	}

	@Override
	public long getNameMax() {
		return statvfs.f_namemax$get(segment);
	}

	@Override
	public void setNameMax(long namemax) {
		statvfs.f_namemax$set(segment, namemax);
	}

}
