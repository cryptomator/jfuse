package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_darwin_attr;

import java.lang.foreign.MemorySegment;

record StatImpl(MemorySegment segment) implements Stat {

	@Override
	public TimeSpec aTime() {
		return new TimeSpecImpl(fuse_darwin_attr.atimespec(segment));
	}

	@Override
	public TimeSpec cTime() {
		return new TimeSpecImpl(fuse_darwin_attr.ctimespec(segment));
	}

	@Override
	public TimeSpec mTime() {
		return new TimeSpecImpl(fuse_darwin_attr.mtimespec(segment));
	}

	@Override
	public TimeSpec birthTime() {
		return new TimeSpecImpl(fuse_darwin_attr.btimespec(segment));
	}

	@Override
	public TimeSpec backupTime() {
		return new TimeSpecImpl(fuse_darwin_attr.bkuptimespec(segment));
	}

	@Override
	public void setMode(int mode) {
		fuse_darwin_attr.mode(segment, (short) mode);
	}

	@Override
	public int getMode() {
		return fuse_darwin_attr.mode(segment);
	}

	@Override
	public void setUid(int uid) {
		fuse_darwin_attr.uid(segment, uid);
	}

	@Override
	public int getUid() {
		return fuse_darwin_attr.uid(segment);
	}

	@Override
	public void setGid(int gid) {
		fuse_darwin_attr.gid(segment, gid);
	}

	@Override
	public int getGid() {
		return fuse_darwin_attr.gid(segment);
	}

	@Override
	public void setNLink(short count) {
		fuse_darwin_attr.nlink(segment, count);
	}

	@Override
	public long getNLink() {
		return fuse_darwin_attr.nlink(segment);
	}

	@Override
	public void setSize(long size) {
		fuse_darwin_attr.size(segment, size);
	}

	@Override
	public long getSize() {
		return fuse_darwin_attr.size(segment);
	}

}
