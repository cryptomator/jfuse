// Generated by jextract

package org.cryptomator.jfuse.mac.extr.fuse;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$19 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$19() {}
    static final VarHandle const$0 = constants$9.const$4.varHandle(MemoryLayout.PathElement.groupElement("flush"));
    static final MethodHandle const$1 = RuntimeHelper.upcallHandle(fuse_operations.release.class, "apply", constants$9.const$5);
    static final VarHandle const$2 = constants$9.const$4.varHandle(MemoryLayout.PathElement.groupElement("release"));
    static final FunctionDescriptor const$3 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        JAVA_INT,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse_operations.fsync.class, "apply", constants$19.const$3);
    static final MethodHandle const$5 = RuntimeHelper.downcallHandle(
        constants$19.const$3
    );
}


