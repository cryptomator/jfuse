package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.stat.stat_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.timespec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.time.Instant;

public class TimeSpecImplTest {

	@Test
	@DisplayName("test get()")
	public void testGet() {
		try (var arena = Arena.ofConfined()) {
			var timeSpec = new TimeSpecImpl(timespec.allocate(arena));
			timespec.tv_sec$set(timeSpec.segment(), 123L);
			timespec.tv_nsec$set(timeSpec.segment(), 456L);

			var result = timeSpec.get();

			Assertions.assertEquals(123L, result.getEpochSecond());
			Assertions.assertEquals(456L, result.getNano());
		}
	}

	@Test
	@DisplayName("test set()")
	public void testSet() {
		try (var arena = Arena.ofConfined()) {
			var timeSpec = new TimeSpecImpl(timespec.allocate(arena));
			timespec.tv_sec$set(timeSpec.segment(), 0L);
			timespec.tv_nsec$set(timeSpec.segment(), 0L);

			timeSpec.set(Instant.ofEpochSecond(123L, 456L));

			Assertions.assertEquals(123L, timespec.tv_sec$get(timeSpec.segment()));
			Assertions.assertEquals(456L, timespec.tv_nsec$get(timeSpec.segment()));
		}
	}

	@Test
	@DisplayName("test isUtimeOmit()")
	public void testIsUtimeOmit() {
		try (var arena = Arena.ofConfined()) {
			var timeSpec = new TimeSpecImpl(timespec.allocate(arena));
			timespec.tv_sec$set(timeSpec.segment(), 123L);
			timespec.tv_nsec$set(timeSpec.segment(), stat_h.UTIME_OMIT());

			Assertions.assertTrue(timeSpec.isUtimeOmit());
			Assertions.assertFalse(timeSpec.isUtimeNow());
		}
	}

	@Test
	@DisplayName("test isUtimeNow()")
	public void testIsUtimeNow() {
		try (var arena = Arena.ofConfined()) {
			var timeSpec = new TimeSpecImpl(timespec.allocate(arena));
			timespec.tv_sec$set(timeSpec.segment(), 123L);
			timespec.tv_nsec$set(timeSpec.segment(), stat_h.UTIME_NOW());

			Assertions.assertFalse(timeSpec.isUtimeOmit());
			Assertions.assertTrue(timeSpec.isUtimeNow());
		}
	}

}