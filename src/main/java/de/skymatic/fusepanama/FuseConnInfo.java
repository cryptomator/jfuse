package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_conn_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public record FuseConnInfo(MemorySegment segment) {

	FuseConnInfo(MemoryAddress address, ResourceScope scope) {
		this(fuse_conn_info.ofAddress(address, scope));
	}

	public int protoMajor() {
		return fuse_conn_info.proto_major$get(segment);
	}

	public int protoMinor() {
		return fuse_conn_info.proto_minor$get(segment);
	}
}
