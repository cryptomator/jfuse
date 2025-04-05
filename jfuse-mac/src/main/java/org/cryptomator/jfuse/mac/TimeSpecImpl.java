package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.stat.stat_h;
import org.cryptomator.jfuse.mac.extr.fuse.timespec;
import java.lang.foreign.MemorySegment;

import java.time.Instant;

record TimeSpecImpl(MemorySegment segment) implements TimeSpec {

	@Override
	public boolean isUtimeOmit() {
		var nanos = timespec.tv_nsec(segment);
		return stat_h.UTIME_OMIT() == nanos;
	}

	@Override
	public boolean isUtimeNow() {
		var nanos = timespec.tv_nsec(segment);
		return stat_h.UTIME_NOW() == nanos;
	}

	@Override
	public void set(Instant newValue) {
		timespec.tv_sec(segment, newValue.getEpochSecond());
		timespec.tv_nsec(segment, newValue.getNano());
	}

	@Override
	public Instant get() {
		var seconds = timespec.tv_sec(segment);
		var nanos = timespec.tv_nsec(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
