package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_conn_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

record FuseConnInfoImpl(MemorySegment segment) implements FuseConnInfo {

	public FuseConnInfoImpl(MemoryAddress address, ResourceScope scope) {
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
