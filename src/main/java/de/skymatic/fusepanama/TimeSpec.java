package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.timespec;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;

public class TimeSpec {

	private final MemorySegment segment;

	TimeSpec(MemorySegment segment) {
		this.segment = segment;
	}

	public void set(Instant newValue) {
		timespec.tv_sec$set(segment, newValue.getEpochSecond());
		timespec.tv_nsec$set(segment, newValue.getNano());
	}

	public Instant get() {
		var seconds = timespec.tv_sec$get(segment);
		var nanos = timespec.tv_nsec$get(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
