package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Statvfs;
import de.skymatic.fusepanama.mac.lowlevel.statvfs;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record MacStatvfs(MemorySegment segment) implements Statvfs {

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
		if (blocks > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			statvfs.f_blocks$set(segment, (int) blocks);
		}
	}

	@Override
	public long getBfree() {
		return statvfs.f_bfree$get(segment);
	}

	@Override
	public void setBfree(long bfree) {
		if (bfree > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			statvfs.f_bfree$set(segment, (int) bfree);
		}
	}

	@Override
	public long getBavail() {
		return statvfs.f_bavail$get(segment);
	}

	@Override
	public void setBavail(long bavail) {
		if (bavail > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Max supported number of blocks: " + Integer.MAX_VALUE);
		} else {
			statvfs.f_bavail$set(segment, (int) bavail);
		}
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
