package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_conn_info;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record FuseConnInfoImpl(MemorySegment segment) implements FuseConnInfo {

	public FuseConnInfoImpl(MemoryAddress address, MemorySession scope) {
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
