package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_conn_info;

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

	// libfuse 3.17.0 - 3.17.2 only provide the fuse_*_feature_flag functions as static inline functions in fuse_common.h,
	// hence they are not available via symbol lookup. Their bodies are replicated below, so that this class works on every 3.17.x.
	// Like libfuse 3.17.3 and newer, the fallbacks keep the deprecated want field in sync with the lower 32 bits of want_ext.
	// If the native function is accessible, it is called instead of our manual code to also account for any changes

	@Override
	public boolean setFeatureFlag(long flag) {
		if (FuseFunctions.hasFeatureFlagFunctions()) {
			return FuseFunctions.fuse_set_feature_flag(segment, flag);
		} else if ((capableExt() & flag) == 0) {
			return false;
		} else {
			setWantExt(wantExt() | flag);
			setWant((int) wantExt());
			return true;
		}
	}

	@Override
	public void unsetFeatureFlag(long flag) {
		if (FuseFunctions.hasFeatureFlagFunctions()) {
			FuseFunctions.fuse_unset_feature_flag(segment, flag);
		} else {
			setWantExt(wantExt() & ~flag);
			setWant((int) wantExt());
		}
	}

	@Override
	public boolean getFeatureFlag(long flag) {
		if (FuseFunctions.hasFeatureFlagFunctions()) {
			return FuseFunctions.fuse_get_feature_flag(segment, flag);
		} else {
			return (capableExt() & flag) != 0;
		}
	}

}
