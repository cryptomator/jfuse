// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$32 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$32() {}
    static final VarHandle const$0 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("ioctl"));
    static final FunctionDescriptor const$1 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$2 = RuntimeHelper.upcallHandle(fuse_operations.poll.class, "apply", constants$32.const$1);
    static final MethodHandle const$3 = RuntimeHelper.downcallHandle(
        constants$32.const$1
    );
    static final VarHandle const$4 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("poll"));
    static final FunctionDescriptor const$5 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        JAVA_LONG,
        RuntimeHelper.POINTER
    );
}


