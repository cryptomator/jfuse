// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$22 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$22() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        constants$21.const$4
    );
    static final VarHandle const$1 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("truncate"));
    static final MethodHandle const$2 = RuntimeHelper.upcallHandle(fuse_operations.open.class, "apply", constants$18.const$4);
    static final VarHandle const$3 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("open"));
    static final FunctionDescriptor const$4 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        JAVA_LONG,
        JAVA_LONG,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$5 = RuntimeHelper.upcallHandle(fuse_operations.read.class, "apply", constants$22.const$4);
}


