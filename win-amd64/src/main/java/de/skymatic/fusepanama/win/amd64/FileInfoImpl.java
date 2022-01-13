package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.win.amd64.lowlevel.fuse_file_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record FileInfoImpl(MemorySegment segment) implements FileInfo {

	public FileInfoImpl(MemoryAddress address, ResourceScope scope) {
		this(fuse_file_info.ofAddress(address, scope));
	}

	@Override
	public long getFh() {
		return fuse_file_info.fh$get(segment);
	}

	@Override
	public void setFh(long fh) {
		fuse_file_info.fh$set(segment, fh);
	}

	@Override
	public int getFlags() {
		return fuse_file_info.flags$get(segment);
	}

	@Override
	public long getLockOwner() {
		return fuse_file_info.lock_owner$get(segment);
	}

}
