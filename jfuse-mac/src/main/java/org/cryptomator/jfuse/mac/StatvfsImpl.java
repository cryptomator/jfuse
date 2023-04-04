package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.statvfs;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	private static final long MAX_UINT = 0xFFFFFFFFL;

	public StatvfsImpl(MemorySegment address, SegmentScope scope) {
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
		return Integer.toUnsignedLong(statvfs.f_blocks$get(segment));
	}

	@Override
	public void setBlocks(long blocks) {
		statvfs.f_blocks$set(segment,(int) Math.min(MAX_UINT, blocks));
	}

	@Override
	public long getBfree() {
		return Integer.toUnsignedLong(statvfs.f_bfree$get(segment));
	}

	@Override
	public void setBfree(long bfree) {
		statvfs.f_bfree$set(segment,(int) Math.min(MAX_UINT, bfree));
	}

	@Override
	public long getBavail() {
		return Integer.toUnsignedLong(statvfs.f_bavail$get(segment));
	}

	@Override
	public void setBavail(long bavail) {
		statvfs.f_bavail$set(segment,(int) Math.min(MAX_UINT, bavail));
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
