package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.linux.aarch64.extr.statvfs;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	public StatvfsImpl(MemoryAddress address, MemorySession scope) {
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
	public long getFrsize() {
		return statvfs.f_frsize$get(segment);
	}

	@Override
	public void setFrsize(long frsize) {
		statvfs.f_frsize$set(segment, frsize);
	}

	@Override
	public long getBlocks() {
		return statvfs.f_blocks$get(segment);
	}

	@Override
	public void setBlocks(long blocks) {
		statvfs.f_blocks$set(segment, blocks);
	}

	@Override
	public long getBfree() {
		return statvfs.f_bfree$get(segment);
	}

	@Override
	public void setBfree(long bfree) {
		statvfs.f_bfree$set(segment, bfree);
	}

	@Override
	public long getBavail() {
		return statvfs.f_bavail$get(segment);
	}

	@Override
	public void setBavail(long bavail) {
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
