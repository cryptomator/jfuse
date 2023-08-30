package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.fuse.statvfs;
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

public class StatvfsImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment<Number> setter, GetInStatvfs<Number> getter, Number value, long expected) {
		try (var arena = Arena.ofConfined()) {
			var segment = statvfs.allocate(arena);
			var statvfs = new StatvfsImpl(segment, arena);

			setter.accept(segment, value);

			Assertions.assertEquals(expected, getter.apply(statvfs).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_bsize$set, Named.of("getBsize()", (GetInStatvfs<Long>) Statvfs::getBsize), 42L, 42L),
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_frsize$set, Named.of("getFrsize()", (GetInStatvfs<Long>) Statvfs::getFrsize), 42L, 42L),
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_namemax$set, Named.of("getNameMax()", (GetInStatvfs<Long>) Statvfs::getNameMax), 42L, 42L),

				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_blocks$set, Named.of("getBlocks() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBlocks), 42, 42L),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bfree$set, Named.of("getBfree() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBfree), 42, 42L),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bavail$set, Named.of("getBavail() with memory containing value < INT32", (GetInStatvfs<Long>) Statvfs::getBavail), 42, 42L),

				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_blocks$set, Named.of("getBlocks() with memory containing value < UINT32", (GetInStatvfs<Long>) Statvfs::getBlocks), 0xFFFFFFD6, 0x00000000_FFFFFFD6L),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bfree$set, Named.of("getBfree() with memory containing value < UINT32", (GetInStatvfs<Long>) Statvfs::getBfree), 0xFFFFFFD6, 0x00000000_FFFFFFD6L),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bavail$set, Named.of("getBavail() with memory containing value < UINT32", (GetInStatvfs<Long>) Statvfs::getBavail), 0xFFFFFFD6, 0x00000000_FFFFFFD6L)
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
			var statvfs = new StatvfsImpl(segment, arena);

			setter.accept(statvfs, value.longValue());

			Assertions.assertEquals(expected, getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setBsize()", (SetInStatvfs<Long>) Statvfs::setBsize), (GetInMemorySegment<Long>) statvfs::f_bsize$get, 42L, 42L),
				Arguments.arguments(Named.of("setFrsize()", (SetInStatvfs<Long>) Statvfs::setFrsize), (GetInMemorySegment<Long>) statvfs::f_frsize$get, 42L, 42L),
				Arguments.arguments(Named.of("setNameMax()", (SetInStatvfs<Long>) Statvfs::setNameMax), (GetInMemorySegment<Long>) statvfs::f_namemax$get, 42L, 42L),

				Arguments.arguments(Named.of("setBlocks(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Integer>) statvfs::f_blocks$get, 42, 42),
				Arguments.arguments(Named.of("setBfree(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Integer>) statvfs::f_bfree$get, 42, 42),
				Arguments.arguments(Named.of("setBavail(i) with i < INT32", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Integer>) statvfs::f_bavail$get, 42, 42),

				Arguments.arguments(Named.of("setBlocks(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Integer>) statvfs::f_blocks$get, 0xFFFFFFD6, 0xFFFFFFD6),
				Arguments.arguments(Named.of("setBfree(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Integer>) statvfs::f_bfree$get, 0xFFFFFFD6, 0xFFFFFFD6),
				Arguments.arguments(Named.of("setBavail(i) with i > INT32", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Integer>) statvfs::f_bavail$get, 0xFFFFFFD6, 0xFFFFFFD6),

				Arguments.arguments(Named.of("setBlocks(i) with i > UINT32", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Integer>) statvfs::f_blocks$get, 0x01234567_89ABCDEFL, 0xFFFFFFFF),
				Arguments.arguments(Named.of("setBfree(i) with i > UINT32", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Integer>) statvfs::f_bfree$get, 0x01234567_89ABCDEFL, 0xFFFFFFFF),
				Arguments.arguments(Named.of("setBavail(i) with i > UINT32", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Integer>) statvfs::f_bavail$get, 0x01234567_89ABCDEFL, 0xFFFFFFFF)
		);
	}


	private interface SetInStatvfs<T> extends BiConsumer<Statvfs, T> {
	}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {
	}
}
