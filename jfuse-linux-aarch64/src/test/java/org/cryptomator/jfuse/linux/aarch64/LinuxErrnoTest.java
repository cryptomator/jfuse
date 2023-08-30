package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Errno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class LinuxErrnoTest {

	@DisplayName("make sure method is not a stub")
	@ParameterizedTest(name = "{0}()")
	@MethodSource("errnoNameProvider")
	public void testErrnoIsNotZero(String methodName) throws ReflectiveOperationException {
		var errno = new LinuxErrno();
		var method = LinuxErrno.class.getMethod(methodName);

		int result = (int) method.invoke(errno);

		Assertions.assertNotEquals(0, result);
	}

	static Stream<String> errnoNameProvider() {
		return Arrays.stream(Errno.class.getDeclaredMethods()).map(Method::getName);
	}

}