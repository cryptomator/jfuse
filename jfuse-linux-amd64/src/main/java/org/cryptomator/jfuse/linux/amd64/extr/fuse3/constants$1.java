// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$1 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$1() {}
    static final VarHandle const$0 = constants$0.const$4.varHandle(MemoryLayout.PathElement.groupElement("tv_nsec"));
    static final StructLayout const$1 = MemoryLayout.structLayout(
        JAVA_INT.withName("flags"),
        MemoryLayout.paddingLayout(12),
        JAVA_LONG.withName("fh"),
        JAVA_LONG.withName("lock_owner"),
        JAVA_INT.withName("poll_events"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse_file_info");
    static final VarHandle const$2 = constants$1.const$1.varHandle(MemoryLayout.PathElement.groupElement("flags"));
    static final VarHandle const$3 = constants$1.const$1.varHandle(MemoryLayout.PathElement.groupElement("fh"));
    static final VarHandle const$4 = constants$1.const$1.varHandle(MemoryLayout.PathElement.groupElement("lock_owner"));
    static final VarHandle const$5 = constants$1.const$1.varHandle(MemoryLayout.PathElement.groupElement("poll_events"));
}


