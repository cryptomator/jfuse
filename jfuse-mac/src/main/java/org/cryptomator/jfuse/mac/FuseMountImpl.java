package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_h;

import java.lang.foreign.MemorySegment;

record FuseMountImpl(MemorySegment fuse, FuseArgs fuseArgs) implements FuseMount {

	@Override
	public int loop() {
		if (!fuseArgs.multithreaded()) {
			return fuse_h.fuse_loop(fuse);
		} else {
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
