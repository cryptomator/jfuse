package de.skymatic.fusepanama;

import java.time.Instant;

public interface TimeSpec {

	void set(Instant newValue);

	Instant get();

}
