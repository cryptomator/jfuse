package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_conn_info;

import java.lang.foreign.MemorySegment;

class FuseConnInfoImpl317 extends FuseConnInfoImpl {

	FuseConnInfoImpl317(MemorySegment segment) {
		super(segment);
	}

	@Override
	public long capableExt() {
		return fuse_conn_info.capable_ext(segment);
	}

	@Override
	public long wantExt() {
		return fuse_conn_info.want_ext(segment);
	}

	@Override
	public void setWantExt(long wantExt) {
		fuse_conn_info.want_ext(segment, wantExt);
	}

	@Override
	public boolean setFeatureFlag(long flag) {
		return FuseFunctions.fuse_set_feature_flag(segment, flag);
	}

	@Override
	public void unsetFeatureFlag(long flag) {
		FuseFunctions.fuse_unset_feature_flag(segment, flag);
	}

	@Override
	public boolean getFeatureFlag(long flag) {
		return FuseFunctions.fuse_get_feature_flag(segment, flag);
	}

}
