package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_loop_config;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {

	@Override
	public int loop() {
		if (fuseArgs.multithreaded() && fuse_h.fuse_version() > 312) { // TODO: support fuse < 3.12
			var loopCfg = fuse_h.fuse_loop_cfg_create();
			try {
				fuse_h.fuse_loop_cfg_set_clone_fd(loopCfg, 0);
				fuse_h.fuse_loop_cfg_set_max_threads (loopCfg, 4);
				return fuse_h.fuse_loop_mt(fuse, loopCfg);
			} finally {
				fuse_h.fuse_loop_cfg_destroy(loopCfg);
			}
		} else {
			return fuse_h.fuse_loop(fuse);
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
