package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_stat;
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
			var segment = fuse_stat.allocate(arena);
			var stat = new StatImpl(segment);

			setter.accept(segment, value);

			Assertions.assertEquals(value.longValue(), getter.apply(stat).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_stat::st_mode, Named.of("getMode()", (GetInStat<Integer>) Stat::getMode), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_stat::st_uid, Named.of("getUid()", (GetInStat<Integer>) Stat::getUid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_stat::st_gid, Named.of("getGid()", (GetInStat<Integer>) Stat::getGid), 42),
				Arguments.arguments((SetInMemorySegment<Short>) fuse_stat::st_nlink, Named.of("getNLink()", (GetInStat<Long>) Stat::getNLink), (short) 42),
				Arguments.arguments((SetInMemorySegment<Long>) fuse_stat::st_size, Named.of("getSize()", (GetInStat<Long>) Stat::getSize), 42L)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {}

	private interface GetInStat<T> extends Function<Stat, T> {}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInStat<Number> setter, GetInMemorySegment<Number> getter, Number value) {
		try (var arena = Arena.ofConfined()) {
			var segment = fuse_stat.allocate(arena);
			var stat = new StatImpl(segment);

			setter.accept(stat, value);

			Assertions.assertEquals(value.longValue(), getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setMode()", (SetInStat<Integer>) Stat::setMode), (GetInMemorySegment<Integer>) fuse_stat::st_mode, 42),
				Arguments.arguments(Named.of("setUid()", (SetInStat<Integer>) Stat::setUid), (GetInMemorySegment<Integer>) fuse_stat::st_uid, 42),
				Arguments.arguments(Named.of("setGid()", (SetInStat<Integer>) Stat::setGid), (GetInMemorySegment<Integer>) fuse_stat::st_gid, 42),
				Arguments.arguments(Named.of("setNLink()", (SetInStat<Short>) Stat::setNLink), (GetInMemorySegment<Short>) fuse_stat::st_nlink, (short) 42),
				Arguments.arguments(Named.of("setSize()", (SetInStat<Long>) Stat::setSize), (GetInMemorySegment<Long>) fuse_stat::st_size, 42L)
		);
	}

	private interface SetInStat<T> extends BiConsumer<Stat, T> {}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {}

}