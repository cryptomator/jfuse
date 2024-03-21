package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.fuse.statvfs;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	private static final long MAX_UINT = 0xFFFFFFFFL;

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
		return Integer.toUnsignedLong(statvfs.f_blocks(segment));
	}

	@Override
	public void setBlocks(long blocks) {
		statvfs.f_blocks(segment,(int) Math.min(MAX_UINT, blocks));
	}

	@Override
	public long getBfree() {
		return Integer.toUnsignedLong(statvfs.f_bfree(segment));
	}

	@Override
	public void setBfree(long bfree) {
		statvfs.f_bfree(segment,(int) Math.min(MAX_UINT, bfree));
	}

	@Override
	public long getBavail() {
		return Integer.toUnsignedLong(statvfs.f_bavail(segment));
	}

	@Override
	public void setBavail(long bavail) {
		statvfs.f_bavail(segment,(int) Math.min(MAX_UINT, bavail));
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
