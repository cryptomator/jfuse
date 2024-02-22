package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.statvfs;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	@Override
	public long getBsize() {
		return statvfs.f_bsize(segment);
	}

	@Override
	public void setBsize(long bsize) {
		statvfs.f_bsize(segment, bsize);
	}

	@Override
	public long getFrsize() {
		return statvfs.f_frsize(segment);
	}

	@Override
	public void setFrsize(long frsize) {
		statvfs.f_frsize(segment, frsize);
	}

	@Override
	public long getBlocks() {
		return statvfs.f_blocks(segment);
	}

	@Override
	public void setBlocks(long blocks) {
		statvfs.f_blocks(segment, blocks);
	}

	@Override
	public long getBfree() {
		return statvfs.f_bfree(segment);
	}

	@Override
	public void setBfree(long bfree) {
		statvfs.f_bfree(segment, bfree);
	}

	@Override
	public long getBavail() {
		return statvfs.f_bavail(segment);
	}

	@Override
	public void setBavail(long bavail) {
		statvfs.f_bavail(segment, bavail);
	}

	@Override
	public long getNameMax() {
		return statvfs.f_namemax(segment);
	}

	@Override
	public void setNameMax(long namemax) {
		statvfs.f_namemax(segment, namemax);
	}

}
