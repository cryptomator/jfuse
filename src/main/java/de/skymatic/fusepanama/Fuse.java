package de.skymatic.fusepanama;

import de.skymatic.fusepanama.mac.MacFuse;

import java.nio.file.Path;
import java.util.concurrent.CompletionException;

public interface Fuse extends AutoCloseable {

	static Fuse create(FuseOperations fuseOperations) {
		return switch (Platform.CURRENT) {
			case MAC -> new MacFuse(fuseOperations);
			default -> throw new UnsupportedOperationException("");
		};
	}

	/**
	 * Mounts this fuse file system at the given mount point.
	 * <p>
	 * This method blocks until either {@link FuseOperations#init(FuseConnInfo)} completes or an error occurs.
	 *
	 * @param mountPoint mount point
	 * @return 0 if mounted successfully, or the result of <code>fuse_main_real()</code> in case of errors
	 * @throws CompletionException wrapping exceptions thrown during <code>init()</code> or <code>fuse_main_real()</code>
	 */
	int mount(Path mountPoint) throws CompletionException;

	@Override
	void close();

}
