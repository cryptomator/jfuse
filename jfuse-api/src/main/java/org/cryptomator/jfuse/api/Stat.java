package org.cryptomator.jfuse.api;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

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
		return (getMode() & S_IFDIR) == S_IFDIR;
	}

	/**
	 * Sets the <code>S_IFDIR</code> bit in {@link #setMode(int)}
	 *
	 * @param isDir Whether to set the <code>S_IFDIR</code> bit to one.
	 */
	default void toggleDir(boolean isDir) {
		if (isDir) {
			setMode(getMode() | S_IFDIR);
		} else {
			setMode(getMode() & ~S_IFDIR);
		}
	}

	/**
	 * @return <code>true</code> if <code>S_IFREG</code> bit is set in {@link #getMode()}
	 */
	default boolean isReg() {
		return (getMode() & S_IFREG) == S_IFREG;
	}

	/**
	 * Sets the <code>S_IFREG</code> bit in {@link #setMode(int)}
	 *
	 * @param isReg Whether to set the <code>S_IFREG</code> bit to one.
	 */
	default void toggleReg(boolean isReg) {
		if (isReg) {
			setMode(getMode() | S_IFREG);
		} else {
			setMode(getMode() & ~S_IFREG);
		}
	}

	/**
	 * @return <code>true</code> if <code>S_IFLNK</code> bit is set in {@link #getMode()}
	 */
	default boolean isLnk() {
		return (getMode() & S_IFLNK) == S_IFLNK;
	}

	/**
	 * Sets the <code>S_IFLNK</code> bit in {@link #setMode(int)}
	 *
	 * @param isLnk Whether to set the <code>S_IFLNK</code> bit to one.
	 */
	default void toggleLnk(boolean isLnk) {
		if (isLnk) {
			setMode(getMode() | S_IFLNK);
		} else {
			setMode(getMode() & ~S_IFLNK);
		}
	}

}
