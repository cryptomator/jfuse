package org.cryptomator.jfuse.api;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

public interface Stat {

	TimeSpec aTime();

	TimeSpec cTime();

	TimeSpec mTime();

	TimeSpec birthTime();

	void setMode(int mode);

	int getMode();

	void setNLink(short count);

	long getNLink();

	void setSize(long size);

	long getSize();

	/**
	 * @return Parsed permission bits from {@link #getMode()}
	 */
	default Set<PosixFilePermission> getPermissions() {
		return FileModes.toPermissions(getMode());
	}

	/**
	 * Sets the permission bits in {@link #setMode(int)}
	 *
	 * @param permissions Permissions to apply
	 */
	default void setPermissions(Set<PosixFilePermission> permissions) {
		int mode = getMode() & ~FileModes.PERMISSIONS_MASK; // reset last 9 bits to zero
		mode |= FileModes.fromPermissions(permissions);
		setMode(mode);
	}

	/**
	 * @return <code>true</code> if <code>S_IFDIR</code> bit is set in {@link #getMode()}
	 */
	boolean isDir();

	/**
	 * Sets the <code>S_IFDIR</code> bit in {@link #setMode(int)}
	 *
	 * @param isDir Whether to set the <code>S_IFDIR</code> bit to one.
	 */
	void toggleDir(boolean isDir);

	/**
	 * @return <code>true</code> if <code>S_IFREG</code> bit is set in {@link #getMode()}
	 */
	boolean isReg();

	/**
	 * Sets the <code>S_IFREG</code> bit in {@link #setMode(int)}
	 *
	 * @param isReg Whether to set the <code>S_IFREG</code> bit to one.
	 */
	void toggleReg(boolean isReg);

	/**
	 * @return <code>true</code> if <code>S_IFLNK</code> bit is set in {@link #getMode()}
	 */
	boolean isLnk();

	/**
	 * Sets the <code>S_IFLNK</code> bit in {@link #setMode(int)}
	 *
	 * @param isLnk Whether to set the <code>S_IFLNK</code> bit to one.
	 */
	void toggleLnk(boolean isLnk);

}
