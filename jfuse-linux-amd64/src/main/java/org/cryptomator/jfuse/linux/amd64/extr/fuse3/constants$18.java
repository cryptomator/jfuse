// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$18 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$18() {}
    static final VarHandle const$0 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("unlink"));
    static final MethodHandle const$1 = RuntimeHelper.upcallHandle(fuse_operations.rmdir.class, "apply", constants$17.const$3);
    static final VarHandle const$2 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("rmdir"));
    static final FunctionDescriptor const$3 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse_operations.symlink.class, "apply", constants$18.const$3);
    static final MethodHandle const$5 = RuntimeHelper.downcallHandle(
        constants$18.const$3
    );
}

