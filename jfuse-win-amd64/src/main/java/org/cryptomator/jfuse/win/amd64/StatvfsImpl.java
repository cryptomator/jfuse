package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.win.amd64.extr.fuse_statvfs;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record StatvfsImpl(MemorySegment segment) implements Statvfs {

	public StatvfsImpl(MemoryAddress address, ResourceScope scope) {
		this(fuse_statvfs.ofAddress(address, scope));
	}

	@Override
	public long getBsize() {
		return fuse_statvfs.f_bsize$get(segment);
	}

	@Override
	public void setBsize(long bsize) {
		fuse_statvfs.f_bsize$set(segment, bsize);
	}

	@Override
	public long getFrsize() {
		return fuse_statvfs.f_frsize$get(segment);
	}

	@Override
	public void setFrsize(long frsize) {
		fuse_statvfs.f_frsize$set(segment, frsize);
	}

	@Override
	public long getBlocks() {
		return fuse_statvfs.f_blocks$get(segment);
	}

	@Override
	public void setBlocks(long blocks) {
		fuse_statvfs.f_blocks$set(segment, blocks);
	}

	@Override
	public long getBfree() {
		return fuse_statvfs.f_bfree$get(segment);
	}

	@Override
	public void setBfree(long bfree) {
		fuse_statvfs.f_bfree$set(segment, bfree);
	}

	@Override
	public long getBavail() {
		return fuse_statvfs.f_bavail$get(segment);
	}

	@Override
	public void setBavail(long bavail) {
		fuse_statvfs.f_bavail$set(segment, bavail);
	}

	@Override
	public long getNameMax() {
		return fuse_statvfs.f_namemax$get(segment);
	}

	@Override
	public void setNameMax(long namemax) {
		fuse_statvfs.f_namemax$set(segment, namemax);
	}

}
