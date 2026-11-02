package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_conn_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class FuseConnInfoImpl317Test {

	@DisplayName("XFeatureFlag methods are delegating to FuseFunctions")
	@Test
	void testXFeatureFlagMethods() {
		try (var arena = Arena.ofConfined();
			 var fuseFunctionsMock = Mockito.mockStatic(FuseFunctions.class)) {
			var segment = fuse_conn_info.allocate(arena);
			var connInfo = new FuseConnInfoImpl317(segment);
			fuseFunctionsMock.when(FuseFunctions::hasFeatureFlagFunctions).thenReturn(true);
			fuseFunctionsMock.when(() -> FuseFunctions.fuse_set_feature_flag(any(), anyLong())).thenReturn(true);
			fuseFunctionsMock.when(() -> FuseFunctions.fuse_unset_feature_flag(any(), anyLong())).then(Answers.RETURNS_DEFAULTS);
			fuseFunctionsMock.when(() -> FuseFunctions.fuse_get_feature_flag(any(), anyLong())).thenReturn(true);

			Assertions.assertDoesNotThrow(() -> connInfo.setFeatureFlag(3003L));
			Assertions.assertDoesNotThrow(() -> connInfo.getFeatureFlag(3003L));
			Assertions.assertDoesNotThrow(() -> connInfo.unsetFeatureFlag(3003L));

			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_set_feature_flag(segment, 3003L));
			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_unset_feature_flag(segment, 3003L));
			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_get_feature_flag(segment, 3003L));
		}
	}

	@DisplayName("setFeatureFlag() sets want_ext without FuseFunctions, if the flag is in capable_ext")
	@Test
	void testSetFeatureFlagFallback() {
		try (var arena = Arena.ofConfined();
			 var fuseFunctionsMock = Mockito.mockStatic(FuseFunctions.class)) {
			var segment = fuse_conn_info.allocate(arena);
			fuse_conn_info.capable_ext(segment, 0b0110L);
			var connInfo = new FuseConnInfoImpl317(segment);
			fuseFunctionsMock.when(FuseFunctions::hasFeatureFlagFunctions).thenReturn(false);

			Assertions.assertTrue(connInfo.setFeatureFlag(0b0010L));
			Assertions.assertEquals(0b0010L, connInfo.wantExt());

			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_set_feature_flag(any(), anyLong()), Mockito.never());
		}
	}

	@DisplayName("setFeatureFlag() leaves want_ext untouched without FuseFunctions, if the flag is not in capable_ext")
	@Test
	void testSetFeatureFlagFallbackNotCapable() {
		try (var arena = Arena.ofConfined();
			 var fuseFunctionsMock = Mockito.mockStatic(FuseFunctions.class)) {
			var segment = fuse_conn_info.allocate(arena);
			fuse_conn_info.capable_ext(segment, 0b0110L);
			var connInfo = new FuseConnInfoImpl317(segment);
			fuseFunctionsMock.when(FuseFunctions::hasFeatureFlagFunctions).thenReturn(false);

			Assertions.assertFalse(connInfo.setFeatureFlag(0b1000L));
			Assertions.assertEquals(0L, connInfo.wantExt());
		}
	}

	@DisplayName("unsetFeatureFlag() clears want_ext without FuseFunctions")
	@Test
	void testUnsetFeatureFlagFallback() {
		try (var arena = Arena.ofConfined();
			 var fuseFunctionsMock = Mockito.mockStatic(FuseFunctions.class)) {
			var segment = fuse_conn_info.allocate(arena);
			fuse_conn_info.want_ext(segment, 0b0110L);
			var connInfo = new FuseConnInfoImpl317(segment);
			fuseFunctionsMock.when(FuseFunctions::hasFeatureFlagFunctions).thenReturn(false);

			connInfo.unsetFeatureFlag(0b0010L);

			Assertions.assertEquals(0b0100L, connInfo.wantExt());
			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_unset_feature_flag(any(), anyLong()), Mockito.never());
		}
	}

	@DisplayName("getFeatureFlag() reads capable_ext without FuseFunctions")
	@Test
	void testGetFeatureFlagFallback() {
		try (var arena = Arena.ofConfined();
			 var fuseFunctionsMock = Mockito.mockStatic(FuseFunctions.class)) {
			var segment = fuse_conn_info.allocate(arena);
			fuse_conn_info.capable_ext(segment, 0b0110L);
			var connInfo = new FuseConnInfoImpl317(segment);
			fuseFunctionsMock.when(FuseFunctions::hasFeatureFlagFunctions).thenReturn(false);

			Assertions.assertTrue(connInfo.getFeatureFlag(0b0100L));
			Assertions.assertFalse(connInfo.getFeatureFlag(0b1000L));

			fuseFunctionsMock.verify(() -> FuseFunctions.fuse_get_feature_flag(any(), anyLong()), Mockito.never());
		}
	}

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
				Arguments.arguments((SetInMemorySegment) fuse_conn_info::capable_ext, Named.of("capableExt()", (GetInConnInfo) FuseConnInfo::capableExt))
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
