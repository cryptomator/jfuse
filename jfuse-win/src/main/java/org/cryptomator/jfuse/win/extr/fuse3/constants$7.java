// Generated by jextract

package org.cryptomator.jfuse.win.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$7 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$7() {}
    static final VarHandle const$0 = constants$5.const$4.varHandle(MemoryLayout.PathElement.groupElement("max_background"));
    static final VarHandle const$1 = constants$5.const$4.varHandle(MemoryLayout.PathElement.groupElement("congestion_threshold"));
    static final VarHandle const$2 = constants$5.const$4.varHandle(MemoryLayout.PathElement.groupElement("time_gran"));
    static final FunctionDescriptor const$3 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        JAVA_LONG,
        JAVA_INT
    );
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse3_fill_dir_t.class, "apply", constants$7.const$3);
    static final MethodHandle const$5 = RuntimeHelper.downcallHandle(
        constants$7.const$3
    );
}


