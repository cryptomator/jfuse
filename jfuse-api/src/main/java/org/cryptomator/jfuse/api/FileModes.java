package org.cryptomator.jfuse.api;

import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;

/**
 * Utility for parsing and serializing file permissions to and from octal int representation.
 */
@SuppressWarnings("OctalInteger")
public final class FileModes {

	/**
	 * Mask of the 9 permission bits in a file mode integer
	 */
	public static final int PERMISSIONS_MASK = 0777;

	private FileModes() {
	}

	/**
	 * Parses permissions from the 9 least significant bits of the file mode.
	 *
	 * @param mode File mode
	 * @return Parsed permissions
	 */
	public static Set<PosixFilePermission> toPermissions(int mode) {
		Set<PosixFilePermission> permissions = EnumSet.noneOf(PosixFilePermission.class);
		// @formatter:off
		if ((mode & 0400) == 0400) permissions.add(PosixFilePermission.OWNER_READ);
		if ((mode & 0200) == 0200) permissions.add(PosixFilePermission.OWNER_WRITE);
		if ((mode & 0100) == 0100) permissions.add(PosixFilePermission.OWNER_EXECUTE);
		if ((mode & 0040) == 0040) permissions.add(PosixFilePermission.GROUP_READ);
		if ((mode & 0020) == 0020) permissions.add(PosixFilePermission.GROUP_WRITE);
		if ((mode & 0010) == 0010) permissions.add(PosixFilePermission.GROUP_EXECUTE);
		if ((mode & 0004) == 0004) permissions.add(PosixFilePermission.OTHERS_READ);
		if ((mode & 0002) == 0002) permissions.add(PosixFilePermission.OTHERS_WRITE);
		if ((mode & 0001) == 0001) permissions.add(PosixFilePermission.OTHERS_EXECUTE);
		// @formatter:on
		return permissions;
	}

	/**
	 * Serializes the given permissions into a integer.
	 *
	 * @param permissions Permissions
	 * @return An integer with the {@link #PERMISSIONS_MASK permission bits} set accordingly
	 */
	public static int fromPermissions(Set<PosixFilePermission> permissions) {
		int mode = 0;
		// @formatter:off
		if (permissions.contains(PosixFilePermission.OWNER_READ))     mode |= 0400;
		if (permissions.contains(PosixFilePermission.OWNER_WRITE))    mode |= 0200;
		if (permissions.contains(PosixFilePermission.OWNER_EXECUTE))  mode |= 0100;
		if (permissions.contains(PosixFilePermission.GROUP_READ))     mode |= 0040;
		if (permissions.contains(PosixFilePermission.GROUP_WRITE))    mode |= 0020;
		if (permissions.contains(PosixFilePermission.GROUP_EXECUTE))  mode |= 0010;
		if (permissions.contains(PosixFilePermission.OTHERS_READ))    mode |= 0004;
		if (permissions.contains(PosixFilePermission.OTHERS_WRITE))   mode |= 0002;
		if (permissions.contains(PosixFilePermission.OTHERS_EXECUTE)) mode |= 0001;
		// @formatter:on
		return mode;
	}

}
