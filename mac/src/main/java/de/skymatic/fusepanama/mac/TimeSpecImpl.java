package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.mac.lowlevel.stat_h;
import de.skymatic.fusepanama.mac.lowlevel.timespec;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;

record TimeSpecImpl(MemorySegment segment) implements TimeSpec {

	@Override
	public boolean isUtimeOmit() {
		var nanos = timespec.tv_nsec$get(segment);
		return stat_h.UTIME_OMIT() == nanos;
	}

	@Override
	public boolean isUtimeNow() {
		var nanos = timespec.tv_nsec$get(segment);
		return stat_h.UTIME_NOW() == nanos;
	}

	@Override
	public void set(Instant newValue) {
		timespec.tv_sec$set(segment, newValue.getEpochSecond());
		timespec.tv_nsec$set(segment, newValue.getNano());
	}

	@Override
	public Instant get() {
		var seconds = timespec.tv_sec$get(segment);
		var nanos = timespec.tv_nsec$get(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
