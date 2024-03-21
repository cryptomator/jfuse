package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_statvfs;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	@Override
	public long getBsize() {
		return fuse_statvfs.f_bsize(segment);
	}

	@Override
	public void setBsize(long bsize) {
		fuse_statvfs.f_bsize(segment, bsize);
	}

	@Override
	public long getFrsize() {
		return fuse_statvfs.f_frsize(segment);
	}

	@Override
	public void setFrsize(long frsize) {
		fuse_statvfs.f_frsize(segment, frsize);
	}

	@Override
	public long getBlocks() {
		return fuse_statvfs.f_blocks(segment);
	}

	@Override
	public void setBlocks(long blocks) {
		fuse_statvfs.f_blocks(segment, blocks);
	}

	@Override
	public long getBfree() {
		return fuse_statvfs.f_bfree(segment);
	}

	@Override
	public void setBfree(long bfree) {
		fuse_statvfs.f_bfree(segment, bfree);
	}

	@Override
	public long getBavail() {
		return fuse_statvfs.f_bavail(segment);
	}

	@Override
	public void setBavail(long bavail) {
		fuse_statvfs.f_bavail(segment, bavail);
	}

	@Override
	public long getNameMax() {
		return fuse_statvfs.f_namemax(segment);
	}

	@Override
	public void setNameMax(long namemax) {
		fuse_statvfs.f_namemax(segment, namemax);
	}

}
