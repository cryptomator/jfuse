package org.cryptomator.jfuse.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class MemoryUtilsTest {


	@Test
	@DisplayName("On MemorySegment != NULL pointer, method returns utf8 string in the memory region")
	void testValidSegmentReturnsString() {
		try (var arena = Arena.ofConfined()) {
			var address = arena.allocate(4);
			address.setUtf8String(0, "abc");
			String result = MemoryUtils.toUtf8StringOrNull(address);
			Assertions.assertEquals("abc", result);
		}
	}

	@Test
	@DisplayName("With offset, on MemorySegment != NULL pointer, method returns utf8 string in the memory region")
	void testValidSegmentReturnsStringAtOffset() {
		try (var arena = Arena.ofConfined()) {
			var address = arena.allocate(10);
			address.setUtf8String(5, "abc");
			String result = MemoryUtils.toUtf8StringOrNull(address, 5);
			Assertions.assertEquals("abc", result);
		}
	}

	@Test
	@DisplayName("When MemorySegment == NULL pointer, method returns null")
	void testNullPointerSegmentReturnsNull() {
		String result = MemoryUtils.toUtf8StringOrNull(MemorySegment.NULL, 0);
		Assertions.assertNull(result);
	}
}
