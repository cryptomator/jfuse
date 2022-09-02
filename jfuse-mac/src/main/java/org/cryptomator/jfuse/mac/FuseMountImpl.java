package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.mac.extr.fuse_h;

import java.lang.foreign.MemoryAddress;

record FuseMountImpl(MemoryAddress fuse, MemoryAddress ch, FuseArgs args) implements FuseMount {

	public void loop() {
		// TODO support fuse_loop_mt if args.multiThreaded()
		fuse_h.fuse_loop(fuse);
		System.out.println("fuse_loop finished"); // TODO remove
	}

	public void unmount() {
		fuse_h.fuse_exit(fuse);
		fuse_h.fuse_unmount(args.mountPoint(), ch);
	}

	public void destroy() {
		fuse_h.fuse_destroy(fuse);
	}

}
