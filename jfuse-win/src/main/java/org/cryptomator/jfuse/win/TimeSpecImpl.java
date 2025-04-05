package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_timespec;

import java.lang.foreign.MemorySegment;
import java.time.Instant;

record TimeSpecImpl(MemorySegment segment) implements TimeSpec {

	@Override
	public boolean isUtimeOmit() {
		// no magic values on Windows...
		return false;
	}

	@Override
	public boolean isUtimeNow() {
		// no magic values on Windows...
		return false;
	}

	@Override
	public void set(Instant newValue) {
		fuse_timespec.tv_sec(segment, newValue.getEpochSecond());
		fuse_timespec.tv_nsec(segment, newValue.getNano());
	}

	@Override
	public Instant get() {
		var seconds = fuse_timespec.tv_sec(segment);
		var nanos = fuse_timespec.tv_nsec(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
