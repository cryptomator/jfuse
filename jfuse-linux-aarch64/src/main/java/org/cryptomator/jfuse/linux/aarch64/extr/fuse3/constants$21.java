// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$21 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$21() {}
    static final FunctionDescriptor const$0 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        JAVA_INT,
        JAVA_INT,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$1 = RuntimeHelper.upcallHandle(fuse_operations.chown.class, "apply", constants$21.const$0);
    static final MethodHandle const$2 = RuntimeHelper.downcallHandle(
        constants$21.const$0
    );
    static final VarHandle const$3 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("chown"));
    static final FunctionDescriptor const$4 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        JAVA_LONG,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$5 = RuntimeHelper.upcallHandle(fuse_operations.truncate.class, "apply", constants$21.const$4);
}

