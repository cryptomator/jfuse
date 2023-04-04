package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_conn_info;
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

public class FuseConnInfoImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment setter, GetInConnInfo getter) {
		try (var arena = Arena.openConfined()) {
			var segment = fuse_conn_info.allocate(arena);
			var connInfo = new FuseConnInfoImpl(segment, arena.scope());

			setter.accept(segment, 42);

			Assertions.assertEquals(42, getter.apply(connInfo));
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::proto_major$set, Named.of("protoMajor()", (GetInConnInfo) FuseConnInfo::protoMajor)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::proto_minor$set, Named.of("protoMinor()", (GetInConnInfo) FuseConnInfo::protoMinor)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::capable$set, Named.of("capable()", (GetInConnInfo) FuseConnInfo::capable)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_write$set, Named.of("maxWrite()", (GetInConnInfo) FuseConnInfo::maxWrite)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_read$set, Named.of("maxRead()", (GetInConnInfo) FuseConnInfo::maxRead)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_readahead$set, Named.of("maxReadahead()", (GetInConnInfo) FuseConnInfo::maxReadahead)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::max_background$set, Named.of("maxBackground()", (GetInConnInfo) FuseConnInfo::maxBackground)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::congestion_threshold$set, Named.of("congestionThreshold()", (GetInConnInfo) FuseConnInfo::congestionThreshold)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::time_gran$set, Named.of("timeGran()", (GetInConnInfo) FuseConnInfo::timeGran)),
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::want$set, Named.of("want()", (GetInConnInfo) FuseConnInfo::want))
		);
	}

	private interface SetInMemorySegment extends BiConsumer<MemorySegment, Integer> {}

	private interface GetInConnInfo extends Function<FuseConnInfo, Integer> {}

	@DisplayName("test setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testSetters(SetInConnInfo setter, GetInMemorySegment getter) {
		try (var arena = Arena.openConfined()) {
			var segment = fuse_conn_info.allocate(arena);
			var connInfo = new FuseConnInfoImpl(segment, arena.scope());

			setter.accept(connInfo, 42);

			Assertions.assertEquals(42, getter.apply(segment));
		}
	}

	public static Stream<Arguments> testSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setWant()", (SetInConnInfo) FuseConnInfo::setWant), (GetInMemorySegment) fuse_conn_info::want$get),
				Arguments.arguments(Named.of("setMaxWrite()", (SetInConnInfo) FuseConnInfo::setMaxWrite), (GetInMemorySegment) fuse_conn_info::max_write$get),
				Arguments.arguments(Named.of("setMaxRead()", (SetInConnInfo) FuseConnInfo::setMaxRead), (GetInMemorySegment) fuse_conn_info::max_read$get),
				Arguments.arguments(Named.of("setMaxReadahead()", (SetInConnInfo) FuseConnInfo::setMaxReadahead), (GetInMemorySegment) fuse_conn_info::max_readahead$get),
				Arguments.arguments(Named.of("setMaxBackground()", (SetInConnInfo) FuseConnInfo::setMaxBackground), (GetInMemorySegment) fuse_conn_info::max_background$get),
				Arguments.arguments(Named.of("setCongestionThreshold()", (SetInConnInfo) FuseConnInfo::setCongestionThreshold), (GetInMemorySegment) fuse_conn_info::congestion_threshold$get),
				Arguments.arguments(Named.of("setTimeGran()", (SetInConnInfo) FuseConnInfo::setTimeGran), (GetInMemorySegment) fuse_conn_info::time_gran$get)
		);
	}

	private interface SetInConnInfo extends BiConsumer<FuseConnInfo, Integer> {}

	private interface GetInMemorySegment extends Function<MemorySegment, Integer> {}

}