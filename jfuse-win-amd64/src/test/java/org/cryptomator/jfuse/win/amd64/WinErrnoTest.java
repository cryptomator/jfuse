package org.cryptomator.jfuse.win.amd64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class WinErrnoTest {

	@DisplayName("make sure method is not a stub")
	@ParameterizedTest(name = "{0}()")
	@ValueSource(strings = {"enoent", "enosys", "enomem", "eacces", "eio", "erofs", "ebadf", "eexist", "enotdir", "eisdir", "enotempty", "einval"})
	public void testErrnoIsNotZero(String methodName) throws ReflectiveOperationException {
		var errno = new WinErrno();
		var method = WinErrno.class.getMethod(methodName);

		int result = (int) method.invoke(errno);

		Assertions.assertNotEquals(0, result);
	}

}