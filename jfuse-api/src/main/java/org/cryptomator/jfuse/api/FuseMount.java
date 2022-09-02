package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Blocking;

public interface FuseMount {

	/**
	 * Runs <code>fuse_loop</code> or <code>fuse_loop_mt</code>,
	 * depending on the implementation and requested options.
	 *
	 * @return exit code returned by the <code>fuse_loop(_mt)</code>
	 */
	@Blocking
	int loop();

	/**
	 * Perform actions required to stop the {@link #loop() run loop}, e.g. <code>fuse_exit</code>
	 * and to unmount the volume, e.g. <code>fuse_unmount</code>.
	 */
	void unmount() ;

	/**
	 * Cleans up resources <em>after</em> the {@link #loop() run loop} exited.
	 */
	void destroy();

}
