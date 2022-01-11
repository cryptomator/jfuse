package de.skymatic.fusepanama;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.ServiceLoader;
import java.util.Set;

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
			var perms = posix.permissions();
			// @formatter:off
			if (perms.contains(PosixFilePermission.OWNER_READ)) mode |= ownerRead();
			if (perms.contains(PosixFilePermission.OWNER_WRITE)) mode |= ownerWrite();
			if (perms.contains(PosixFilePermission.OWNER_EXECUTE)) mode |= ownerExecute();
			if (perms.contains(PosixFilePermission.GROUP_READ)) mode |= groupRead();
			if (perms.contains(PosixFilePermission.GROUP_WRITE)) mode |= groupWrite();
			if (perms.contains(PosixFilePermission.GROUP_EXECUTE)) mode |= groupExecute();
			if (perms.contains(PosixFilePermission.OTHERS_READ)) mode |= otherRead();
			if (perms.contains(PosixFilePermission.OTHERS_WRITE)) mode |= otherWrite();
			if (perms.contains(PosixFilePermission.OTHERS_EXECUTE)) mode |= otherExecute();
			// @formatter:on
		}
		return mode;
	}

	default Set<PosixFilePermission> toPermissions(int mode) {
		Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);
		// @formatter:off
		if ((mode & 0400) == 0400) result.add(PosixFilePermission.OWNER_READ);
		if ((mode & 0200) == 0200) result.add(PosixFilePermission.OWNER_WRITE);
		if ((mode & 0100) == 0100) result.add(PosixFilePermission.OWNER_EXECUTE);
		if ((mode & 0040) == 0040) result.add(PosixFilePermission.GROUP_READ);
		if ((mode & 0020) == 0020) result.add(PosixFilePermission.GROUP_WRITE);
		if ((mode & 0010) == 0010) result.add(PosixFilePermission.GROUP_EXECUTE);
		if ((mode & 0004) == 0004) result.add(PosixFilePermission.OTHERS_READ);
		if ((mode & 0002) == 0002) result.add(PosixFilePermission.OTHERS_WRITE);
		if ((mode & 0001) == 0001) result.add(PosixFilePermission.OTHERS_EXECUTE);
		// @formatter:on
		return result;
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

	/* TODO: do we need to keep the following? octal file permissions are universal on all targeted platforms */

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
