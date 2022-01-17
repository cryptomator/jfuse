package org.cryptomator.jfuse;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.stream.Stream;

public interface DirFiller {

	/**
	 * Inserts a single item into the directory entry buffer.
	 *
	 * @param name   The file name
	 * @param stat   Currently ignored, future use
	 * @param offset The offset of the readdir-call plus the number of already filled entries
	 * @return 0 if readdir should continue to fill in further items, non-zero otherwise (including errors)
	 * @see <a href="https://libfuse.github.io/doxygen/structfuse__operations.html#adc01d3622754fc7de8e643249e73268d">official readdir docs</a>
	 * @see <a href="https://www.cs.hmc.edu/~geoff/classes/hmc.cs135.201001/homework/fuse/fuse_doc.html#readdir-details">readdir explanation from Geoff Kuenning</a>
	 */
	int fill(String name, @Nullable Stat stat, long offset);

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long)}, ignoring the offset parameter.
	 *
	 * @param name The file name
	 * @param stat Currently ignored, future use.
	 * @throws IOException If {@link #fill(String, Stat, long) fuse_fill_dir_t} returns 1, which indicates an error.
	 */
	default void fill(String name, @Nullable Stat stat) throws IOException {
		if (fill(name, stat, 0) != 0) {
			throw new IOException("fuse_fill_dir_t unexpectedly returned 1");
		}
	}

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long)}, using the offset parameter.
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
			if (fill(child.name, child.stat, i + 1) != 0) {
				return;
			}
		}
	}

	/**
	 * Convenience wrapper for {@link #fill(String, Stat, long)}, using the offset parameter.
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
