package de.skymatic.fusepanama;

import java.time.Instant;
import java.util.Optional;

public interface TimeSpec {

	/**
	 * Check if the nanoseconds value is set to the magic number <code>UTIME_OMIT</code>.
	 *
	 * @return <code>true</code> if this time represents the special value <code>UTIME_OMIT</code>
	 */
	boolean isUtimeOmit();

	/**
	 * Check if the nanoseconds value is set to the magic number <code>UTIME_NOW</code>.
	 *
	 * @return <code>true</code> if this time represents the special value <code>UTIME_NOW</code>
	 */
	boolean isUtimeNow();

	void set(Instant newValue);

	Instant get();

	/**
	 * @return Instant represented by this TimeSpec.
	 * Might be {@link Instant#now() now} in case of {@link #isUtimeNow() <code>UTIME_NOW</code>}
	 * or absent in case of {@link #isUtimeOmit() <code>UTIME_OMIT</code>}
	 */
	default Optional<Instant> getOptional() {
		if (isUtimeNow()) {
			return Optional.empty();
		} else if (isUtimeNow()) {
			return Optional.of(Instant.now());
		} else {
			return Optional.of(get());
		}
	}

}
