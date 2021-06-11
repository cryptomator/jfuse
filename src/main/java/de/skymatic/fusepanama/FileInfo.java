package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_file_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public class FileInfo {

	private final MemorySegment segment;

	FileInfo(MemoryAddress address, ResourceScope scope) {
		this(fuse_file_info.ofAddress(address, scope));
	}

	FileInfo(MemorySegment segment) {
		this.segment = segment;
	}

	public long getFh() {
		return fuse_file_info.fh$get(segment);
	}

	public int getFlags() {
		return fuse_file_info.flags$get(segment);
	}

	public long getLockOwner() {
		return fuse_file_info.lock_owner$get(segment);
	}

}
