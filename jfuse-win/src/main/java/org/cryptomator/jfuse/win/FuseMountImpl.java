package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_loop_config;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_h;

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
		// Signalling the run loop is all we may do here: WinFsp's fuse3_unmount() does not unmount, it frees `struct fuse`
		// (fsp_fuse3_unmount() -> fsp_fuse_destroy()), which the run loop still dereferences while it winds down.
		// The volume is detached by the run loop itself, in fsp_fuse_loop_stop() -> FspFileSystemDelete(). See https://github.com/cryptomator/jfuse/issues/176
		fuse_h.fuse3_exit(fuse);
	}

	@Override
	public void destroy() {
		fuse_h.fuse3_unmount(fuse);
		fuse_h.fuse3_destroy(fuse);
	}

}