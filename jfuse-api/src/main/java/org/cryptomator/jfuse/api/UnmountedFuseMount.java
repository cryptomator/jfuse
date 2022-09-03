package org.cryptomator.jfuse.api;

/**
 * A dummy implementation that can safely be used for repeated invocations during {@link Fuse#close()}, making it idempotent.
 */
class UnmountedFuseMount implements FuseMount {
	@Override
	public int loop() {
		return 0;
	}

	@Override
	public void unmount() {
		// no-op
	}

	@Override
	public void destroy() {
		// no-op
	}
}
