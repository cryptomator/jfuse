package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.TimeSpec;
import de.skymatic.fusepanama.win.amd64.lowlevel.fuse_timespec;
import jdk.incubator.foreign.MemorySegment;

import java.time.Instant;
import java.util.Optional;

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
		fuse_timespec.tv_sec$set(segment, newValue.getEpochSecond());
		fuse_timespec.tv_nsec$set(segment, newValue.getNano());
	}

	@Override
	public Instant get() {
		var seconds = fuse_timespec.tv_sec$get(segment);
		var nanos = fuse_timespec.tv_nsec$get(segment);
		return Instant.ofEpochSecond(seconds, nanos);
	}

}
