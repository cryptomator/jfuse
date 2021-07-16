package de.skymatic.fusepanama;

public interface Stat {

	TimeSpec aTime();

	TimeSpec cTime();

	TimeSpec mTime();

	TimeSpec birthTime();

	void setMode(short mode);

	short getMode();

	void setNLink(short count);

	short getNLink();

	void setSize(long size);

	long getSize();

}
