package de.skymatic.fusepanama;

public interface DirFiller {

	/**
	 * Inserts a single item into the directory entry buffer.
	 *
	 * @param name   The file name
	 * @param stat   Currently ignored, future use.
	 * @param offset The offset of the readdir-call plus the number of already filled entries
	 * @return 0 if readdir should continue to fill in further items, non-zero otherwise
	 */
	int fill(String name, Stat stat, long offset);

}
