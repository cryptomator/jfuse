package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_conn_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class FuseConnInfoImplTest {

	@Nested
	public class IntFieldTests {

		@DisplayName("test getters")
		@ParameterizedTest(name = "{1}")
		@MethodSource
		void testGetters(SetInMemorySegment setter, GetInConnInfo getter) {
			try (var arena = Arena.ofConfined()) {
				var segment = fuse_conn_info.allocate(arena);
				var connInfo = new FuseConnInfoImpl(segment);

				setter.accept(segment, 42);

				Assertions.assertEquals(42, getter.apply(connInfo));
			}
		}

		public static Stream<Arguments> testGetters() {
			return Stream.of(
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::proto_major, Named.of("protoMajor()", (GetInConnInfo) FuseConnInfo::protoMajor)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::proto_minor, Named.of("protoMinor()", (GetInConnInfo) FuseConnInfo::protoMinor)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::capable, Named.of("capable()", (GetInConnInfo) FuseConnInfo::capable)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_write, Named.of("maxWrite()", (GetInConnInfo) FuseConnInfo::maxWrite)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_read, Named.of("maxRead()", (GetInConnInfo) FuseConnInfo::maxRead)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_readahead, Named.of("maxReadahead()", (GetInConnInfo) FuseConnInfo::maxReadahead)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_background, Named.of("maxBackground()", (GetInConnInfo) FuseConnInfo::maxBackground)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::congestion_threshold, Named.of("congestionThreshold()", (GetInConnInfo) FuseConnInfo::congestionThreshold)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::time_gran, Named.of("timeGran()", (GetInConnInfo) FuseConnInfo::timeGran)),
					Arguments.arguments((SetInMemorySegment) fuse_conn_info::want, Named.of("want()", (GetInConnInfo) FuseConnInfo::want))
			);
		}

		interface SetInMemorySegment extends GenericSetInMemorySegment<Integer> {
		}

		interface GetInConnInfo extends GenericGetInConnInfo<Integer> {
		}

		@DisplayName("test setters")
		@ParameterizedTest(name = "{0}")
		@MethodSource
		void testSetters(SetInConnInfo setter, GetInMemorySegment getter) {
			try (var arena = Arena.ofConfined()) {
				var segment = fuse_conn_info.allocate(arena);
				var connInfo = new FuseConnInfoImpl(segment);

				setter.accept(connInfo, 42);

				Assertions.assertEquals(42, getter.apply(segment));
			}
		}

		public static Stream<Arguments> testSetters() {
			return Stream.of(
					Arguments.arguments(Named.of("setWant()", (SetInConnInfo) FuseConnInfo::setWant), (GetInMemorySegment) fuse_conn_info::want),
					Arguments.arguments(Named.of("setMaxWrite()", (SetInConnInfo) FuseConnInfo::setMaxWrite), (GetInMemorySegment) fuse_conn_info::max_write),
					Arguments.arguments(Named.of("setMaxRead()", (SetInConnInfo) FuseConnInfo::setMaxRead), (GetInMemorySegment) fuse_conn_info::max_read),
					Arguments.arguments(Named.of("setMaxReadahead()", (SetInConnInfo) FuseConnInfo::setMaxReadahead), (GetInMemorySegment) fuse_conn_info::max_readahead),
					Arguments.arguments(Named.of("setMaxBackground()", (SetInConnInfo) FuseConnInfo::setMaxBackground), (GetInMemorySegment) fuse_conn_info::max_background),
					Arguments.arguments(Named.of("setCongestionThreshold()", (SetInConnInfo) FuseConnInfo::setCongestionThreshold), (GetInMemorySegment) fuse_conn_info::congestion_threshold),
					Arguments.arguments(Named.of("setTimeGran()", (SetInConnInfo) FuseConnInfo::setTimeGran), (GetInMemorySegment) fuse_conn_info::time_gran)
			);
		}

		interface SetInConnInfo extends GenericSetInConnInfo<Integer> {
		}

		interface GetInMemorySegment extends GenericGetInMemorySegment<Integer> {
		}
	}

	@Nested
	public class LongFieldTest {

		@DisplayName("test long getters")
		@ParameterizedTest(name = "{1}")
		@MethodSource
		void testGetters(SetInMemorySegment setter, GetInConnInfo getter) {
			try (var arena = Arena.ofConfined()) {
				var segment = fuse_conn_info.allocate(arena);
				var connInfo = new FuseConnInfoImpl(segment);

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

		interface SetInMemorySegment extends GenericSetInMemorySegment<Long> {
		}

		interface GetInConnInfo extends GenericGetInConnInfo<Long> {
		}

		@DisplayName("test setters")
		@ParameterizedTest(name = "{0}")
		@MethodSource
		void testSetters(SetInConnInfo setter, GetInMemorySegment getter) {
			try (var arena = Arena.ofConfined()) {
				var segment = fuse_conn_info.allocate(arena);
				var connInfo = new FuseConnInfoImpl(segment);

				setter.accept(connInfo, 42L);

				Assertions.assertEquals(42L, getter.apply(segment));
			}
		}

		public static Stream<Arguments> testSetters() {
			return Stream.of(
					Arguments.arguments(Named.of("setWantExt()", (SetInConnInfo) FuseConnInfo::setWantExt), (GetInMemorySegment) fuse_conn_info::want_ext)
			);
		}

		interface SetInConnInfo extends GenericSetInConnInfo<Long> {
		}

		interface GetInMemorySegment extends GenericGetInMemorySegment<Long> {
		}
	}

	private interface GenericSetInMemorySegment<E extends Number> extends BiConsumer<MemorySegment, E> {
	}

	private interface GenericGetInConnInfo<E extends Number> extends Function<FuseConnInfo, E> {
	}

	private interface GenericSetInConnInfo<E extends Number> extends BiConsumer<FuseConnInfo, E> {
	}

	private interface GenericGetInMemorySegment<E extends Number> extends Function<MemorySegment, E> {
	}
}