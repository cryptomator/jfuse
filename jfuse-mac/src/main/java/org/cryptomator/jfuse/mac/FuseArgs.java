package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.mac.extr.fuse.fuse_args;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

record FuseArgs(MemorySegment args, MemorySegment mountPoint, boolean multiThreaded) {

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var argc = fuse_args.argc$get(args);
		var argv = fuse_args.argv$get(args);
		for (int i = 0; i < argc; i++) {
			var cString = argv.getAtIndex(ValueLayout.ADDRESS, i).reinterpret(Long.MAX_VALUE);
			sb.append("arg[").append(i).append("] = ").append(cString.getUtf8String(0)).append(", ");
		}
		sb.append("mountPoint = ").append(mountPoint.getUtf8String(0)).append(", ");
		sb.append("multiThreaded = ").append(multiThreaded);
		return sb.toString();
	}
}
