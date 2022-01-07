package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.FuseProvider;

public class MacFuseProvider implements FuseProvider {

	private volatile boolean initialized = false;

	synchronized private void init() {
		if (!initialized) {
			System.loadLibrary("fuse");
			initialized = true;
		}
	}

	@Override
	public boolean isAvailable() {
		try {
			init();
			return true;
		} catch (UnsatisfiedLinkError e) {
			return false;
		}
	}

	@Override
	public Fuse create(FuseOperations fuseOperations) {
		init();
		return new MacFuse(fuseOperations);
	}
}
