package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.win.FileInfoImpl;
import org.cryptomator.jfuse.win.extr.fcntl_h;
import org.cryptomator.jfuse.win.extr.fuse3_file_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.foreign.MemorySession;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.stream.Stream;

public class FileInfoImplTest {

	@ParameterizedTest
	@MethodSource("testGetOpenFlagParams")
	@DisplayName("test getOpenFlags()")
	public void testGetOpenFlags(int flags, Set<OpenOption> expectedResult) {
		try (var scope = MemorySession.openConfined()) {
			var fi = new FileInfoImpl(fuse3_file_info.allocate(scope));
			fuse3_file_info.flags$set(fi.segment(), flags);

			var result = fi.getOpenFlags();

			Assertions.assertEquals(expectedResult, result);
		}
	}

	public static Stream<Arguments> testGetOpenFlagParams() {
		return Stream.of(
				Arguments.of(fcntl_h.O_RDONLY(), Set.of(StandardOpenOption.READ)),
				Arguments.of(fcntl_h.O_WRONLY(), Set.of(StandardOpenOption.WRITE)),
				Arguments.of(fcntl_h.O_RDWR(), Set.of(StandardOpenOption.READ, StandardOpenOption.WRITE)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_APPEND(), Set.of(StandardOpenOption.WRITE, StandardOpenOption.APPEND)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_CREAT(), Set.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_CREAT() | fcntl_h.O_EXCL(), Set.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_TRUNC(), Set.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_CREAT() | fcntl_h.O_TRUNC(), Set.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))
		);
	}

}