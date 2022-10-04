package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse_args;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_cmdline_opts;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

record FuseArgs(MemorySegment args, MemorySegment cmdLineOpts) {

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var argc = fuse_args.argc$get(args);
		var argv = fuse_args.argv$get(args);
		for (int i = 0; i < argc; i++) {
			var cString = argv.getAtIndex(ValueLayout.ADDRESS, i);
			sb.append("arg[").append(i).append("] = ").append(cString.getUtf8String(0)).append(", ");
		}
		sb.append("mountPoint = ").append(mountPoint().getUtf8String(0));
		sb.append("debug = ").append(fuse_cmdline_opts.debug$get(cmdLineOpts));
		sb.append("singlethreaded = ").append(!multithreaded());
		return sb.toString();
	}

	public MemoryAddress mountPoint() {
		return fuse_cmdline_opts.mountpoint$get(cmdLineOpts);
	}

	public boolean multithreaded() {
		return fuse_cmdline_opts.singlethread$get(cmdLineOpts) == 0;
	}

}
