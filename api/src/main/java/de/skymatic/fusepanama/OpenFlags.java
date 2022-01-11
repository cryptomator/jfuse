package de.skymatic.fusepanama;

import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @see <a href="https://linux.die.net/man/3/open">open man page</a>
 */
public interface OpenFlags {

	ServiceLoader<OpenFlags> SERVICE_LOADER = ServiceLoader.load(OpenFlags.class);

	static OpenFlags instance() {
		// TODO: filter for current platform?
		return SERVICE_LOADER.findFirst().orElseThrow(() -> new IllegalStateException("No implementation of FileModes loaded."));
	}

	default Set<StandardOpenOption> toOpenOptions(long flags) {
		Set<StandardOpenOption> result = EnumSet.noneOf(StandardOpenOption.class);
		int read = readOnly() | readWrite();
		int write = writeOnly() | readWrite();
		int createNew = create() | excl();
		// @formatter:off
		if ((flags & read) != 0) result.add(StandardOpenOption.READ);
		if ((flags & write) != 0) result.add(StandardOpenOption.WRITE);
		if ((flags & append()) == append()) result.add(StandardOpenOption.APPEND);
		if ((flags & create()) == create()) result.add(StandardOpenOption.CREATE);
		if ((flags & createNew) == createNew) result.add(StandardOpenOption.CREATE_NEW);
		if ((flags & truncate()) == truncate()) result.add(StandardOpenOption.TRUNCATE_EXISTING);
		if ((flags & sync()) == sync()) result.add(StandardOpenOption.SYNC);
		if ((flags & dsync()) == dsync()) result.add(StandardOpenOption.DSYNC);
		// @formatter:on
		return result;
	}

	/**
	 * Open for reading only.
	 *
	 * @return read-only bit (O_RDONLY)
	 */
	int readOnly();

	/**
	 * Open for writing only.
	 *
	 * @return write-only bit (O_WRONLY)
	 */
	int writeOnly();

	/**
	 * Open for reading and writing.
	 *
	 * @return read-write bit (O_RDWR)
	 */
	int readWrite();

	/**
	 * Append to end of file.
	 *
	 * @return append bit (O_APPEND)
	 */
	int append();

	/**
	 * Create new file.
	 *
	 * @return create bit (O_CREAT)
	 * @see #excl()
	 */
	int create();

	/**
	 * Check for existence of file with same name.
	 * <p>
	 * If name is taken, fail with {@link Errno#eexist() EEXIST}
	 *
	 * @return exclusive bit (O_EXCL)
	 */
	int excl();

	/**
	 * Truncate file to length 0 when combined opening for writing.
	 *
	 * @return truncate bit (O_TRUNC)
	 */
	int truncate();

	/**
	 * Use synchronous writes for file contents and metadata.
	 *
	 * @return sync bit (O_SYNC)
	 */
	int sync();

	/**
	 * Use synchronous writes for file contents.
	 *
	 * @return dsync bit (O_DSYNC)
	 */
	int dsync();


}
