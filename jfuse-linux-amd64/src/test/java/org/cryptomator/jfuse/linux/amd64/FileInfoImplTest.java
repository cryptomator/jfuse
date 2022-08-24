package org.cryptomator.jfuse.linux.amd64;

import jdk.incubator.foreign.ResourceScope;
import org.cryptomator.jfuse.linux.amd64.extr.fcntl_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_file_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.stream.Stream;

public class FileInfoImplTest {

	@ParameterizedTest
	@MethodSource("testGetOpenFlagParams")
	@DisplayName("test getOpenFlags()")
	public void testGetOpenFlags(int flags, Set<OpenOption> expectedResult) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var fi = new FileInfoImpl(fuse_file_info.allocate(scope));
			fuse_file_info.flags$set(fi.segment(), flags);

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
				Arguments.of(fcntl_h.O_RDWR() | fcntl_h.O_SYNC(), Set.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.SYNC)),
				Arguments.of(fcntl_h.O_RDWR() | fcntl_h.O_DSYNC(), Set.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC)),
				Arguments.of(fcntl_h.O_WRONLY() | fcntl_h.O_CREAT() | fcntl_h.O_TRUNC(), Set.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))
		);
	}

}