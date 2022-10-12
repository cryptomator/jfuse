package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_config;
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

public class FuseConfigImplTest {

	@DisplayName("test getters returning int")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testIntGetters(SetIntInMemorySegment setter, GetIntInConfig getter) {
		try (var scope = MemorySession.openConfined()) {
			var segment = fuse_config.allocate(scope);
			var connInfo = new FuseConfigImpl(segment.address(), scope);

			setter.accept(segment, 42);

			Assertions.assertEquals(42, getter.apply(connInfo));
		}
	}

	public static Stream<Arguments> testIntGetters() {
		return Stream.of(
				Arguments.arguments((SetIntInMemorySegment) fuse_config::set_gid$set, Named.of("getSetGid()", (GetIntInConfig) FuseConfig::getSetGid)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::gid$set, Named.of("gid()", (GetIntInConfig) FuseConfig::gid)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::set_uid$set, Named.of("getSetUid()", (GetIntInConfig) FuseConfig::getSetUid)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::uid$set, Named.of("uid()", (GetIntInConfig) FuseConfig::uid)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::set_mode$set, Named.of("getSetMode()", (GetIntInConfig) FuseConfig::getSetMode)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::umask$set, Named.of("umask()", (GetIntInConfig) FuseConfig::umask)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::intr$set, Named.of("intr()", (GetIntInConfig) FuseConfig::intr)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::intr_signal$set, Named.of("intrSignal()", (GetIntInConfig) FuseConfig::intrSignal)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::remember$set, Named.of("remember()", (GetIntInConfig) FuseConfig::remember)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::hard_remove$set, Named.of("hardRemove()", (GetIntInConfig) FuseConfig::hardRemove)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::use_ino$set, Named.of("useIno()", (GetIntInConfig) FuseConfig::useIno)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::readdir_ino$set, Named.of("readdirIno()", (GetIntInConfig) FuseConfig::readdirIno)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::direct_io$set, Named.of("directIo()", (GetIntInConfig) FuseConfig::directIo)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::kernel_cache$set, Named.of("kernelCache()", (GetIntInConfig) FuseConfig::kernelCache)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::auto_cache$set, Named.of("autoCache()", (GetIntInConfig) FuseConfig::autoCache)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::ac_attr_timeout_set$set, Named.of("acAtrrTimeoutSet()", (GetIntInConfig) FuseConfig::acAttrTimeoutSet)),
				Arguments.arguments((SetIntInMemorySegment) fuse_config::nullpath_ok$set, Named.of("nullpathOk()", (GetIntInConfig) FuseConfig::nullpathOk))
		);
	}

	private interface SetIntInMemorySegment extends BiConsumer<MemorySegment, Integer> {
	}

	private interface GetIntInConfig extends Function<FuseConfig, Integer> {
	}

	@DisplayName("test getters returning double")
	@ParameterizedTest(name = "{1}")
	@MethodSource
	public void testDoubleGetters(SetDoubleInMemorySegment setter, GetDoubleInConfig getter) {
		try (var scope = MemorySession.openConfined()) {
			var segment = fuse_config.allocate(scope);
			var connInfo = new FuseConfigImpl(segment.address(), scope);

			setter.accept(segment, 4.2);

			Assertions.assertEquals(4.2, getter.apply(connInfo));
		}
	}

	public static Stream<Arguments> testDoubleGetters() {
		return Stream.of(
				Arguments.arguments((SetDoubleInMemorySegment) fuse_config::entry_timeout$set, Named.of("entryTimeout()", (GetDoubleInConfig) FuseConfig::entryTimeout)),
				Arguments.arguments((SetDoubleInMemorySegment) fuse_config::negative_timeout$set, Named.of("negativeTimeout()", (GetDoubleInConfig) FuseConfig::negativeTimeout)),
				Arguments.arguments((SetDoubleInMemorySegment) fuse_config::attr_timeout$set, Named.of("attrTimeout()", (GetDoubleInConfig) FuseConfig::attrTimeout)),
				Arguments.arguments((SetDoubleInMemorySegment) fuse_config::ac_attr_timeout$set, Named.of("acAttrTimeout()", (GetDoubleInConfig) FuseConfig::acAttrTimeout))
		);
	}

	private interface SetDoubleInMemorySegment extends BiConsumer<MemorySegment, Double> {
	}

	private interface GetDoubleInConfig extends Function<FuseConfig, Double> {
	}

	@DisplayName("test integer setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testIntSetters(SetIntInConfig setter, GetIntInMemorySegment getter) {
		try (var scope = MemorySession.openConfined()) {
			var segment = fuse_config.allocate(scope);
			var connInfo = new FuseConfigImpl(segment.address(), scope);

			setter.accept(connInfo, 42);

			Assertions.assertEquals(42, getter.apply(segment));
		}
	}

	public static Stream<Arguments> testIntSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("setSetGid()", (SetIntInConfig) FuseConfig::setSetGid), (GetIntInMemorySegment) fuse_config::set_gid$get),
				Arguments.arguments(Named.of("setGid()", (SetIntInConfig) FuseConfig::setGid), (GetIntInMemorySegment) fuse_config::gid$get),
				Arguments.arguments(Named.of("setSetUid()", (SetIntInConfig) FuseConfig::setSetUid), (GetIntInMemorySegment) fuse_config::set_uid$get),
				Arguments.arguments(Named.of("setUid()", (SetIntInConfig) FuseConfig::setUid), (GetIntInMemorySegment) fuse_config::uid$get),
				Arguments.arguments(Named.of("setSetMode()", (SetIntInConfig) FuseConfig::setSetMode), (GetIntInMemorySegment) fuse_config::set_mode$get),
				//Arguments.arguments(Named.of("setUmask()", (SetIntInConfig) FuseConfig::setUmask), (GetIntInMemorySegment) fuse_config::umask$get),
				Arguments.arguments(Named.of("setIntr()", (SetIntInConfig) FuseConfig::setIntr), (GetIntInMemorySegment) fuse_config::intr$get),
				Arguments.arguments(Named.of("setIntrSignal()", (SetIntInConfig) FuseConfig::setIntrSignal), (GetIntInMemorySegment) fuse_config::intr_signal$get),
				Arguments.arguments(Named.of("setRemember()", (SetIntInConfig) FuseConfig::setRemember), (GetIntInMemorySegment) fuse_config::remember$get),
				Arguments.arguments(Named.of("setHardRemove()", (SetIntInConfig) FuseConfig::setHardRemove), (GetIntInMemorySegment) fuse_config::hard_remove$get),
				Arguments.arguments(Named.of("setUseIno()", (SetIntInConfig) FuseConfig::setUseIno), (GetIntInMemorySegment) fuse_config::use_ino$get),
				Arguments.arguments(Named.of("setReaddirIno()", (SetIntInConfig) FuseConfig::setReaddirIno), (GetIntInMemorySegment) fuse_config::readdir_ino$get),
				Arguments.arguments(Named.of("setDirectIo()", (SetIntInConfig) FuseConfig::setDirectIo), (GetIntInMemorySegment) fuse_config::direct_io$get),
				Arguments.arguments(Named.of("setKernelCache()", (SetIntInConfig) FuseConfig::setKernelCache), (GetIntInMemorySegment) fuse_config::kernel_cache$get),
				Arguments.arguments(Named.of("setAutoCache()", (SetIntInConfig) FuseConfig::setAutoCache), (GetIntInMemorySegment) fuse_config::auto_cache$get),
				Arguments.arguments(Named.of("setAcAtrrTimeoutSet()", (SetIntInConfig) FuseConfig::setAcAttrTimeoutSet), (GetIntInMemorySegment) fuse_config::ac_attr_timeout_set$get),
				Arguments.arguments(Named.of("setNullpathOk()", (SetIntInConfig) FuseConfig::setNullpathOk), (GetIntInMemorySegment) fuse_config::nullpath_ok$get)
		);
	}

	private interface SetIntInConfig extends BiConsumer<FuseConfig, Integer> {
	}

	private interface GetIntInMemorySegment extends Function<MemorySegment, Integer> {
	}

	@DisplayName("test double setters")
	@ParameterizedTest(name = "{0}")
	@MethodSource
	public void testDoubleSetters(SetDoubleInConfig setter, GetDoubleInMemorySegment getter) {
		try (var scope = MemorySession.openConfined()) {
			var segment = fuse_config.allocate(scope);
			var connInfo = new FuseConfigImpl(segment.address(), scope);

			setter.accept(connInfo, 4.2);

			Assertions.assertEquals(4.2, getter.apply(segment));
		}
	}

	public static Stream<Arguments> testDoubleSetters() {
		return Stream.of(
				Arguments.arguments(Named.of("entryTimeout()", (SetDoubleInConfig) FuseConfig::setEntryTimeout), (GetDoubleInMemorySegment) fuse_config::entry_timeout$get),
				Arguments.arguments(Named.of("setNegativeTimeout()", (SetDoubleInConfig) FuseConfig::setNegativeTimeout), (GetDoubleInMemorySegment) fuse_config::negative_timeout$get),
				Arguments.arguments(Named.of("setAttrTimeout()", (SetDoubleInConfig) FuseConfig::setAttrTimeout), (GetDoubleInMemorySegment) fuse_config::attr_timeout$get),
				Arguments.arguments(Named.of("setAcAttrTimeout()", (SetDoubleInConfig) FuseConfig::setAcAttrTimeout), (GetDoubleInMemorySegment) fuse_config::ac_attr_timeout$get)
		);
	}

	private interface SetDoubleInConfig extends BiConsumer<FuseConfig, Double> {
	}

	private interface GetDoubleInMemorySegment extends Function<MemorySegment, Double> {
	}
}
