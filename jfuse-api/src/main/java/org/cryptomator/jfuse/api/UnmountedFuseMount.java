package org.cryptomator.jfuse.api;

class UnmountedFuseMount implements FuseMount {
	@Override
	public void loop() {
		// no-op
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
