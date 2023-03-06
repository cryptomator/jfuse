package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Statvfs;
import org.cryptomator.jfuse.mac.extr.statvfs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class StatvfsImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment<Number> setter, GetInStatvfs<Number> getter, Number value) {
		try (var scope = MemorySession.openConfined()) {
			var segment = statvfs.allocate(scope);
			var statvfs = new StatvfsImpl(segment.address(), scope);

			setter.accept(segment, value);

			Assertions.assertEquals(value.longValue(), getter.apply(statvfs).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_bsize$set, Named.of("getBsize()", (GetInStatvfs<Long>) Statvfs::getBsize), 42L),
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_frsize$set, Named.of("getFrsize()", (GetInStatvfs<Long>) Statvfs::getFrsize), 42L),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_blocks$set, Named.of("getBlocks()", (GetInStatvfs<Long>) Statvfs::getBlocks), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bfree$set, Named.of("getBfree()", (GetInStatvfs<Long>) Statvfs::getBfree), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) statvfs::f_bavail$set, Named.of("getBavail()", (GetInStatvfs<Long>) Statvfs::getBavail), 42),
				Arguments.arguments((SetInMemorySegment<Long>) statvfs::f_namemax$set, Named.of("getNameMax()", (GetInStatvfs<Long>) Statvfs::getNameMax), 42)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {
	}

	private interface GetInStatvfs<T> extends Function<Statvfs, T> {
	}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInStatvfs<Number> setter, GetInMemorySegment<Number> getter, Number value) {
		try (var scope = MemorySession.openConfined()) {
			var segment = statvfs.allocate(scope);
			var statvfs = new StatvfsImpl(segment.address(), scope);

			setter.accept(statvfs, value);

			Assertions.assertEquals(value.longValue(), getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setBsize()", (SetInStatvfs<Long>) Statvfs::setBsize), (GetInMemorySegment<Long>) statvfs::f_bsize$get, 42L),
				Arguments.arguments(Named.of("setFrsize()", (SetInStatvfs<Long>) Statvfs::setFrsize), (GetInMemorySegment<Long>) statvfs::f_frsize$get, 42L),
				Arguments.arguments(Named.of("setBlocks()", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Integer>) statvfs::f_blocks$get, 42),
				Arguments.arguments(Named.of("setBfree()", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Integer>) statvfs::f_bfree$get, 42),
				Arguments.arguments(Named.of("setBavail()", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Integer>) statvfs::f_bavail$get, 42),
				Arguments.arguments(Named.of("setNameMax()", (SetInStatvfs<Long>) Statvfs::setNameMax), (GetInMemorySegment<Long>) statvfs::f_namemax$get, 42L)
		);
	}

	@DisplayName("test setters casting")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSettersCasting(SetInStatvfs<Number> setter, GetInMemorySegment<Number> getter, Number value) {
		try (var scope = MemorySession.openConfined()) {
			var segment = statvfs.allocate(scope);
			var statvfs = new StatvfsImpl(segment.address(), scope);

			setter.accept(statvfs, value);

			Assertions.assertEquals(value.byteValue(), getter.apply(segment).byteValue());
		}
	}

	public static Stream<Arguments> testSettersCasting() {
		return Stream.of(
				Arguments.arguments(Named.of("setBlocks()", (SetInStatvfs<Long>) Statvfs::setBlocks), (GetInMemorySegment<Integer>) statvfs::f_blocks$get, -42),
				Arguments.arguments(Named.of("setBfree()", (SetInStatvfs<Long>) Statvfs::setBfree), (GetInMemorySegment<Integer>) statvfs::f_bfree$get, -42),
				Arguments.arguments(Named.of("setBavail()", (SetInStatvfs<Long>) Statvfs::setBavail), (GetInMemorySegment<Integer>) statvfs::f_bavail$get, -42)
		);
	}

	@DisplayName("test setters out-of-range")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSettersOutOfRange(SetInStatvfs<Number> setter, Number value) {
		try (var scope = MemorySession.openConfined()) {
			var segment = statvfs.allocate(scope);
			var statvfs = new StatvfsImpl(segment.address(), scope);

			Assertions.assertThrows(IllegalArgumentException.class, () -> setter.accept(statvfs, value));
		}
	}

	public static Stream<Arguments> testSettersOutOfRange() {
		return Stream.of(
				Arguments.arguments(Named.of("setBlocks()", (SetInStatvfs<Long>) Statvfs::setBlocks), 0x100000000L),
				Arguments.arguments(Named.of("setBfree()", (SetInStatvfs<Long>) Statvfs::setBfree), 0x100000000L),
				Arguments.arguments(Named.of("setBavail()", (SetInStatvfs<Long>) Statvfs::setBavail), 0x100000000L)
		);
	}

	private interface SetInStatvfs<T> extends BiConsumer<Statvfs, T> {
	}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {
	}
}
