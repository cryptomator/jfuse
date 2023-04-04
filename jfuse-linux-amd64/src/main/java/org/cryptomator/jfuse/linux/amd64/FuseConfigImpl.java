package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_config;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	public FuseConfigImpl(MemorySegment address, SegmentScope scope) {
		this(fuse_config.ofAddress(address, scope));
	}

	@Override
	public int setGid() {
		return fuse_config.set_gid$get(segment);
	}

	@Override
	public int gid() {
		return fuse_config.gid$get(segment);
	}

	@Override
	public int setUid() {
		return fuse_config.set_uid$get(segment);
	}

	@Override
	public int uid() {
		return fuse_config.uid$get(segment);
	}

	@Override
	public int setMode() {
		return fuse_config.set_mode$get(segment);
	}

	@Override
	public int umask() {
		return fuse_config.umask$get(segment);
	}

	@Override
	public double entryTimeout() {
		return fuse_config.entry_timeout$get(segment);
	}

	@Override
	public double negativeTimeout() {
		return fuse_config.negative_timeout$get(segment);
	}

	@Override
	public double attrTimeout() {
		return fuse_config.attr_timeout$get(segment);
	}

	@Override
	public int intr() {
		return fuse_config.intr$get(segment);
	}

	@Override
	public int intrSignal() {
		return fuse_config.intr_signal$get(segment);
	}

	@Override
	public int remember() {
		return fuse_config.remember$get(segment);
	}

	@Override
	public int hardRemove() {
		return fuse_config.hard_remove$get(segment);
	}

	@Override
	public int useIno() {
		return fuse_config.use_ino$get(segment);
	}

	@Override
	public int readdirIno() {
		return fuse_config.readdir_ino$get(segment);
	}

	@Override
	public int directIo() {
		return fuse_config.direct_io$get(segment);
	}

	@Override
	public int kernelCache() {
		return fuse_config.kernel_cache$get(segment);
	}

	@Override
	public int autoCache() {
		return fuse_config.auto_cache$get(segment);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse_config.ac_attr_timeout_set$get(segment);
	}

	@Override
	public double acAttrTimeout() {
		return fuse_config.ac_attr_timeout$get(segment);
	}

	@Override
	public int nullpathOk() {
		return fuse_config.nullpath_ok$get(segment);
	}

	@Override
	public int noRofdFlush() {
		return fuse_config.no_rofd_flush$get(segment);
	}

}
