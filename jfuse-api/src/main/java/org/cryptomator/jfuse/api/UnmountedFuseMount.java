package org.cryptomator.jfuse.api;

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
