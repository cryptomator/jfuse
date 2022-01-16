package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Statvfs;
import de.skymatic.fusepanama.win.amd64.lowlevel.fuse_statvfs;
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
		if (blocks > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			fuse_statvfs.f_blocks$set(segment, (int) blocks);
		}
	}

	@Override
	public long getBfree() {
		return fuse_statvfs.f_bfree$get(segment);
	}

	@Override
	public void setBfree(long bfree) {
		if (bfree > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			fuse_statvfs.f_bfree$set(segment, (int) bfree);
		}
	}

	@Override
	public long getBavail() {
		return fuse_statvfs.f_bavail$get(segment);
	}

	@Override
	public void setBavail(long bavail) {
		if (bavail > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			fuse_statvfs.f_bavail$set(segment, (int) bavail);
		}
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
