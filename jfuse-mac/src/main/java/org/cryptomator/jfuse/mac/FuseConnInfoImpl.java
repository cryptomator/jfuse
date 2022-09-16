package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.mac.extr.fuse_conn_info;

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
	@Override
	public int capable() {
		return fuse_conn_info.capable$get(segment);
	}

	@Override
	public int want() {
		return fuse_conn_info.want$get(segment);
	}
	@Override
	public void setWant(int wanted) {
		fuse_conn_info.want$set(segment, wanted);
	}

	@Override
	public int maxWrite() {
		return fuse_conn_info.max_write$get(segment);
	}

	@Override
	public void setMaxWrite(int maxWrite) {
		fuse_conn_info.max_write$set(segment, maxWrite);
	}

	@Override
	public int maxReadahead() {
		return fuse_conn_info.max_readahead$get(segment);
	}

	@Override
	public void setMaxReadahead(int maxReadahead) {
		fuse_conn_info.max_readahead$set(segment, maxReadahead);
	}

	@Override
	public int maxBackground() {
		return fuse_conn_info.max_background$get(segment);
	}

	@Override
	public void setMaxBackground(int maxBackground) {
		fuse_conn_info.max_background$set(segment, maxBackground);
	}

	@Override
	public int congestionThreshold() {
		return fuse_conn_info.congestion_threshold$get(segment);
	}

	@Override
	public void setCongestionThreshold(int congestionThreshold) {
		fuse_conn_info.congestion_threshold$set(segment, congestionThreshold);
	}

	@Override
	public int asyncRead() {
		return fuse_conn_info.async_read$get(segment);
	}

	@Override
	public void setAsyncRead(int asyncRead) {
		fuse_conn_info.async_read$set(segment, asyncRead);
	}

}
