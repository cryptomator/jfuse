// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$35 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$35() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        constants$34.const$4
    );
    static final VarHandle const$1 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("copy_file_range"));
    static final FunctionDescriptor const$2 = FunctionDescriptor.of(JAVA_LONG,
        RuntimeHelper.POINTER,
        JAVA_LONG,
        JAVA_INT,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$3 = RuntimeHelper.upcallHandle(fuse_operations.lseek.class, "apply", constants$35.const$2);
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        constants$35.const$2
    );
    static final VarHandle const$5 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("lseek"));
}

