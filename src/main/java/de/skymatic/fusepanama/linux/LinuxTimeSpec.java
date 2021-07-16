package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.linux.lowlevel.timespec;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;

record LinuxTimeSpec(MemorySegment segment) implements TimeSpec {

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
