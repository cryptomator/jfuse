package org.cryptomator.jfuse.api;

import java.nio.file.StandardOpenOption;
import java.util.Set;

/**
 * The {@code fi} argument passed to most calls in {@link FuseOperations} when working on an open file.
 */
public interface FileInfo {

	/**
	 * File handle id. May be filled in by filesystem in {@link FuseOperations#create(String, int, FileInfo) create},
	 * {@link FuseOperations#open(String, FileInfo) open}, and {@link FuseOperations#opendir(String, FileInfo) opendir()}.
	 * Available in most other file operations on the same file handle.
	 *
	 * @return File handle id
	 */
	long getFh();

	/**
	 * Sets the file handle id during {@link FuseOperations#create(String, int, FileInfo) create},
	 * {@link FuseOperations#open(String, FileInfo) open} or {@link FuseOperations#opendir(String, FileInfo) opendir()}.
	 *
	 * @param fh File handle id
	 */
	void setFh(long fh);

	/**
	 * Open flags. Available in {@link FuseOperations#open(String, FileInfo)} and {@link FuseOperations#release(String, FileInfo)}.
	 *
	 * @return Open flags
	 */
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

	/**
	 * Lock owner id. Available in locking operations and flush
	 *
	 * @return Lock owner id
	 */
	long getLockOwner();

}
