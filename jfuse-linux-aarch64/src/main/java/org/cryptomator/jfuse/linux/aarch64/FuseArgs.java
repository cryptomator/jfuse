package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.linux.aarch64.extr.fuse3.fuse_args;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse3_lowlevel.fuse_cmdline_opts;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

record FuseArgs(MemorySegment args, MemorySegment cmdLineOpts) {

	@Override
	public String toString() {
		var sb = new StringBuilder();
		var argc = fuse_args.argc$get(args);
		var argv = fuse_args.argv$get(args);
		for (int i = 0; i < argc; i++) {
			var cString = argv.getAtIndex(ValueLayout.ADDRESS.withoutTargetLayout(), i).reinterpret(Long.MAX_VALUE);
			sb.append("arg[").append(i).append("] = ").append(cString.getUtf8String(0)).append(", ");
		}
		sb.append("mountPoint = ").append(mountPoint().getUtf8String(0));
		sb.append("debug = ").append(fuse_cmdline_opts.debug$get(cmdLineOpts));
		sb.append("singlethreaded = ").append(!multithreaded());
		return sb.toString();
	}

	public MemorySegment mountPoint() {
		return fuse_cmdline_opts.mountpoint$get(cmdLineOpts);
	}

	public boolean multithreaded() {
		return fuse_cmdline_opts.singlethread$get(cmdLineOpts) == 0;
	}

	public int cloneFd() {
		return fuse_cmdline_opts.clone_fd$get(cmdLineOpts);
	}

	public int maxIdleThreads() {
		return fuse_cmdline_opts.max_idle_threads$get(cmdLineOpts);
	}

	public int maxThreads() {
		return fuse_cmdline_opts.max_threads$get(cmdLineOpts);
	}
}
