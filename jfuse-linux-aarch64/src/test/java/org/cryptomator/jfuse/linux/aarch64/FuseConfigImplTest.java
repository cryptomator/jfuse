package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_config;
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
			var segment = fuse_config.allocate(arena);
			var fuseConfig = new FuseConfigImpl(segment);

			setter.accept(segment, value);

			Assertions.assertEquals(value.doubleValue(), getter.apply(fuseConfig).doubleValue(), 0.01);
		}
	}

	public static Stream<Arguments> testGetters() {
		return Stream.of(
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::set_gid, Named.of("setGid()", (GetInFuseConfig<Integer>) FuseConfig::setGid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::gid, Named.of("gid()", (GetInFuseConfig<Integer>) FuseConfig::gid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::set_uid, Named.of("setUid()", (GetInFuseConfig<Integer>) FuseConfig::setUid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::uid, Named.of("uid()", (GetInFuseConfig<Integer>) FuseConfig::uid), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::set_mode, Named.of("setMode()", (GetInFuseConfig<Integer>) FuseConfig::setMode), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::umask, Named.of("umask()", (GetInFuseConfig<Integer>) FuseConfig::umask), 42),
				Arguments.arguments((SetInMemorySegment<Double>) fuse_config::entry_timeout, Named.of("entryTimeout()", (GetInFuseConfig<Double>) FuseConfig::entryTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Double>) fuse_config::negative_timeout, Named.of("negativeTimeout()", (GetInFuseConfig<Double>) FuseConfig::negativeTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Double>) fuse_config::attr_timeout, Named.of("attrTimeout()", (GetInFuseConfig<Double>) FuseConfig::attrTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::intr, Named.of("intr()", (GetInFuseConfig<Integer>) FuseConfig::intr), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::intr_signal, Named.of("intrSignal()", (GetInFuseConfig<Integer>) FuseConfig::intrSignal), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::remember, Named.of("remember()", (GetInFuseConfig<Integer>) FuseConfig::remember), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::hard_remove, Named.of("hardRemove()", (GetInFuseConfig<Integer>) FuseConfig::hardRemove), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::use_ino, Named.of("useIno()", (GetInFuseConfig<Integer>) FuseConfig::useIno), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::readdir_ino, Named.of("readdirIno()", (GetInFuseConfig<Integer>) FuseConfig::readdirIno), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::direct_io, Named.of("directIo()", (GetInFuseConfig<Integer>) FuseConfig::directIo), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::kernel_cache, Named.of("kernelCache()", (GetInFuseConfig<Integer>) FuseConfig::kernelCache), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::auto_cache, Named.of("autoCache()", (GetInFuseConfig<Integer>) FuseConfig::autoCache), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::ac_attr_timeout_set, Named.of("acAtrrTimeoutSet()", (GetInFuseConfig<Integer>) FuseConfig::acAttrTimeoutSet), 42),
				Arguments.arguments((SetInMemorySegment<Double>) fuse_config::ac_attr_timeout, Named.of("acAttrTimeout()", (GetInFuseConfig<Double>) FuseConfig::acAttrTimeout), 4.2),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::nullpath_ok, Named.of("nullpathOk()", (GetInFuseConfig<Integer>) FuseConfig::nullpathOk), 42),
				Arguments.arguments((SetInMemorySegment<Integer>) fuse_config::no_rofd_flush, Named.of("noRofdFlush()", (GetInFuseConfig<Integer>) FuseConfig::noRofdFlush), 42)
		);
	}

	private interface SetInMemorySegment<T> extends BiConsumer<MemorySegment, T> {}

	private interface GetInFuseConfig<T> extends Function<FuseConfig, T> {}

}
