package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.fuse3.statfs;

import java.lang.foreign.MemorySegment;

record StatfsImpl(MemorySegment segment) implements Statvfs {

	private static final long MAX_UINT = 0xFFFFFFFFL;

	@Override
	public long getBsize() {
		return statfs.f_bsize(segment);
	}

	@Override
	public void setBsize(long bsize) {
		statfs.f_bsize(segment, (int) Math.min(MAX_UINT, bsize));
	}

	@Override
	public long getFrsize() {
		return getBsize();
	}

	@Override
	public void setFrsize(long frsize) {
		// no-op
	}

	@Override
	public long getBlocks() {
		return statfs.f_blocks(segment);
	}

	@Override
	public void setBlocks(long blocks) {
		statfs.f_blocks(segment, blocks);
	}

	@Override
	public long getBfree() {
		return statfs.f_bfree(segment);
	}

	@Override
	public void setBfree(long bfree) {
		statfs.f_bfree(segment, bfree);
	}

	@Override
	public long getBavail() {
		return statfs.f_bavail(segment);
	}

	@Override
	public void setBavail(long bavail) {
		statfs.f_bavail(segment, bavail);
	}

	@Override
	public long getNameMax() {
		return 255;
	}

	@Override
	public void setNameMax(long namemax) {
		// no-op
	}

}
