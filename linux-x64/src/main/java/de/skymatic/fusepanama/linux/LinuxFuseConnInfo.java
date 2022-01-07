package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.FuseConnInfo;
import de.skymatic.fusepanama.linux.lowlevel.fuse_conn_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record LinuxFuseConnInfo(MemorySegment segment) implements FuseConnInfo {

	public LinuxFuseConnInfo(MemoryAddress address, ResourceScope scope) {
		this(fuse_conn_info.ofAddress(address, scope));
	}

	@Override
	public int protoMajor() {
		return fuse_conn_info.proto_major$get(segment);
	}

	@Override
	public int protoMinor() {
		return fuse_conn_info.proto_minor$get(segment);
	}
}
