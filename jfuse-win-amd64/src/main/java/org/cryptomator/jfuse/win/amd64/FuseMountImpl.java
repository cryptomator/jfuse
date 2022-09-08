package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.win.amd64.extr.fuse3_loop_config;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {

	@Override
	public int loop() {
		if (fuseArgs.multiThreaded()) {
			try (var scope = MemorySession.openConfined()) {
				var loopCfg = fuse3_loop_config.allocate(scope);
				fuse3_loop_config.clone_fd$set(loopCfg, 0);
				fuse3_loop_config.max_idle_threads$set(loopCfg, 10);
				return fuse_h.fuse3_loop_mt(fuse, loopCfg);
			}
		} else {
			return fuse_h.fuse3_loop(fuse);
		}
	}

	@Override
	public void unmount() {
		fuse_h.fuse3_exit(fuse);
		fuse_h.fuse3_unmount(fuse);
	}

	@Override
	public void destroy() {
		fuse_h.fuse3_destroy(fuse);
	}

}