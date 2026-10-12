package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.fuse3.statfs;
import org.cryptomator.jfuse.mac.extr.fuse3.statvfs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class StatfsImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment<Number> setter, GetInStatvfs<Number> getter, Number value, long expected) {
		try (var arena = Arena.ofConfined()) {
			var segment = statfs.allocate(arena);
			var statvfs = new StatfsImpl(segment);

			setter.accept(segment, value);

			Assertions.assertEquals(expected, getter.apply(statvfs).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Integer>) statfs::f_bsize, Named.of("getBsize() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBsize), 42, 42L),

				Arguments.arguments((SetInMemorySegment<Integer>) statfs::f_blocks, Named.of("getBlocks() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBlocks), 42, 42L),
				Arguments.arguments((SetInMemorySegment<Integer>) statfs::f_bfree, Named.of("getBfree() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBfree), 42, 42L),
				Arguments.arguments((SetInMemorySegment<Integer>) statfs::f_bavail, Named.of("getBavail() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBavail), 42, 42L),

				Arguments.arguments((SetInMemorySegment<Long>) statfs::f_blocks, Named.of("getBlocks() with memory containing value > UINT32", (GetInStatvfs<Long>) Statvfs::getBlocks), 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL),
				Arguments.arguments((SetInMemorySegment<Long>) statfs::f_bfree, Named.of("getBfree() with memory containing value > UINT32", (GetInStatvfs<Long>) Statvfs::getBfree), 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL),
				Arguments.arguments((SetInMemorySegment<Long>) statfs::f_bavail, Named.of("getBavail() with memory containing value > UINT32", (GetInStatvfs<Long>) Statvfs::getBavail), 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {
	}

	private interface GetInStatvfs<T> extends Function<Statvfs, T> {
	}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInStatvfs<Number> setter, GetInMemorySegment<Number> getter, Number value, long expected) {
		try (var arena = Arena.ofConfined()) {
			var segment = statvfs.allocate(arena);
			var statvfs = new StatfsImpl(segment);

			setter.accept(statvfs, value.longValue());

			Assertions.assertEquals(expected, getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setBsize(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBsize), (GetInMemorySegment<Integer>) statfs::f_bsize, 42L, 42L),
				Arguments.arguments(Named.of("setBlocks(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Long>) statfs::f_blocks, 42, 42),
				Arguments.arguments(Named.of("setBfree(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Long>) statfs::f_bfree, 42, 42),
				Arguments.arguments(Named.of("setBavail(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Long>) statfs::f_bavail, 42, 42),

				Arguments.arguments(Named.of("setBsize(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBsize), (GetInMemorySegment<Integer>) statfs::f_bsize, 0x01234567_89ABCDEFL, 0xFFFFFFFF),
				Arguments.arguments(Named.of("setBlocks(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Long>) statfs::f_blocks, 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL),
				Arguments.arguments(Named.of("setBfree(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Long>) statfs::f_bfree, 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL),
				Arguments.arguments(Named.of("setBavail(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Long>) statfs::f_bavail, 0x01234567_89ABCDEFL, 0x01234567_89ABCDEFL)
		);
	}


	private interface SetInStatvfs<T> extends BiConsumer<Statvfs, T> {
	}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {
	}
}
