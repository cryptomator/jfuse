package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_loop_config_v1;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {

	private static final int FUSE_3_2 = 32;
	private static final int FUSE_3_12 = 312;

	@Override
	public int loop() {
		// depends on fuse version: https://github.com/libfuse/libfuse/blob/fuse-3.12.0/include/fuse.h#L1011-L1050
		if (!fuseArgs.multithreaded() || fuse_h.fuse_version() < FUSE_3_2) {
			// FUSE 3.1: to keep things simple, we just don't support fuse_loop_mt
			return fuse_h.fuse_loop(fuse);
		} else if (fuse_h.fuse_version() < FUSE_3_12) {
			// FUSE 3.2
			try (var scope = MemorySession.openConfined()) {
				var loopCfg = fuse_loop_config_v1.allocate(scope);
				fuse_loop_config_v1.clone_fd$set(loopCfg, fuseArgs.cloneFd());
				fuse_loop_config_v1.max_idle_threads$set(loopCfg, fuseArgs.maxIdleThreads());
				return fuse_h.fuse_loop_mt(fuse, loopCfg);
			}
		} else {
			// FUSE 3.12
			var loopCfg = fuse_h.fuse_loop_cfg_create();
			try {
				fuse_h.fuse_loop_cfg_set_clone_fd(loopCfg, fuseArgs.cloneFd());
				fuse_h.fuse_loop_cfg_set_max_threads(loopCfg, fuseArgs.maxThreads());
				return fuse_h.fuse_loop_mt(fuse, loopCfg);
			} finally {
				fuse_h.fuse_loop_cfg_destroy(loopCfg);
			}
		}
	}

	@Override
	public void unmount() {
		fuse_h.fuse_exit(fuse);
		fuse_h.fuse_unmount(fuse);
	}

	@Override
	public void destroy() {
		fuse_h.fuse_destroy(fuse);
	}
}
