package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;

import java.lang.foreign.MemoryAddress;

record FuseMountImpl(MemoryAddress fuse, FuseArgs fuseArgs) implements FuseMount {
	@Override
	public int loop() {
		// TODO support fuse_loop_mt if args.multiThreaded()
		return fuse_h.fuse_loop(fuse);
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
