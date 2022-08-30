package org.cryptomator.jfuse.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(SupportedPlatforms.class)
public @interface SupportedPlatform {
	OperatingSystem os() default OperatingSystem.UNKNOWN;
	Architecture arch() default Architecture.UNKNOWN;
}
