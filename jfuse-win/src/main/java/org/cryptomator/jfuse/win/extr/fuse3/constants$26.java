// Generated by jextract

package org.cryptomator.jfuse.win.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$26 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$26() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        constants$25.const$4
    );
    static final VarHandle const$1 = constants$12.const$1.varHandle(MemoryLayout.PathElement.groupElement("init"));
    static final FunctionDescriptor const$2 = FunctionDescriptor.ofVoid(
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$3 = RuntimeHelper.upcallHandle(fuse3_operations.destroy.class, "apply", constants$26.const$2);
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        constants$26.const$2
    );
    static final VarHandle const$5 = constants$12.const$1.varHandle(MemoryLayout.PathElement.groupElement("destroy"));
}

