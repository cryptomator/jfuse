package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;

import java.lang.foreign.MemoryAddress;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {

	@Override
	public int loop() {
		if (fuseArgs.multiThreaded()) {
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