package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;

import java.lang.foreign.MemoryAddress;
import java.util.concurrent.atomic.AtomicReference;

record FuseMountImpl(AtomicReference<MemoryAddress> fuseHandle) implements FuseMount {
	@Override
	public void loop() {
		// no-op
	}

	@Override
	public void unmount() {
		var actualHandle = fuseHandle.getAndSet(null);
		if (actualHandle != null) {
			fuse_h.fuse_exit(actualHandle);
		}
	}

	@Override
	public void destroy() {
		// no-op
	}
}
