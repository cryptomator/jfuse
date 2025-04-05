package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.win.extr.fuse2.fuse_args;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

record FuseArgs(MemorySegment args, MemorySegment mountPoint, boolean multiThreaded) {

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var argc = fuse_args.argc(args);
		var argv = fuse_args.argv(args);
		for (int i = 0; i < argc; i++) {
			var cString = argv.getAtIndex(ValueLayout.ADDRESS, i).reinterpret(Long.MAX_VALUE);
			sb.append("arg[").append(i).append("] = ").append(cString.getString(0)).append(", ");
		}
		sb.append("mountPoint = ").append(mountPoint().reinterpret(Long.MAX_VALUE).getString(0)).append(", ");
		sb.append("singlethreaded = ").append(!multiThreaded);
		return sb.toString();
	}
}