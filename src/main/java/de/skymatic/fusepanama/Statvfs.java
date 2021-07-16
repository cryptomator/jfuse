package de.skymatic.fusepanama;

public interface Statvfs {

	long getBsize();

	void setBsize(long bsize);

	long getBlocks();

	void setBlocks(int blocks);

	long getBfree();

	void setBfree(int bfree);

	long getBavail();

	void setBavail(int bavail);

	long getNameMax();

	void setNameMax(long namemax);

}
