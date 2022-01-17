package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.linux.amd64.extr.fcntl_h;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_file_info;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Set;

record FileInfoImpl(MemorySegment segment) implements FileInfo {

	public FileInfoImpl(MemoryAddress address, ResourceScope scope) {
		this(fuse_file_info.ofAddress(address, scope));
	}

	@Override
	public long getFh() {
		return fuse_file_info.fh$get(segment);
	}

	@Override
	public void setFh(long fh) {
		fuse_file_info.fh$set(segment, fh);
	}

	@Override
	public int getFlags() {
		return fuse_file_info.flags$get(segment);
	}

	@Override
	public Set<StandardOpenOption> getOpenFlags() {
		Set<StandardOpenOption> result = EnumSet.noneOf(StandardOpenOption.class);
		int read = fcntl_h.O_RDONLY() | fcntl_h.O_RDWR();
		int write = fcntl_h.O_WRONLY() | fcntl_h.O_RDWR();
		int createNew = fcntl_h.O_WRONLY() | fcntl_h.O_EXCL();
		int flags = getFlags();
		// @formatter:off
		if ((flags & read) != 0)               result.add(StandardOpenOption.READ);
		if ((flags & write) != 0)              result.add(StandardOpenOption.WRITE);
		if ((flags & fcntl_h.O_APPEND()) != 0) result.add(StandardOpenOption.APPEND);
		if ((flags & fcntl_h.O_CREAT()) != 0)  result.add(StandardOpenOption.CREATE);
		if ((flags & createNew) != 0)          result.add(StandardOpenOption.CREATE_NEW);
		if ((flags & fcntl_h.O_TRUNC()) != 0)  result.add(StandardOpenOption.TRUNCATE_EXISTING);
		if ((flags & fcntl_h.O_SYNC()) != 0)   result.add(StandardOpenOption.SYNC);
		if ((flags & fcntl_h.O_DSYNC()) != 0)  result.add(StandardOpenOption.DSYNC);
		// @formatter:on
		return result;
	}

	@Override
	public long getLockOwner() {
		return fuse_file_info.lock_owner$get(segment);
	}

}
