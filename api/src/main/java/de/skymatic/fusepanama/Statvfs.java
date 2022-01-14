package de.skymatic.fusepanama;

public interface Statvfs {

	/**
	 * @return Block size
	 */
	long getBsize();

	void setBsize(long bsize);

	/**
	 * @return Fragment size
	 */
	long getFrsize();

	// TODO: remove. ignored during statfs() call anyway...
	void setFrsize(long frsize);

	/**
	 * @return Number of total blocks
	 */
	long getBlocks();

	void setBlocks(long blocks);

	/**
	 * @return Number of unallocated blocks
	 */
	long getBfree();

	void setBfree(long bfree);

	/**
	 * @return Number of usable blocks
	 */
	long getBavail();

	void setBavail(long bavail);

	/**
	 * @return Maximum filename length
	 */
	long getNameMax();

	void setNameMax(long namemax);

}
