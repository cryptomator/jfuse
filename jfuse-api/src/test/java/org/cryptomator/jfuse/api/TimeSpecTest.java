package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;

public class TimeSpecTest {

	@Test
	public void testGetOptionalOmit() {
		var timeSpec = Mockito.mock(TimeSpec.class);
		Mockito.doReturn(true).when(timeSpec).isUtimeOmit();
		Mockito.doCallRealMethod().when(timeSpec).getOptional();

		var result = timeSpec.getOptional();

		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void testGetOptionalNow() {
		var timeSpec = Mockito.mock(TimeSpec.class);
		Mockito.doReturn(true).when(timeSpec).isUtimeNow();
		Mockito.doCallRealMethod().when(timeSpec).getOptional();

		var result = timeSpec.getOptional();

		Assertions.assertFalse(result.isEmpty());
		var delta = Duration.between(result.get(), Instant.now()).abs();
		var maxDelta = Duration.ofSeconds(1);
		Assertions.assertTrue(delta.compareTo(maxDelta) < 0);
	}

	@Test
	public void testGetOptional() {
		var expected = Instant.ofEpochSecond(123L, 456L);
		var timeSpec = Mockito.mock(TimeSpec.class);
		Mockito.doReturn(expected).when(timeSpec).get();
		Mockito.doCallRealMethod().when(timeSpec).getOptional();

		var result = timeSpec.getOptional();
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expected, result.get());
	}

}