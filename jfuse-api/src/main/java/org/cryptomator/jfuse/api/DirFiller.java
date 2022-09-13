package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
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
	 * @param offset offset of the next entry or zero
	 * @param flags  fill flags
	 * @return 1 if buffer is full, zero otherwise
	 * @see <a href="https://libfuse.github.io/doxygen/structfuse__operations.html#adc01d3622754fc7de8e643249e73268d">official readdir docs</a>
	 * @see <a href="https://www.cs.hmc.edu/~geoff/classes/hmc.cs135.201001/homework/fuse/fuse_doc.html#readdir-details">readdir explanation from Geoff Kuenning</a>
	 */
	int fill(String name, @Nullable Stat stat, long offset, Set<FillDirFlags> flags);

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long, Set)}, ignoring the offset parameter.
	 *
	 * @param name The file name
	 * @param stat Currently ignored, future use.
	 * @throws IOException If {@link #fill(String, Stat, long, Set) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name, @Nullable Stat stat) throws IOException {
		var plusMode = stat != null ? FILL_DIR_PLUS_FLAGS : Set.<FillDirFlags>of();
		if (fill(name, stat, 0, plusMode) != 0) {
			throw new IOException("fuse_fill_dir_t unexpectedly returned 1");
		}
	}

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long, Set)}, using the offset parameter.
	 * <p>
	 * <strong>Important:</strong> Note that the complete set of directors entries must contain <code>.</code> and <code>..</code>.
	 *
	 * @param children A stream of directory entries offset by the given <code>offset</code>
	 * @param offset   The offset of the stream (as requested by <code>readdir</code>)
	 */
	default void fillChildrenFromOffset(Stream<Child> children, long offset) {
		var iterator = children.iterator();
		for (long i = offset; iterator.hasNext(); i++) {
			var child = iterator.next();
			var plusMode = child.stat != null ? FILL_DIR_PLUS_FLAGS : Set.<FillDirFlags>of();
			if (fill(child.name, child.stat, i + 1, plusMode) != 0) {
				return;
			}
		}
	}

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long, Set)}, using the offset parameter.
	 * <p>
	 * <strong>Important:</strong> Note that the complete set of directors entries must contain <code>.</code> and <code>..</code>.
	 *
	 * @param childNames A stream of the names of directory entries offset by the given <code>offset</code>
	 * @param offset     The offset of the stream (as requested by <code>readdir</code>)
	 */
	default void fillNamesFromOffset(Stream<String> childNames, long offset) {
		var children = childNames.map(name -> new Child(name, null));
		fillChildrenFromOffset(children, offset);
	}

	record Child(String name, @Nullable Stat stat) {
	}

}
