package org.cryptomator.jfuse.api.util;

import org.jetbrains.annotations.Nullable;

import java.lang.foreign.MemorySegment;

public class MemoryUtils {

	@Nullable
	public static String toUtf8StringOrNull(MemorySegment string, long offset) {
		return MemorySegment.NULL.equals(string) ? null : string.getUtf8String(offset);
	}

	@Nullable
	public static String toUtf8StringOrNull(MemorySegment string) {
		return toUtf8StringOrNull(string, 0);
	}
}
