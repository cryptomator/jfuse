package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.win.extr.fuse3_config;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	public FuseConfigImpl(MemoryAddress address, MemorySession scope) {
		this(fuse3_config.ofAddress(address, scope));
	}

	@Override
	public int setGid() {
		return fuse3_config.set_gid$get(segment);
	}

	@Override
	public int gid() {
		return fuse3_config.gid$get(segment);
	}

	@Override
	public int setUid() {
		return fuse3_config.set_uid$get(segment);
	}

	@Override
	public int uid() {
		return fuse3_config.uid$get(segment);
	}

	@Override
	public int setMode() {
		return fuse3_config.set_mode$get(segment);
	}

	@Override
	public int umask() {
		return fuse3_config.umask$get(segment);
	}

	@Override
	public double entryTimeout() {
		return fuse3_config.entry_timeout$get(segment);
	}

	@Override
	public double negativeTimeout() {
		return fuse3_config.negative_timeout$get(segment);
	}

	@Override
	public double attrTimeout() {
		return fuse3_config.attr_timeout$get(segment);
	}

	@Override
	public int intr() {
		return fuse3_config.intr$get(segment);
	}

	@Override
	public int intrSignal() {
		return fuse3_config.intr_signal$get(segment);
	}

	@Override
	public int remember() {
		return fuse3_config.remember$get(segment);
	}

	@Override
	public int hardRemove() {
		return fuse3_config.hard_remove$get(segment);
	}

	@Override
	public int useIno() {
		return fuse3_config.use_ino$get(segment);
	}

	@Override
	public int readdirIno() {
		return fuse3_config.readdir_ino$get(segment);
	}

	@Override
	public int directIo() {
		return fuse3_config.direct_io$get(segment);
	}

	@Override
	public int kernelCache() {
		return fuse3_config.kernel_cache$get(segment);
	}

	@Override
	public int autoCache() {
		return fuse3_config.auto_cache$get(segment);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse3_config.ac_attr_timeout_set$get(segment);
	}

	@Override
	public double acAttrTimeout() {
		return fuse3_config.ac_attr_timeout$get(segment);
	}

	@Override
	public int nullpathOk() {
		return fuse3_config.nullpath_ok$get(segment);
	}

	@Override
	public int noRofdFlush() {
		return 0;
	}

}
