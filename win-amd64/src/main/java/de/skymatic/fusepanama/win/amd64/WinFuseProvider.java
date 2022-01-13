package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.FuseProvider;

public class WinFuseProvider implements FuseProvider {

	@Override
	public boolean isAvailable() {
		try {
			return Holder.INSTANCE.isAvailable();
		} catch (UnsatisfiedLinkError e) {
			return false;
		}
	}

	@Override
	public Fuse create(FuseOperations fuseOperations) {
		return Holder.INSTANCE.create(fuseOperations);
	}

	// initialization-on-demand used for lazy library loading
	private static class Holder implements FuseProvider {
		private static final Holder INSTANCE = new Holder();

		private Holder() {
			System.load("C:\\Program Files (x86)\\WinFsp\\bin\\winfsp-x64.dll"); // TODO: make configurable?
		}

		@Override
		public boolean isAvailable() {
			return true;
		}

		@Override
		public Fuse create(FuseOperations fuseOperations) {
			return new FuseImpl(fuseOperations);
		}

	}

}
