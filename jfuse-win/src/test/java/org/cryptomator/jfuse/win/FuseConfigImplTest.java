package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_config;
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

public class FuseConfigImplTest {

	@DisplayName("test getters")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testGetters(SetInMemorySegment<Number> setter, GetInFuseConfig<Number> getter, Number value) {
		try (var arena = Arena.ofConfined()) {
			var segment = fuse3_config.allocate(arena);
			var fuseConfig = new FuseConfigImpl(segment, arena);

			setter.accept(segment, value);

			Assertions.assertEquals(value.doubleValue(), getter.apply(fuseConfig).doubleValue(), 0.01);
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::set_gid$set, Named.of("setGid()", (GetInFuseConfig<Integer>) FuseConfig::setGid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::gid$set, Named.of("gid()", (GetInFuseConfig<Integer>) FuseConfig::gid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::set_uid$set, Named.of("setUid()", (GetInFuseConfig<Integer>) FuseConfig::setUid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::uid$set, Named.of("uid()", (GetInFuseConfig<Integer>) FuseConfig::uid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::set_mode$set, Named.of("setMode()", (GetInFuseConfig<Integer>) FuseConfig::setMode), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::umask$set, Named.of("umask()", (GetInFuseConfig<Integer>) FuseConfig::umask), 42),
				Arguments.arguments((SetInMemorySegment<Double>) fuse3_config::entry_timeout$set, Named.of("entryTimeout()", (GetInFuseConfig<Double>) FuseConfig::entryTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Double>) fuse3_config::negative_timeout$set, Named.of("negativeTimeout()", (GetInFuseConfig<Double>) FuseConfig::negativeTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Double>) fuse3_config::attr_timeout$set, Named.of("attrTimeout()", (GetInFuseConfig<Double>) FuseConfig::attrTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::intr$set, Named.of("intr()", (GetInFuseConfig<Integer>) FuseConfig::intr), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::intr_signal$set, Named.of("intrSignal()", (GetInFuseConfig<Integer>) FuseConfig::intrSignal), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::remember$set, Named.of("remember()", (GetInFuseConfig<Integer>) FuseConfig::remember), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::hard_remove$set, Named.of("hardRemove()", (GetInFuseConfig<Integer>) FuseConfig::hardRemove), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::use_ino$set, Named.of("useIno()", (GetInFuseConfig<Integer>) FuseConfig::useIno), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::readdir_ino$set, Named.of("readdirIno()", (GetInFuseConfig<Integer>) FuseConfig::readdirIno), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::direct_io$set, Named.of("directIo()", (GetInFuseConfig<Integer>) FuseConfig::directIo), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::kernel_cache$set, Named.of("kernelCache()", (GetInFuseConfig<Integer>) FuseConfig::kernelCache), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::auto_cache$set, Named.of("autoCache()", (GetInFuseConfig<Integer>) FuseConfig::autoCache), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::ac_attr_timeout_set$set, Named.of("acAtrrTimeoutSet()", (GetInFuseConfig<Integer>) FuseConfig::acAttrTimeoutSet), 42),
				Arguments.arguments((SetInMemorySegment<Double>) fuse3_config::ac_attr_timeout$set, Named.of("acAttrTimeout()", (GetInFuseConfig<Double>) FuseConfig::acAttrTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse3_config::nullpath_ok$set, Named.of("nullpathOk()", (GetInFuseConfig<Integer>) FuseConfig::nullpathOk), 42)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {}

	private interface GetInFuseConfig<T> extends Function<FuseConfig, T> {}

}
