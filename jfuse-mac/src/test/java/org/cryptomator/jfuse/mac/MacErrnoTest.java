package org.cryptomator.jfuse.mac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MacErrnoTest {

	@DisplayName("make sure method is not a stub")
	@ParameterizedTest(name = "{0}()")
	@ValueSource(strings = {"enoent", "enosys", "enomem", "eacces", "eio", "erofs", "ebadf", "eexist", "enotdir", "eisdir", "enotempty", "einval"})
	public void testErrnoIsNotZero(String methodName) throws ReflectiveOperationException {
		var errno = new MacErrno();
		var method = MacErrno.class.getMethod(methodName);

		int result = (int) method.invoke(errno);

		Assertions.assertNotEquals(0, result);
	}

}