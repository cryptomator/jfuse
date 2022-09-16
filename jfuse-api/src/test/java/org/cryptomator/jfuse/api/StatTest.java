package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.cryptomator.jfuse.api.Stat.S_IFDIR;
import static org.cryptomator.jfuse.api.Stat.S_IFLNK;
import static org.cryptomator.jfuse.api.Stat.S_IFREG;

public class StatTest {

	private static final int MAGIC = 123456;//some rando number

	public Stat stat = Mockito.spy(new StubStatImpl());

	@ParameterizedTest
	@MethodSource("provideDataForToggle")
	public void testToggleMode(int mode, int mask, boolean toSet) {
		Mockito.when(stat.getMode()).thenReturn(mode);
		stat.toggleMode(mask, toSet);
		Mockito.verify(stat).setMode(Mockito.intThat(toSet ? hasBitsSet(mask) : hasBitsNotSet(mask)));
	}

	public static Stream<Arguments> provideDataForToggle() {
		return Stream.of(
				Arguments.arguments(S_IFDIR, S_IFDIR, true), //
				Arguments.arguments(S_IFDIR, S_IFDIR, false), //
				Arguments.arguments(0, S_IFDIR, true), //
				Arguments.arguments(0, S_IFDIR, false)
		);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	public void testHasMode(boolean isPresent) {
		Mockito.when(stat.getMode()).thenReturn(isPresent ? MAGIC : ~MAGIC);
		boolean result = stat.hasMode(MAGIC);
		Assertions.assertEquals(isPresent, result);
	}


	@Test
	public void testIsDir() {
		stat.isDir();
		Mockito.verify(stat).hasMode(S_IFDIR);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	public void testToggleDir(boolean isDir) {
		stat.toggleDir(isDir);
		Mockito.verify(stat).toggleMode(S_IFDIR, isDir);
	}

	@Test
	public void testIsReg() {
		stat.isReg();
		Mockito.verify(stat).hasMode(S_IFREG);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	public void testToggleReg(boolean isReg) {
		stat.toggleReg(isReg);
		Mockito.verify(stat).toggleMode(S_IFREG, isReg);
	}

	@Test
	public void testIsLnk() {
		stat.isLnk();
		Mockito.verify(stat).hasMode(S_IFLNK);
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	public void testToggleLnk(boolean isLnk) {
		stat.toggleLnk(isLnk);
		Mockito.verify(stat).toggleMode(S_IFLNK, isLnk);
	}

	private static ArgumentMatcher<Integer> hasBitsSet(int mask) {
		return toMatch -> (toMatch & mask) == mask;
	}

	private static ArgumentMatcher<Integer> hasBitsNotSet(int mask) {
		return toMatch -> (toMatch & mask) == 0;
	}


	private class StubStatImpl implements Stat {

		@Override
		public TimeSpec aTime() {
			return null;
		}

		@Override
		public TimeSpec cTime() {
			return null;
		}

		@Override
		public TimeSpec mTime() {
			return null;
		}

		@Override
		public TimeSpec birthTime() {
			return null;
		}

		@Override
		public void setMode(int mode) {

		}

		@Override
		public int getMode() {
			return 0;
		}

		@Override
		public void setNLink(short count) {

		}

		@Override
		public long getNLink() {
			return 0;
		}

		@Override
		public void setSize(long size) {

		}

		@Override
		public long getSize() {
			return 0;
		}
	}

}
