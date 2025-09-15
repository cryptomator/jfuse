package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_conn_info;
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

public class FuseConnInfoImpl317Test {

	@DisplayName("test long getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	void testGetters(SetInMemorySegment setter, GetInConnInfo getter) {
		try (var arena = Arena.ofConfined()) {
			var segment = fuse_conn_info.allocate(arena);
			var connInfo = new FuseConnInfoImpl317(segment);

			setter.accept(segment, 42L);

			Assertions.assertEquals(42L, getter.apply(connInfo));
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::want_ext, Named.of("wantExt()", (GetInConnInfo) FuseConnInfo::wantExt)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::capable_ext, Named.of("capable()", (GetInConnInfo) FuseConnInfo::capableExt))
		);
	}

	interface SetInMemorySegment extends BiConsumer<MemorySegment, Long> {
	}

	interface GetInConnInfo extends Function<FuseConnInfo, Long> {
	}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	void testSetters(SetInConnInfo setter, GetInMemorySegment getter) {
		try (var arena = Arena.ofConfined()) {
			var segment = fuse_conn_info.allocate(arena);
			var connInfo = new FuseConnInfoImpl317(segment);

			setter.accept(connInfo, 42L);

			Assertions.assertEquals(42L, getter.apply(segment));
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setWantExt()", (SetInConnInfo) FuseConnInfo::setWantExt), (GetInMemorySegment) fuse_conn_info::want_ext)
		);
	}

	interface SetInConnInfo extends BiConsumer<FuseConnInfo, Long> {
	}

	interface GetInMemorySegment extends Function<MemorySegment, Long> {
	}
}
