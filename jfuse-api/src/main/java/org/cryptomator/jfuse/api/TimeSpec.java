package org.cryptomator.jfuse.api;

import java.time.Instant;
import java.util.Optional;

/**
 * A wrapper storing time and makes it accessible via {@link #get()} and {@link #set(Instant)}.
 */
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

	/**
	 * Sets the time to the given value.
	 *
	 * @param newValue The time value
	 */
	void set(Instant newValue);

	/**
	 * Get the time represented by this object.
	 *
	 * @return The time value
	 */
	Instant get();

	/**
	 * Get the {@link Instant} represented by this TimeSpec.
	 * <p>
	 * Might be {@link Instant#now() now} in case of {@link #isUtimeNow() UTIME_NOW} or absent
	 * in case of {@link #isUtimeOmit() UTIME_OMIT}
	 *
	 * @return The time represented by this object, taking magic numbers into account.
	 */
	default Optional<Instant> getOptional() {
		if (isUtimeOmit()) {
			return Optional.empty();
		} else if (isUtimeNow()) {
			return Optional.of(Instant.now());
		} else {
			return Optional.of(get());
		}
	}

}
