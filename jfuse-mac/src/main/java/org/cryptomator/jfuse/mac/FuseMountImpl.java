package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_h;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_loop_config_v1;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

record FuseMountImpl(MemorySegment fuse, FuseArgs fuseArgs) implements FuseMount {

	private static final int FUSE_3_2 = 32;
	private static final int FUSE_3_12 = 312;

	@Override
	public int loop() {
		// macFUSE FSKit backend requires fuse_mount and fuse_loop on the same thread
		if (fuse_h.fuse_mount(fuse, fuseArgs.mountPoint()) != 0) {
			throw new RuntimeException(new FuseMountFailedException("fuse_mount failed"));
		}
		if (!fuseArgs.multithreaded() || fuse_h.fuse_version() < FUSE_3_2) {
			return fuse_h.fuse_loop(fuse);
		} else if (fuse_h.fuse_version() < FUSE_3_12) {
			try (var arena = Arena.ofConfined()) {
				var loopCfg = fuse_loop_config_v1.allocate(arena);
				fuse_loop_config_v1.clone_fd(loopCfg, fuseArgs.cloneFd());
				fuse_loop_config_v1.max_idle_threads(loopCfg, fuseArgs.maxIdleThreads());
				return fuse_h.fuse_loop_mt(fuse, loopCfg);
			}
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
