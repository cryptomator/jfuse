package de.skymatic.fusepanama;

public interface FileInfo {

	long getFh();

	void setFh(long fh);

	int getFlags();

	long getLockOwner();

}
