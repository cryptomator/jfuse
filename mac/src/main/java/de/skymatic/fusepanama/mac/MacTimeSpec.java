package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.mac.lowlevel.stat_h;
import de.skymatic.fusepanama.mac.lowlevel.timespec;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;
import java.util.Optional;

record MacTimeSpec(MemorySegment segment) implements TimeSpec {

	@Override
	public void set(Instant newValue) {
		timespec.tv_sec$set(segment, newValue.getEpochSecond());
		timespec.tv_nsec$set(segment, newValue.getNano());
	}

	@Override
	public Optional<Instant> get() {
		var seconds = timespec.tv_sec$get(segment);
		var nanos = timespec.tv_nsec$get(segment);
		if (stat_h.UTIME_OMIT() == nanos) {
			return Optional.empty();
		} else if (stat_h.UTIME_NOW() == nanos) {
			return Optional.of(Instant.now());
		} else {
			return Optional.of(Instant.ofEpochSecond(seconds, nanos));
		}
	}

}
