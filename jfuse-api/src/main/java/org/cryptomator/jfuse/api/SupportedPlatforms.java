package org.cryptomator.jfuse.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container for multiple {@link SupportedPlatform} annotations.
 * <p>
 * Do not use directly. Use {@link SupportedPlatform} instead.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SupportedPlatforms {

	/**
	 * Contained {@link SupportedPlatform} annotations.
	 *
	 * @return supported platforms
	 */
	SupportedPlatform[] value();
}
