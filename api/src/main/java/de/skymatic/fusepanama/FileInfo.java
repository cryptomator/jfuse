package de.skymatic.fusepanama;

import java.nio.file.StandardOpenOption;
import java.util.Set;

public interface FileInfo {

	long getFh();

	void setFh(long fh);

	int getFlags();

	/**
	 * Parses the {@link #getFlags() flags} for <code>oflags</code> contained in <code>fcntl.h</code>.
	 * These flags are: <code>O_RDONLY, O_WRONLY, O_RDWR, O_APPEND, O_CREAT, O_DSYNC, O_SYNC, O_EXCL, O_TRUNC</code>
	 * <p>
	 * Flags that can not be directly mapped to {@link StandardOpenOption}s will be ignored.
	 *
	 * @return OpenOptions contained in {@link #getFlags()}
	 */
	Set<StandardOpenOption> getOpenFlags();

	long getLockOwner();

}
