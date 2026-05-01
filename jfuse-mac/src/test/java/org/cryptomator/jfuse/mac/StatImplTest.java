package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_darwin_attr;
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
			var segment = fuse_darwin_attr.allocate(arena);
			var stat = new StatImpl(segment);

			setter.accept(segment, value);

			Assertions.assertEquals(value.longValue(), getter.apply(stat).longValue());
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Short>) fuse_darwin_attr::mode, Named.of("getMode()", (GetInStat<Integer>) Stat::getMode), (short) 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_darwin_attr::uid, Named.of("getUid()", (GetInStat<Integer>) Stat::getUid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_darwin_attr::gid, Named.of("getGid()", (GetInStat<Integer>) Stat::getGid), 42),
				Arguments.arguments((SetInMemorySegment<Short>) fuse_darwin_attr::nlink, Named.of("getNLink()", (GetInStat<Long>) Stat::getNLink), (short) 42),
				Arguments.arguments((SetInMemorySegment<Long>) fuse_darwin_attr::size, Named.of("getSize()", (GetInStat<Long>) Stat::getSize), 42L)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {}

	private interface GetInStat<T> extends Function<Stat, T> {}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInStat<Number> setter, GetInMemorySegment<Number> getter, Number value) {
		try (var arena = Arena.ofConfined()) {
			var segment = fuse_darwin_attr.allocate(arena);
			var stat = new StatImpl(segment);

			setter.accept(stat, value);

			Assertions.assertEquals(value.longValue(), getter.apply(segment).longValue());
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setMode()", (SetInStat<Integer>) Stat::setMode), (GetInMemorySegment<Short>) fuse_darwin_attr::mode, 42),
				Arguments.arguments(Named.of("setUid()", (SetInStat<Integer>) Stat::setUid), (GetInMemorySegment<Integer>) fuse_darwin_attr::uid, 42),
				Arguments.arguments(Named.of("setGid()", (SetInStat<Integer>) Stat::setGid), (GetInMemorySegment<Integer>) fuse_darwin_attr::gid, 42),
				Arguments.arguments(Named.of("setNLink()", (SetInStat<Short>) Stat::setNLink), (GetInMemorySegment<Short>) fuse_darwin_attr::nlink, (short) 42),
				Arguments.arguments(Named.of("setSize()", (SetInStat<Long>) Stat::setSize), (GetInMemorySegment<Long>) fuse_darwin_attr::size, 42L)
		);
	}

	private interface SetInStat<T> extends BiConsumer<Stat, T> {}

	private interface GetInMemorySegment<T> extends Function<MemorySegment, T> {}

}
