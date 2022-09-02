package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_loop_config;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {
	@Override
	public int loop() {
		if (fuseArgs.multithreaded()) {
			try (var scope = MemorySession.openConfined()) {
				var loopCfg = fuse_loop_config.allocate(scope);
				fuse_loop_config.clone_fd$set(loopCfg, 0);
				fuse_loop_config.max_idle_threads$set(loopCfg, 10);
				return fuse_h.fuse_loop_mt(fuse, loopCfg);
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
