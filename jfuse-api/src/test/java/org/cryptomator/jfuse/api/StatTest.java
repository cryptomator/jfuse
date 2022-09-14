package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import static org.cryptomator.jfuse.api.Stat.S_IFDIR;
import static org.cryptomator.jfuse.api.Stat.S_IFLNK;
import static org.cryptomator.jfuse.api.Stat.S_IFREG;

public class StatTest {

	private static final int MAGIC = 123456;//some rando number

	public Stat stat = Mockito.spy(new StubStatImpl());

	@Nested
	public class DirTest {

		@Test
		public void testIsDir() {
			Mockito.when(stat.getMode()).thenReturn(S_IFDIR | MAGIC);
			Assertions.assertTrue(stat.isDir());
		}

		@Test
		public void testToggleDir1() {
			Mockito.when(stat.getMode()).thenReturn(S_IFDIR | MAGIC);
			stat.toggleDir(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(Stat.S_IFDIR)));
		}

		@Test
		public void testToggleDir2() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleDir(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(Stat.S_IFDIR)));
		}

		@Test
		public void testToggleDir3() {
			Mockito.when(stat.getMode()).thenReturn(S_IFDIR | MAGIC);
			stat.toggleDir(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(Stat.S_IFDIR)));
		}

		@Test
		public void testToggleDir4() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleDir(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(Stat.S_IFDIR)));
		}

	}

	@Nested
	public class RegTest {

		@Test
		public void testIsReg() {
			Mockito.when(stat.getMode()).thenReturn(S_IFREG | MAGIC);
			Assertions.assertTrue(stat.isReg());
		}

		@Test
		public void testToggleReg1() {
			Mockito.when(stat.getMode()).thenReturn(S_IFREG | MAGIC);
			stat.toggleReg(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(S_IFREG)));
		}

		@Test
		public void testToggleReg2() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleReg(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(S_IFREG)));
		}

		@Test
		public void testToggleReg3() {
			Mockito.when(stat.getMode()).thenReturn(S_IFREG | MAGIC);
			stat.toggleReg(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(S_IFREG)));
		}

		@Test
		public void testToggleReg4() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleReg(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(S_IFREG)));
		}

	}

	@Nested
	public class LnkTest {

		@Test
		public void testIsLnk() {
			Mockito.when(stat.getMode()).thenReturn(S_IFLNK | MAGIC);
			Assertions.assertTrue(stat.isLnk());
		}

		@Test
		public void testToggleLnk1() {
			Mockito.when(stat.getMode()).thenReturn(S_IFLNK | MAGIC);
			stat.toggleLnk(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(S_IFLNK)));
		}

		@Test
		public void testToggleLnk2() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleLnk(true);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsSet(S_IFLNK)));
		}

		@Test
		public void testToggleLnk3() {
			Mockito.when(stat.getMode()).thenReturn(S_IFLNK | MAGIC);
			stat.toggleLnk(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(S_IFLNK)));
		}

		@Test
		public void testToggleLnk4() {
			Mockito.when(stat.getMode()).thenReturn(0);
			stat.toggleLnk(false);
			Mockito.verify(stat).setMode(Mockito.intThat(hasBitsNotSet(S_IFLNK)));
		}
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
