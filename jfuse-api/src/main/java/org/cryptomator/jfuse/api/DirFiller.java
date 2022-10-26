package org.cryptomator.jfuse.api;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * The DirFiller will be used during {@link FuseOperations#readdir(String, DirFiller, long, FileInfo, int) readdir} requests
 * and needs to be invoked by file system implementations once for every child of the read dir.
 */
public interface DirFiller {

	/**
	 * "Plus" mode: all file attributes are valid.
	 * <p>
	 * The attributes are used by the kernel to prefill the inode cache during a readdir.
	 * <p>
	 * It is okay to set FUSE_FILL_DIR_PLUS if {@link FuseOperations#FUSE_READDIR_PLUS FUSE_READDIR_PLUS} is not set
	 * and vice versa.
	 */
	int FUSE_FILL_DIR_PLUS = 1 << 1;

	/**
	 * Function to add an entry in a readdir() operation
	 * <p>
	 * The off parameter can be any non-zero value that enables the filesystem to identify the current point in the
	 * directory stream. It does not need to be the actual physical position. A value of zero is reserved to indicate
	 * that seeking in directories is not supported.
	 *
	 * @param name   the file name of the directory entry
	 * @param stat   file attributes, can be NULL
	 * @param offset offset of the next entry or zero when ignoring the offset parameter
	 * @param flags  fill flags, set to {@link #FUSE_FILL_DIR_PLUS} to cache stats
	 * @return 1 if buffer is full or an error occured, zero otherwise
	 * @see <a href="https://libfuse.github.io/doxygen/structfuse__operations.html#adc01d3622754fc7de8e643249e73268d">official readdir docs</a>
	 * @see <a href="https://www.cs.hmc.edu/~geoff/classes/hmc.cs135.201001/homework/fuse/fuse_doc.html#readdir-details">readdir explanation from Geoff Kuenning</a>
	 */
	int fill(String name, Consumer<Stat> stat, long offset, int flags);

	/**
	 * Convenience wrapper for {@link #fill(String, Consumer, long, int)}, ignoring the offset parameter.
	 *
	 * @param name  The file name
	 * @param stat  A method to pre-fill the stats of this node
	 * @throws IOException If {@link #fill(String, Consumer, long, int) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name, Consumer<Stat> stat) throws IOException {
		if (fill(name, stat, 0, 0) != 0) {
			throw new IOException("fuse_fill_dir_t unexpectedly returned 1");
		}
	}

	/**
	 * Convenienve wrapper for {@link #fill(String, Consumer, long, int)}, just filling in the name, ignoring stats.
	 *
	 * @param name The file name
	 * @throws IOException If {@link #fill(String, Consumer, long, int) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name) throws IOException {
		fill(name, stat -> {});
	}

}
