package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.mac.extr.fuse_args;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

record FuseArgs(MemorySegment args, MemoryAddress mountPoint, boolean multiThreaded) {

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var argc = fuse_args.argc$get(args);
		var argv = fuse_args.argv$get(args);
		for (int i = 0; i < argc; i++) {
			var cString = argv.getAtIndex(ValueLayout.ADDRESS, i);
			sb.append("arg[").append(i).append("] = ").append(cString.getUtf8String(0)).append(", ");
		}
		sb.append("mountPoint = ").append(mountPoint.getUtf8String(0)).append(", ");
		sb.append("multiThreaded = ").append(multiThreaded);
		return sb.toString();
	}
}
