package de.skymatic.fusepanama;

import de.skymatic.fusepanama.macfuse.fuse_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class FileInfo {

	private final MemorySegment segment;

	FileInfo(MemoryAddress address) {
		this(fuse_h.fuse_file_info.ofAddressRestricted(address));
	}

	FileInfo(MemorySegment segment) {
		this.segment = segment;
	}

	public long getFh() {
		return fuse_h.fuse_file_info.fh$get(segment);
	}

	public int getFlags() {
		return fuse_h.fuse_file_info.flags$get(segment);
	}

	public long getLockOwner() {
		return fuse_h.fuse_file_info.lock_owner$get(segment);
	}

}
