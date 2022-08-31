package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.linux.amd64.extr.timespec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.time.Instant;

public class FuseImplTest {

	private FuseOperations fuseOps = Mockito.mock(FuseOperations.class);
	private FuseImpl fuseImpl = new FuseImpl(fuseOps);

	@Nested
	@DisplayName("utimens")
	public class Utimens {

		@DisplayName("utimens(\"/foo\", UTIME_NOW, UTIME_NOW)")
		@Test
		public void testUtimensNow() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var times = MemoryAddress.NULL;
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(TimeSpec::isUtimeNow), Mockito.argThat(TimeSpec::isUtimeNow));

				var result = fuseImpl.utimens(path.address(), times);

				Assertions.assertEquals(42, result);
			}
		}

		@DisplayName("utimens(\"/foo\", ...)")
		@ParameterizedTest
		@CsvSource(value = {
				"123456,789, 456789,123",
				"111222,333, 444555,666",
		})
		public void testUtimens(long sec0, long nsec0, long sec1, long nsec1) {
			Instant expectedATime = Instant.ofEpochSecond(sec0, nsec0);
			Instant expectedMTime = Instant.ofEpochSecond(sec1, nsec1);
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var times = timespec.allocateArray(2, scope);
				timespec.tv_sec$set(times, 0, sec0);
				timespec.tv_nsec$set(times, 0, nsec0);
				timespec.tv_sec$set(times, 1, sec1);
				timespec.tv_nsec$set(times, 1, nsec1);
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(t -> expectedATime.equals(t.get())), Mockito.argThat(t -> expectedMTime.equals(t.get())));

				var result = fuseImpl.utimens(path.address(), times.address());

				Assertions.assertEquals(42, result);
			}
		}
	}

}