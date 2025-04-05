package org.cryptomator.jfuse.api;

/**
 * VFS File System information
 */
public interface Statvfs {

	/**
	 * File system block size.
	 *
	 * @return {@code f_bsize} value
	 */
	long getBsize();

	/**
	 * Set {@link #getBsize()}. Should normally be equal to {@link #setFrsize(long) frsize}.
	 *
	 * @param bsize {@code f_bsize} value
	 */
	void setBsize(long bsize);

	/**
	 * Fundamental file system block size.
	 *
	 * @return {@code f_frsize} value
	 */
	long getFrsize();

	/**
	 * Set {@link #getFrsize()}. Should normally be equal to {@link #setBsize(long) bsize}.
	 *
	 * @param frsize {@code f_frsize} value
	 * @see <a href="https://github.com/billziss-gh/winfsp/blob/73f587e674eda57333b42c7f284574d826a15a8b/src/dll/fuse/fuse_intf.c#L801-L802">WinFSP FS Quota Calculation</a>
	 */
	void setFrsize(long frsize);

	/**
	 * Total number of blocks on file system in units of {@link #getFrsize() f_frsize}.
	 *
	 * @return {@code f_blocks} value
	 */
	long getBlocks();

	/**
	 * Set {@link #getBlocks()}.
	 *
	 * @param blocks {@code f_blocks} value
	 */
	void setBlocks(long blocks);

	/**
	 * Total number of free blocks.
	 *
	 * @return {@code f_bfree} value
	 */
	long getBfree();

	/**
	 * Set {@link #getBfree()}.
	 *
	 * @param bfree {@code f_bfree} value
	 */
	void setBfree(long bfree);

	/**
	 * Number of free blocks available to non-privileged process.
	 *
	 * @return {@code f_bavail} value
	 */
	long getBavail();

	/**
	 * Set {@link #getBavail()}.
	 *
	 * @param bavail {@code f_bavail} value
	 */
	void setBavail(long bavail);

	/**
	 * Maximum filename length.
	 *
	 * @return {@code f_namemax} value
	 */
	long getNameMax();

	/**
	 * Set {@link #getNameMax()}.
	 *
	 * @param namemax {@code f_namemax} value
	 */
	void setNameMax(long namemax);

}
