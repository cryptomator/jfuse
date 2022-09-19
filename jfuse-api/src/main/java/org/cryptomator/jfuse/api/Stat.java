package org.cryptomator.jfuse.api;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

@SuppressWarnings("OctalInteger")
public interface Stat {

	/**
	 * Mask value for file type socket
	 * See man page of inode(7).
	 */
	int S_IFSOCK = 0140000;
	/**
	 * Mask value for file type symbolic link
	 * See man page of inode(7).
	 */
	int S_IFLNK = 0120000;
	/**
	 * Mask value for file type regular file
	 * See man page of inode(7).
	 */
	int S_IFREG = 0100000;
	/**
	 * Mask value for file type block device
	 * See man page of inode(7).
	 */
	int S_IFBLK = 0060000;
	/**
	 * Mask value for file type directory
	 * See man page of inode(7).
	 */
	int S_IFDIR = 0040000;
	/**
	 * Mask value for file type character device
	 * See man page of inode(7).
	 */
	int S_IFCHR = 0020000;
	/**
	 * Mask value for file type named pipe (FIFO)
	 * See man page of inode(7).
	 */
	int S_IFIFO = 0010000;

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
	default boolean isDir() {
		return hasMode(S_IFDIR);
	}

	/**
	 * Sets the <code>S_IFDIR</code> bit in {@link #setMode(int)}
	 *
	 * @param isDir Whether to set the <code>S_IFDIR</code> bit to one.
	 */
	default void toggleDir(boolean isDir) {
		toggleMode(S_IFDIR, isDir);
	}

	/**
	 * @return <code>true</code> if <code>S_IFREG</code> bit is set in {@link #getMode()}
	 */
	default boolean isReg() {
		return hasMode(S_IFREG);
	}

	/**
	 * Sets the <code>S_IFREG</code> bit in {@link #setMode(int)}
	 *
	 * @param isReg Whether to set the <code>S_IFREG</code> bit to one.
	 */
	default void toggleReg(boolean isReg) {
		toggleMode(S_IFREG, isReg);
	}

	/**
	 * @return <code>true</code> if <code>S_IFLNK</code> bit is set in {@link #getMode()}
	 */
	default boolean isLnk() {
		return hasMode(S_IFLNK);
	}

	/**
	 * Sets the <code>S_IFLNK</code> bit in {@link #setMode(int)}
	 *
	 * @param isLnk Whether to set the <code>S_IFLNK</code> bit to one.
	 */
	default void toggleLnk(boolean isLnk) {
		toggleMode(S_IFLNK, isLnk);
	}

	default boolean hasMode(int mask) {
		return (getMode() & mask) == mask;
	}

	default void toggleMode(int mask, boolean set) {
		if (set) {
			setMode(getMode() | mask);
		} else {
			setMode(getMode() & ~mask);
		}
	}

}
