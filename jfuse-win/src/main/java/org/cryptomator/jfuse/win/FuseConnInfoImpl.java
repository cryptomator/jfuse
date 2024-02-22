package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_conn_info;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseConnInfoImpl(MemorySegment segment) implements FuseConnInfo {

	@Override
	public int protoMajor() {
		return fuse3_conn_info.proto_major(segment);
	}

	@Override
	public int protoMinor() {
		return fuse3_conn_info.proto_minor(segment);
	}

	@Override
	public int capable() {
		return fuse3_conn_info.capable(segment);
	}

	@Override
	public int want() {
		return fuse3_conn_info.want(segment);
	}

	@Override
	public void setWant(int wanted) {
		fuse3_conn_info.want(segment, wanted);
	}

	@Override
	public int maxWrite() {
		return fuse3_conn_info.max_write(segment);
	}

	@Override
	public void setMaxWrite(int maxWrite) {
		fuse3_conn_info.max_write(segment, maxWrite);
	}

	@Override
	public int maxRead() {
		return fuse3_conn_info.max_read(segment);
	}

	@Override
	public void setMaxRead(int maxRead) {
		fuse3_conn_info.max_read(segment, maxRead);
	}

	@Override
	public int maxReadahead() {
		return fuse3_conn_info.max_readahead(segment);
	}

	@Override
	public void setMaxReadahead(int maxReadahead) {
		fuse3_conn_info.max_readahead(segment, maxReadahead);
	}

	@Override
	public int maxBackground() {
		return fuse3_conn_info.max_background(segment);
	}

	@Override
	public void setMaxBackground(int maxBackground) {
		fuse3_conn_info.max_background(segment, maxBackground);
	}

	@Override
	public int congestionThreshold() {
		return fuse3_conn_info.congestion_threshold(segment);
	}

	@Override
	public void setCongestionThreshold(int congestionThreshold) {
		fuse3_conn_info.congestion_threshold(segment, congestionThreshold);
	}

	@Override
	public int timeGran() {
		return fuse3_conn_info.time_gran(segment);
	}

	@Override
	public void setTimeGran(int timeGran) {
		fuse3_conn_info.time_gran(segment, timeGran);
	}

}
