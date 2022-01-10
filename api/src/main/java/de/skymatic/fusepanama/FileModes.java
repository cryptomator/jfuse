package de.skymatic.fusepanama;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ServiceLoader;

/**
 * Constants from <code>stat.h</code>
 */
public interface FileModes {

	ServiceLoader<FileModes> SERVICE_LOADER = ServiceLoader.load(FileModes.class);

	static FileModes instance() {
		// TODO: filter for current platform?
		return SERVICE_LOADER.findFirst().orElseThrow(() -> new IllegalStateException("No implementation of FileModes loaded."));
	}

	default int toMode(BasicFileAttributes attrs) {
		int mode = 0;
		if (attrs.isDirectory()) {
			mode |= dir();
		} else if (attrs.isRegularFile()) {
			mode |= reg();
		} else if (attrs.isSymbolicLink()) {
			mode |= lnk();
		}
		if (attrs instanceof PosixFileAttributes posix) {
			// @formatter:off
			if (posix.permissions().contains(PosixFilePermission.OWNER_READ)) mode |= ownerRead();
			if (posix.permissions().contains(PosixFilePermission.OWNER_WRITE)) mode |= ownerWrite();
			if (posix.permissions().contains(PosixFilePermission.OWNER_EXECUTE)) mode |= ownerExecute();
			if (posix.permissions().contains(PosixFilePermission.GROUP_READ)) mode |= groupRead();
			if (posix.permissions().contains(PosixFilePermission.GROUP_WRITE)) mode |= groupWrite();
			if (posix.permissions().contains(PosixFilePermission.GROUP_EXECUTE)) mode |= groupExecute();
			if (posix.permissions().contains(PosixFilePermission.OTHERS_READ)) mode |= otherRead();
			if (posix.permissions().contains(PosixFilePermission.OTHERS_WRITE)) mode |= otherWrite();
			if (posix.permissions().contains(PosixFilePermission.OTHERS_EXECUTE)) mode |= otherExecute();
			// @formatter:on
		}
		return mode;
	}

	/**
	 * @return Directory bit (S_IFDIR)
	 */
	int dir();

	/**
	 * @return Regular file bit (S_IFREG)
	 */
	int reg();

	/**
	 * @return Symbolic link bit (S_IFLNK)
	 */
	int lnk();

	/**
	 * @return R bit for owner (S_IRUSR)
	 */
	int ownerRead();

	/**
	 * @return W bit for owner (S_IWUSR)
	 */
	int ownerWrite();

	/**
	 * @return X bit for owner (S_IXUSR)
	 */
	int ownerExecute();

	/**
	 * @return R bit for group (S_IRGRP)
	 */
	int groupRead();

	/**
	 * @return W bit for group (S_IWGRP)
	 */
	int groupWrite();

	/**
	 * @return X bit for group (S_IXGRP)
	 */
	int groupExecute();

	/**
	 * @return R bit for other (S_IROTH)
	 */
	int otherRead();

	/**
	 * @return W bit for other (S_IWOTH)
	 */
	int otherWrite();

	/**
	 * @return X bit for other (S_IXOTH)
	 */
	int otherExecute();
}
