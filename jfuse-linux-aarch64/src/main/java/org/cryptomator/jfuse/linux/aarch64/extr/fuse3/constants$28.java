// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$28 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$28() {}
    static final MethodHandle const$0 = RuntimeHelper.upcallHandle(fuse_operations.fsyncdir.class, "apply", constants$20.const$2);
    static final VarHandle const$1 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("fsyncdir"));
    static final FunctionDescriptor const$2 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$3 = RuntimeHelper.upcallHandle(fuse_operations.init.class, "apply", constants$28.const$2);
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        constants$28.const$2
    );
    static final VarHandle const$5 = constants$14.const$5.varHandle(MemoryLayout.PathElement.groupElement("init"));
}

