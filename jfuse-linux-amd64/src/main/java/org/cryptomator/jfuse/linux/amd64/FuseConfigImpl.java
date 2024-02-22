package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_config;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	@Override
	public int setGid() {
		return fuse_config.set_gid(segment);
	}

	@Override
	public int gid() {
		return fuse_config.gid(segment);
	}

	@Override
	public int setUid() {
		return fuse_config.set_uid(segment);
	}

	@Override
	public int uid() {
		return fuse_config.uid(segment);
	}

	@Override
	public int setMode() {
		return fuse_config.set_mode(segment);
	}

	@Override
	public int umask() {
		return fuse_config.umask(segment);
	}

	@Override
	public double entryTimeout() {
		return fuse_config.entry_timeout(segment);
	}

	@Override
	public double negativeTimeout() {
		return fuse_config.negative_timeout(segment);
	}

	@Override
	public double attrTimeout() {
		return fuse_config.attr_timeout(segment);
	}

	@Override
	public int intr() {
		return fuse_config.intr(segment);
	}

	@Override
	public int intrSignal() {
		return fuse_config.intr_signal(segment);
	}

	@Override
	public int remember() {
		return fuse_config.remember(segment);
	}

	@Override
	public int hardRemove() {
		return fuse_config.hard_remove(segment);
	}

	@Override
	public int useIno() {
		return fuse_config.use_ino(segment);
	}

	@Override
	public int readdirIno() {
		return fuse_config.readdir_ino(segment);
	}

	@Override
	public int directIo() {
		return fuse_config.direct_io(segment);
	}

	@Override
	public int kernelCache() {
		return fuse_config.kernel_cache(segment);
	}

	@Override
	public int autoCache() {
		return fuse_config.auto_cache(segment);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse_config.ac_attr_timeout_set(segment);
	}

	@Override
	public double acAttrTimeout() {
		return fuse_config.ac_attr_timeout(segment);
	}

	@Override
	public int nullpathOk() {
		return fuse_config.nullpath_ok(segment);
	}

	@Override
	public int noRofdFlush() {
		return fuse_config.no_rofd_flush(segment);
	}

}
