// Generated by jextract

package org.cryptomator.jfuse.mac.extr.fuse;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$23 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$23() {}
    static final VarHandle const$0 = constants$9.const$4.varHandle(MemoryLayout.PathElement.groupElement("releasedir"));
    static final MethodHandle const$1 = RuntimeHelper.upcallHandle(fuse_operations.fsyncdir.class, "apply", constants$19.const$3);
    static final VarHandle const$2 = constants$9.const$4.varHandle(MemoryLayout.PathElement.groupElement("fsyncdir"));
    static final FunctionDescriptor const$3 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse_operations.init.class, "apply", constants$23.const$3);
    static final MethodHandle const$5 = RuntimeHelper.downcallHandle(
        constants$23.const$3
    );
}

