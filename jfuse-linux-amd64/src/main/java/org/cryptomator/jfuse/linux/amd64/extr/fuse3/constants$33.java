// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$33 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$33() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        constants$32.const$4
    );
    static final VarHandle const$1 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("write_buf"));
    static final MethodHandle const$2 = RuntimeHelper.upcallHandle(fuse_operations.read_buf.class, "apply", constants$22.const$3);
    static final VarHandle const$3 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("read_buf"));
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse_operations.flock.class, "apply", constants$19.const$1);
    static final VarHandle const$5 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("flock"));
}

