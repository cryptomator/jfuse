package de.skymatic.fusepanama;

public interface Statvfs {

	long getBsize();

	void setBsize(long bsize);

	int getBlocks();

	void setBlocks(int blocks);

	int getBfree();

	void setBfree(int bfree);

	int getBavail();

	void setBavail(int bavail);

	long getNameMax();

	void setNameMax(long namemax);

}
