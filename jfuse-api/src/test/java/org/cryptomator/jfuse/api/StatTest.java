package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.nio.file.attribute.PosixFilePermissions;

import static org.cryptomator.jfuse.api.Stat.*;

@SuppressWarnings("OctalInteger")
public class StatTest {

	private static final int MAGIC = 123456;//some rando number

	public Stat stat = Mockito.spy(new StubStatImpl());

	@Nested
	@DisplayName("Test File Type Predicates")
	public class FileTypeTests {

		@Test
		@DisplayName("neither file mode bit set")
		public void testNone() {
			Assertions.assertFalse(Stat.S_ISREG.test(0));
			Assertions.assertFalse(Stat.S_ISDIR.test(0));
			Assertions.assertFalse(Stat.S_ISCHR.test(0));
			Assertions.assertFalse(Stat.S_ISBLK.test(0));
			Assertions.assertFalse(Stat.S_ISFIFO.test(0));
			Assertions.assertFalse(Stat.S_ISLNK.test(0));
			Assertions.assertFalse(Stat.S_ISSOCK.test(0));
		}

		@ParameterizedTest(name = "S_ISREG({0})")
		@DisplayName("S_ISREG")
		@ValueSource(ints = {S_IFREG, S_IFREG | 0755})
		public void testIsReg(int mode) {
			Assertions.assertTrue(Stat.S_ISREG.test(mode));
		}

		@ParameterizedTest(name = "S_ISDIR({0})")
		@DisplayName("S_ISDIR")
		@ValueSource(ints = {S_IFDIR, S_IFDIR | 0755})
		public void testIsDir(int mode) {
			Assertions.assertTrue(Stat.S_ISDIR.test(mode));
		}

		@ParameterizedTest(name = "S_ISCHR({0})")
		@DisplayName("S_ISCHR")
		@ValueSource(ints = {S_IFCHR, S_IFCHR | 0755})
		public void testIsChr(int mode) {
			Assertions.assertTrue(Stat.S_ISCHR.test(mode));
		}

		@ParameterizedTest(name = "S_ISBLK({0})")
		@DisplayName("S_ISBLK")
		@ValueSource(ints = {S_IFBLK, S_IFBLK | 0755})
		public void testIsBlk(int mode) {
			Assertions.assertTrue(Stat.S_ISBLK.test(mode));
		}

		@ParameterizedTest(name = "S_ISFIFO({0})")
		@DisplayName("S_ISFIFO")
		@ValueSource(ints = {S_IFIFO, S_IFIFO | 0755})
		public void testIsFifo(int mode) {
			Assertions.assertTrue(Stat.S_ISFIFO.test(mode));
		}

		@ParameterizedTest(name = "S_ISLNK({0})")
		@DisplayName("S_ISLNK")
		@ValueSource(ints = {S_IFLNK, S_IFLNK | 0755})
		public void testIsLnk(int mode) {
			Assertions.assertTrue(Stat.S_ISLNK.test(mode));
		}

		@ParameterizedTest(name = "S_ISSOCK({0})")
		@DisplayName("S_ISSOCK")
		@ValueSource(ints = {S_IFSOCK, S_IFSOCK | 0755})
		public void testIsSock(int mode) {
			Assertions.assertTrue(Stat.S_ISSOCK.test(mode));
		}

	}

	@Nested
	@DisplayName("permissions")
	public class Permissions {

		@DisplayName("getPermissions()")
		@ParameterizedTest(name = "getPermissions({0}) == {1}")
		@CsvSource(value = {
				"0755,rwxr-xr-x",
				"0644,rw-r--r--",
				"0010644,rw-r--r--"
		})
		public void getPermissions(String mode, String perms) {
			Mockito.when(stat.getMode()).thenReturn(Integer.valueOf(mode, 8));
			var expected = PosixFilePermissions.fromString(perms);

			var result = stat.getPermissions();

			Assertions.assertEquals(expected, result);
		}

		@DisplayName("setPermissions()")
		@ParameterizedTest(name = "setPermissions({1}) == {0}")
		@CsvSource(value = {
				"0755,rwxr-xr-x",
				"0644,rw-r--r--",
		})
		public void setPermissions(String mode, String perms) {
			Mockito.when(stat.getMode()).thenReturn(S_IFDIR);
			var permissions = PosixFilePermissions.fromString(perms);

			stat.setPermissions(permissions);

			Mockito.verify(stat).setMode(S_IFDIR | Integer.valueOf(mode, 8));
		}

	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	public void testHasMode(boolean isPresent) {
		Mockito.when(stat.getMode()).thenReturn(isPresent ? MAGIC : ~MAGIC);

		boolean result = stat.hasMode(MAGIC);

		Assertions.assertEquals(isPresent, result);
	}

	@Test
	@DisplayName("setModeBits()")
	public void testSetModeBits() {
		Mockito.when(stat.getMode()).thenReturn(0755);

		stat.setModeBits(S_IFDIR);

		Mockito.verify(stat).setMode(S_IFDIR | 0755);
	}

	@Test
	@DisplayName("unsetModeBits()")
	public void testUnsetModeBits() {
		Mockito.when(stat.getMode()).thenReturn(S_IFDIR | 0755);

		stat.unsetModeBits(S_IFDIR);

		Mockito.verify(stat).setMode(0755);
	}

	private static class StubStatImpl implements Stat {

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
