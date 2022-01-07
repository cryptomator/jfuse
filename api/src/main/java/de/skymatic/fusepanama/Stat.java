package de.skymatic.fusepanama;

public interface Stat {

	TimeSpec aTime();

	TimeSpec cTime();

	TimeSpec mTime();

	TimeSpec birthTime();

	void setMode(short mode);

	int getMode();

	void setNLink(short count);

	long getNLink();

	void setSize(long size);

	long getSize();

}
