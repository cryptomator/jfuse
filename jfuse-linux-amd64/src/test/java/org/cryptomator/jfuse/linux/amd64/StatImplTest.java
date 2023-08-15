package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.stat;
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

public class StatImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment<Number> setter, GetInStat<Number> getter, Number value) {
		try (var arena = Arena.ofConfined()) {
			var segment = stat.allocate(arena);
			var stat = new StatImpl(segment, arena);

			setter.accept(segment, value);

			Assertions.assertEquals(value.longValue(), getter.apply(stat).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Integer>) stat::st_mode$set, Named.of("getMode()", (GetInStat<Integer>) Stat::getMode), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) stat::st_uid$set, Named.of("getUid()", (GetInStat<Integer>) Stat::getUid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) stat::st_gid$set, Named.of("getGid()", (GetInStat<Integer>) Stat::getGid), 42),
				Arguments.arguments((SetInMemorySegment<Long>) stat::st_nlink$set, Named.of("getNLink()", (GetInStat<Long>) Stat::getNLink), 42L),
				Arguments.arguments((SetInMemorySegment<Long>) stat::st_size$set, Named.of("getSize()", (GetInStat<Long>) Stat::getSize), 42L)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {}

	private interface GetInStat<T> extends Function<Stat, T> {}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInStat<Number> setter, GetInMemorySegment<Number> getter, Number value) {
		try (var arena = Arena.ofConfined()) {
			var segment = stat.allocate(arena);
			var stat = new StatImpl(segment, arena);

			setter.accept(stat, value);

			Assertions.assertEquals(value.longValue(), getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setMode()", (SetInStat<Integer>) Stat::setMode), (GetInMemorySegment<Integer>) stat::st_mode$get, 42),
				Arguments.arguments(Named.of("setUid()", (SetInStat<Integer>) Stat::setUid), (GetInMemorySegment<Integer>) stat::st_uid$get, 42),
				Arguments.arguments(Named.of("setGid()", (SetInStat<Integer>) Stat::setGid), (GetInMemorySegment<Integer>) stat::st_gid$get, 42),
				Arguments.arguments(Named.of("setNLink()", (SetInStat<Short>) Stat::setNLink), (GetInMemorySegment<Long>) stat::st_nlink$get, (short) 42),
				Arguments.arguments(Named.of("setSize()", (SetInStat<Long>) Stat::setSize), (GetInMemorySegment<Long>) stat::st_size$get, 42L)
		);
	}

	private interface SetInStat<T> extends BiConsumer<Stat, T> {}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {}

}