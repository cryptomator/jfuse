package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_config;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	@Override
	public int setGid() {
		return fuse3_config.set_gid(segment);
	}

	@Override
	public int gid() {
		return fuse3_config.gid(segment);
	}

	@Override
	public int setUid() {
		return fuse3_config.set_uid(segment);
	}

	@Override
	public int uid() {
		return fuse3_config.uid(segment);
	}

	@Override
	public int setMode() {
		return fuse3_config.set_mode(segment);
	}

	@Override
	public int umask() {
		return fuse3_config.umask(segment);
	}

	@Override
	public double entryTimeout() {
		return fuse3_config.entry_timeout(segment);
	}

	@Override
	public double negativeTimeout() {
		return fuse3_config.negative_timeout(segment);
	}

	@Override
	public double attrTimeout() {
		return fuse3_config.attr_timeout(segment);
	}

	@Override
	public int intr() {
		return fuse3_config.intr(segment);
	}

	@Override
	public int intrSignal() {
		return fuse3_config.intr_signal(segment);
	}

	@Override
	public int remember() {
		return fuse3_config.remember(segment);
	}

	@Override
	public int hardRemove() {
		return fuse3_config.hard_remove(segment);
	}

	@Override
	public int useIno() {
		return fuse3_config.use_ino(segment);
	}

	@Override
	public int readdirIno() {
		return fuse3_config.readdir_ino(segment);
	}

	@Override
	public int directIo() {
		return fuse3_config.direct_io(segment);
	}

	@Override
	public int kernelCache() {
		return fuse3_config.kernel_cache(segment);
	}

	@Override
	public int autoCache() {
		return fuse3_config.auto_cache(segment);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse3_config.ac_attr_timeout_set(segment);
	}

	@Override
	public double acAttrTimeout() {
		return fuse3_config.ac_attr_timeout(segment);
	}

	@Override
	public int nullpathOk() {
		return fuse3_config.nullpath_ok(segment);
	}

	@Override
	public int noRofdFlush() {
		return 0;
	}

}
