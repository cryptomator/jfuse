package org.cryptomator.jfuse.api.platforms;

import org.cryptomator.jfuse.api.FuseBuilder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link FuseBuilder} implementation to support a given operating system and architecture.
 * <p>
 * This annotation is {@link Repeatable repeatable}, in case of a implementation supporting multiple operating
 * systems or architectures.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(SupportedPlatforms.class)
public @interface SupportedPlatform {
	/**
	 * The supported operating system.
	 *
	 * @return operationg system
	 */
	OperatingSystem os() default OperatingSystem.UNKNOWN;

	/**
	 * The supported architecture.
	 *
	 * @return architecture
	 */
	Architecture arch() default Architecture.UNKNOWN;
}
