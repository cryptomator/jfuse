package de.skymatic.fusepanama;

import java.foreign.Scope;
import java.foreign.memory.Pointer;
import java.util.concurrent.ThreadLocalRandom;

import com.github.libfuse.fuse_lib;
import com.github.libfuse.fuse_opt_h;
import com.github.libfuse.fuse_opt_lib;

public class Fuse {

	/**
	 * Mounts the given file system at the given mount point.
	 * <p>
	 * This method blocks until unmounted.
	 */
	public static void mount(AbstractFuseFileSystem fs, String mountPoint, boolean debug) {
		try (Scope scope = Scope.globalScope().fork()) {
			var fuse_args = scope.allocateStruct(fuse_opt_h.fuse_args.class);
			fuse_args.argc$set(0);
			fuse_args.argv$set(Pointer.ofNull());
			fuse_args.allocated$set(0);
			fuse_opt_lib.fuse_opt_add_arg(fuse_args.ptr(), scope.allocateCString("fusefs-" + ThreadLocalRandom.current().nextInt()));
			fuse_opt_lib.fuse_opt_add_arg(fuse_args.ptr(), scope.allocateCString("-f"));
			if (debug) {
				fuse_opt_lib.fuse_opt_add_arg(fuse_args.ptr(), scope.allocateCString("-d"));
			}
			fuse_opt_lib.fuse_opt_add_arg(fuse_args.ptr(), scope.allocateCString(mountPoint));

			int result = fuse_lib.fuse_main_real(fuse_args.argc$get(), fuse_args.argv$get(), fs.fuseOperations.ptr(), FuseOperations.SIZEOF_FUSE_OPERATIONS, Pointer.ofNull());

			fuse_opt_lib.fuse_opt_free_args(fuse_args.ptr());

			if (result != 0) {
				throw new RuntimeException("mount failed with return code " + result);
			}
		}
	}


}
