// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$16 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$16() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        constants$15.const$4
    );
    static final VarHandle const$1 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("readlink"));
    static final FunctionDescriptor const$2 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        JAVA_INT,
        JAVA_LONG
    );
    static final MethodHandle const$3 = RuntimeHelper.upcallHandle(fuse_operations.mknod.class, "apply", constants$16.const$2);
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        constants$16.const$2
    );
    static final VarHandle const$5 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("mknod"));
}


