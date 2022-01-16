package de.skymatic.fusepanama;

public interface Statvfs {

	/**
	 * @return Block size
	 */
	long getBsize();

	/**
	 * Sets the block size. Should normally be equal to {@link #setFrsize(long) frsize}.
	 *
	 * @param bsize Block size
	 */
	void setBsize(long bsize);

	/**
	 * @return Fragment size
	 */
	long getFrsize();

	/**
	 * Sets the fragment size (required on Windows). Should normally be equal to {@link #setBsize(long) bsize}.
	 *
	 * @param frsize Fragment size
	 * @see <a href="https://github.com/billziss-gh/winfsp/blob/73f587e674eda57333b42c7f284574d826a15a8b/src/dll/fuse/fuse_intf.c#L801-L802">WinFSP FS Quota Calculation</a>
	 */
	void setFrsize(long frsize);

	/**
	 * @return Number of total blocks
	 */
	long getBlocks();

	/**
	 * Sets the number of total blocks.
	 *
	 * @param blocks Number of total blocks
	 */
	void setBlocks(long blocks);

	/**
	 * @return Number of unallocated blocks
	 */
	long getBfree();

	/**
	 * Sets the number of unallocated blocks.
	 *
	 * @param bfree Number of unallocated blocks.
	 */
	void setBfree(long bfree);

	/**
	 * @return Number of usable blocks
	 */
	long getBavail();

	/**
	 * Sets the number of usable blocks.
	 *
	 * @param bavail Number of usable blocks
	 */
	void setBavail(long bavail);

	/**
	 * @return Maximum filename length
	 */
	long getNameMax();

	/**
	 * Sets the maximum filename length.
	 *
	 * @param namemax Maxium filename length
	 */
	void setNameMax(long namemax);

}
