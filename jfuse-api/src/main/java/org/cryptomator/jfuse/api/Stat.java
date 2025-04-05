package org.cryptomator.jfuse.api;

import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.function.IntPredicate;

/**
 * Represents the <code>stat</code> struct, which contains file attributes.
 *
 * @see <a href="https://man7.org/linux/man-pages/man2/stat.2.html">stat man page</a>
 * @see <a href="https://man7.org/linux/man-pages/man7/inode.7.html">inode man page</a>
 */
@SuppressWarnings("OctalInteger")
public interface Stat {

	/**
	 * Bit mask for the file type bit field
	 */
	int S_IFMT = 0170000;

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

	/**
	 * is it a regular file?
	 */
	IntPredicate S_ISREG = m -> (m & S_IFMT) == S_IFREG;

	/**
	 * directory?
	 */
	IntPredicate S_ISDIR = m -> (m & S_IFMT) == S_IFDIR;

	/**
	 * character device?
	 */
	IntPredicate S_ISCHR = m -> (m & S_IFMT) == S_IFCHR;

	/**
	 * block device?
	 */
	IntPredicate S_ISBLK = m -> (m & S_IFMT) == S_IFBLK;

	/**
	 * FIFO (named pipe)?
	 */
	IntPredicate S_ISFIFO = m -> (m & S_IFMT) == S_IFIFO;

	/**
	 * symbolic link?
	 */
	IntPredicate S_ISLNK = m -> (m & S_IFMT) == S_IFLNK;

	/**
	 * socket?
	 */
	IntPredicate S_ISSOCK = m -> (m & S_IFMT) == S_IFSOCK;

	/**
	 * Time of last access.
	 *
	 * @return {@code st_atime} value
	 */
	TimeSpec aTime();

	/**
	 * Time of last status change.
	 *
	 * @return {@code st_ctime} value
	 */
	TimeSpec cTime();

	/**
	 * Time of last data modification.
	 *
	 * @return {@code st_mtime} value
	 */
	TimeSpec mTime();

	/**
	 * File creation time.
	 *
	 * @return {@code st_birthtime} value
	 */
	TimeSpec birthTime();

	/**
	 * Set {@link #getMode() mode}.
	 *
	 * @param mode {@code st_mode} value
	 */
	void setMode(int mode);

	/**
	 * Mode of file.
	 *
	 * @return {@code st_mode} value
	 */
	int getMode();

	/**
	 * Set {@link  #getUid() uid}.
	 *
	 * @param uid {@code st_uid} value
	 */
	void setUid(int uid);

	/**
	 * User ID of file.
	 *
	 * @return {@code st_uid} value
	 */
	int getUid();

	/**
	 * Set {@link  #getGid() gid}.
	 *
	 * @param gid {@code st_gid} value
	 */
	void setGid(int gid);

	/**
	 * Group ID of file.
	 *
	 * @return {@code st_gid} value
	 */
	int getGid();

	/**
	 * Set {@link #getNLink() nlink}.
	 *
	 * @param nlink {@code st_nlink} value
	 */
	void setNLink(short nlink);

	/**
	 * Number of links to the file.
	 *
	 * @return {@code st_nlink} value
	 */
	long getNLink();

	/**
	 * Set {@link #getSize() size}.
	 *
	 * @param size {@code st_size} value
	 */
	void setSize(long size);

	/**
	 * File size in bytes (if file is a regular file).
	 *
	 * @return {@code st_size} value
	 */
	long getSize();

	/**
	 * Parses the permission bits from {@link #getMode()}.
	 *
	 * @return Permissions contained in mode
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
	 * Checks whether the given bits are set in {@link #getMode() mode}.
	 *
	 * @param mask Bits to check
	 * @return {@code true} if all bits from {@code mask} are set
	 */
	default boolean hasMode(int mask) {
		return (getMode() & mask) == mask;
	}

	/**
	 * Adds the given bit to {@link #getMode() mode}
	 *
	 * @param mask the bits to set
	 */
	default void setModeBits(int mask) {
		setMode(getMode() | mask);
	}

	/**
	 * Erases the given bit from {@link #getMode() mode}
	 *
	 * @param mask the bits to unset
	 */
	default void unsetModeBits(int mask) {
		setMode(getMode() & ~mask);
	}

}
