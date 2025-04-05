package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.mac.extr.fuse.fuse_h;

import java.lang.foreign.MemorySegment;

record FuseMountImpl(MemorySegment fuse, MemorySegment ch, FuseArgs args) implements FuseMount {

	@Override
	public int loop() {
		if (args.multiThreaded()) {
			return fuse_h.fuse_loop_mt(fuse);
		} else {
			return fuse_h.fuse_loop(fuse);
		}
	}

	@Override
	public void unmount() {
		fuse_h.fuse_exit(fuse);
		fuse_h.fuse_unmount(args.mountPoint(), ch);
	}

	@Override
	public void destroy() {
		fuse_h.fuse_destroy(fuse);
	}

}
