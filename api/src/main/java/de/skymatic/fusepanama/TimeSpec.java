package de.skymatic.fusepanama;

import java.time.Instant;
import java.util.Optional;

public interface TimeSpec {

	void set(Instant newValue);

	/**
	 * @return Instant represented by this TimeSpec.
	 * Might be {@link Instant#now() now} in case of <code>UTIME_NOW</code>
	 * or absent in case of <code>UTIME_OMIT</code>
	 */
	Optional<Instant> get();

}
