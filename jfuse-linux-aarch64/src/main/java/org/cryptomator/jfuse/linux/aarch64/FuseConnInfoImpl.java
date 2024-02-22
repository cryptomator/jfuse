package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_conn_info;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseConnInfoImpl(MemorySegment segment) implements FuseConnInfo {

	@Override
	public int protoMajor() {
		return fuse_conn_info.proto_major(segment);
	}

	@Override
	public int protoMinor() {
		return fuse_conn_info.proto_minor(segment);
	}

	@Override
	public int capable() {
		return fuse_conn_info.capable(segment);
	}

	@Override
	public int want() {
		return fuse_conn_info.want(segment);
	}

	@Override
	public void setWant(int wanted) {
		fuse_conn_info.want(segment, wanted);
	}

	@Override
	public int maxWrite() {
		return fuse_conn_info.max_write(segment);
	}

	@Override
	public void setMaxWrite(int maxWrite) {
		fuse_conn_info.max_write(segment, maxWrite);
	}

	@Override
	public int maxRead() {
		return fuse_conn_info.max_read(segment);
	}

	@Override
	public void setMaxRead(int maxRead) {
		fuse_conn_info.max_read(segment, maxRead);
	}

	@Override
	public int maxReadahead() {
		return fuse_conn_info.max_readahead(segment);
	}

	@Override
	public void setMaxReadahead(int maxReadahead) {
		fuse_conn_info.max_readahead(segment, maxReadahead);
	}

	@Override
	public int maxBackground() {
		return fuse_conn_info.max_background(segment);
	}

	@Override
	public void setMaxBackground(int maxBackground) {
		fuse_conn_info.max_background(segment, maxBackground);
	}

	@Override
	public int congestionThreshold() {
		return fuse_conn_info.congestion_threshold(segment);
	}

	@Override
	public void setCongestionThreshold(int congestionThreshold) {
		fuse_conn_info.congestion_threshold(segment, congestionThreshold);
	}

	@Override
	public int timeGran() {
		return fuse_conn_info.time_gran(segment);
	}

	@Override
	public void setTimeGran(int timeGran) {
		fuse_conn_info.time_gran(segment, timeGran);
	}

}
