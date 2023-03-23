package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.win.extr.fuse3_loop_config;
import org.cryptomator.jfuse.win.extr.fuse_h;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseMountImpl(MemorySegment fuse, FuseArgs fuseArgs) implements FuseMount {

	@Override
	public int loop() {
		if (fuseArgs.multiThreaded()) {
			try (var arena = Arena.openConfined()) {
				var loopCfg = fuse3_loop_config.allocate(arena);
				fuse3_loop_config.clone_fd$set(loopCfg,0);
				fuse3_loop_config.max_idle_threads$set(loopCfg, 5);
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