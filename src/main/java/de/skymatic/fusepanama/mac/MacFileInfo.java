package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.FileInfo;
import de.skymatic.fusepanama.mac.lowlevel.fuse_file_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public record MacFileInfo(MemorySegment segment) implements FileInfo {

	public MacFileInfo(MemoryAddress address, ResourceScope scope) {
		this(fuse_file_info.ofAddress(address, scope));
	}

	@Override
	public long getFh() {
		return fuse_file_info.fh$get(segment);
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
