package de.skymatic.fusepanama;

import de.skymatic.fusepanama.macfuse.fuse_h;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;

public class TimeSpec {

	private final MemorySegment segment;

	TimeSpec(MemorySegment segment) {
		this.segment = segment;
	}

	public void set(Instant newValue) {
		fuse_h.timespec.tv_sec$set(segment, newValue.getEpochSecond());
		fuse_h.timespec.tv_nsec$set(segment, newValue.getNano());
	}

	public Instant get() {
		var seconds = fuse_h.timespec.tv_sec$get(segment);
		var nanos = fuse_h.timespec.tv_nsec$get(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
