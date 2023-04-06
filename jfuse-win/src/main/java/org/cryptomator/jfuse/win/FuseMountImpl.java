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
			//We cannot use fuse3_loop_mt(fuse, fuseconfig) due to https://github.com/cryptomator/jfuse/issues/31
			return fuse_h.fuse3_loop_mt_31(fuse, 0);
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