package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.win.extr.fuse3_conn_info;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record FuseConnInfoImpl(MemorySegment segment) implements FuseConnInfo {

	public FuseConnInfoImpl(MemoryAddress address, MemorySession scope) {
		this(fuse3_conn_info.ofAddress(address, scope));
	}

	@Override
	public int protoMajor() {
		return fuse3_conn_info.proto_major$get(segment);
	}

	@Override
	public int protoMinor() {
		return fuse3_conn_info.proto_minor$get(segment);
	}

	@Override
	public int capable() {
		return fuse3_conn_info.capable$get(segment);
	}

	@Override
	public int want() {
		return fuse3_conn_info.want$get(segment);
	}

	@Override
	public void setWant(int wanted) {
		fuse3_conn_info.want$set(segment, wanted);
	}

	@Override
	public int maxWrite() {
		return fuse3_conn_info.max_write$get(segment);
	}

	@Override
	public void setMaxWrite(int maxWrite) {
		fuse3_conn_info.max_write$set(segment, maxWrite);
	}

	@Override
	public int maxRead() {
		return fuse3_conn_info.max_read$get(segment);
	}

	@Override
	public void setMaxRead(int maxRead) {
		fuse3_conn_info.max_read$set(segment, maxRead);
	}

	@Override
	public int maxReadahead() {
		return fuse3_conn_info.max_readahead$get(segment);
	}

	@Override
	public void setMaxReadahead(int maxReadahead) {
		fuse3_conn_info.max_readahead$set(segment, maxReadahead);
	}

	@Override
	public int maxBackground() {
		return fuse3_conn_info.max_background$get(segment);
	}

	@Override
	public void setMaxBackground(int maxBackground) {
		fuse3_conn_info.max_background$set(segment, maxBackground);
	}

	@Override
	public int congestionThreshold() {
		return fuse3_conn_info.congestion_threshold$get(segment);
	}

	@Override
	public void setCongestionThreshold(int congestionThreshold) {
		fuse3_conn_info.congestion_threshold$set(segment, congestionThreshold);
	}

	@Override
	public int timeGran() {
		return fuse3_conn_info.time_gran$get(segment);
	}

	@Override
	public void setTimeGran(int timeGran) {
		fuse3_conn_info.time_gran$set(segment, timeGran);
	}

}
