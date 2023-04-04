package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.win.extr.fuse_timespec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.time.Instant;

public class TimeSpecImplTest {

	@Test
	@DisplayName("test get()")
	public void testGet() {
		try (var arena = Arena.openConfined()) {
			var timeSpec = new TimeSpecImpl(fuse_timespec.allocate(arena));
			fuse_timespec.tv_sec$set(timeSpec.segment(), 123L);
			fuse_timespec.tv_nsec$set(timeSpec.segment(), 456L);

			var result = timeSpec.get();

			Assertions.assertEquals(123L, result.getEpochSecond());
			Assertions.assertEquals(456L, result.getNano());
		}
	}

	@Test
	@DisplayName("test set()")
	public void testSet() {
		try (var arena = Arena.openConfined()) {
			var timeSpec = new TimeSpecImpl(fuse_timespec.allocate(arena));
			fuse_timespec.tv_sec$set(timeSpec.segment(), 0L);
			fuse_timespec.tv_nsec$set(timeSpec.segment(), 0L);

			timeSpec.set(Instant.ofEpochSecond(123L, 456L));

			Assertions.assertEquals(123L, fuse_timespec.tv_sec$get(timeSpec.segment()));
			Assertions.assertEquals(456L, fuse_timespec.tv_nsec$get(timeSpec.segment()));
		}
	}

	@Test
	@DisplayName("test isUtimeOmit()")
	public void testIsUtimeOmit() {
		try (var arena = Arena.openConfined()) {
			var timeSpec = new TimeSpecImpl(fuse_timespec.allocate(arena));

			Assertions.assertFalse(timeSpec.isUtimeOmit());
		}
	}

	@Test
	@DisplayName("test isUtimeNow()")
	public void testIsUtimeNow() {
		try (var arena = Arena.openConfined()) {
			var timeSpec = new TimeSpecImpl(fuse_timespec.allocate(arena));

			Assertions.assertFalse(timeSpec.isUtimeNow());
		}
	}

}