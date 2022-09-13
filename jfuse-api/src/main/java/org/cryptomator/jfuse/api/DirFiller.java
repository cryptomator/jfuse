package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface DirFiller {

	Set<FillDirFlags> FILL_DIR_PLUS_FLAGS = EnumSet.of(FillDirFlags.FILL_DIR_PLUS);

	enum FillDirFlags {

		/**
		 * "Plus" mode: all file attributes are valid.
		 * <p>
		 * The attributes are used by the kernel to prefill the inode cache during a readdir.
		 * <p>
		 * It is okay to set FILL_DIR_PLUS if {@link FuseOperations.ReadDirFlags#READ_DIR_PLUS READ_DIR_PLUS} is not set
		 * and vice versa.
		 */
		FILL_DIR_PLUS;

		/**
		 * Encodes the given {@code flags} into a bit set.
		 *
		 * @param flags       The flags
		 * @param fillDirPlus The constant value of {@code FUSE_FILL_DIR_PLUS}
		 * @return The bit set containing all the bits set for the given {@code flags}
		 */
		public static int toMask(Set<FillDirFlags> flags, int fillDirPlus) {
			return flags.contains(FILL_DIR_PLUS)
					? fillDirPlus
					: 0;
		}
	}

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
	 * @param flags  fill flags
	 * @return 1 if buffer is full or an error occured, zero otherwise
	 * @see <a href="https://libfuse.github.io/doxygen/structfuse__operations.html#adc01d3622754fc7de8e643249e73268d">official readdir docs</a>
	 * @see <a href="https://www.cs.hmc.edu/~geoff/classes/hmc.cs135.201001/homework/fuse/fuse_doc.html#readdir-details">readdir explanation from Geoff Kuenning</a>
	 */
	int fill(String name, Consumer<Stat> stat, long offset, Set<FillDirFlags> flags);

	/**
	 * Convenience wrapper for {@link #fill(String, Consumer, long, Set)}, ignoring the offset parameter.
	 *
	 * @param name  The file name
	 * @param stat  A method to pre-fill the stats of this node
	 * @param flags fill flags
	 * @throws IOException If {@link #fill(String, Consumer, long, Set) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name, Consumer<Stat> stat, Set<FillDirFlags> flags) throws IOException {
		if (fill(name, stat, 0, flags) != 0) {
			throw new IOException("fuse_fill_dir_t unexpectedly returned 1");
		}
	}

	/**
	 * Convenienve wrapper for {@link #fill(String, Consumer, long, Set)}, just filling in the name, ignoring stats.
	 *
	 * @param name The file name
	 * @throws IOException If {@link #fill(String, Consumer, long, Set) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name) throws IOException {
		fill(name, __ -> {
		}, Set.of());
	}

}
