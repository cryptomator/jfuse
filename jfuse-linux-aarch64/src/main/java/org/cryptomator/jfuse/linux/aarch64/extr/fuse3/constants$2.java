// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$2 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$2() {}
    static final StructLayout const$0 = MemoryLayout.structLayout(
        JAVA_INT.withName("clone_fd"),
        JAVA_INT.withName("max_idle_threads")
    ).withName("fuse_loop_config_v1");
    static final VarHandle const$1 = constants$2.const$0.varHandle(MemoryLayout.PathElement.groupElement("clone_fd"));
    static final VarHandle const$2 = constants$2.const$0.varHandle(MemoryLayout.PathElement.groupElement("max_idle_threads"));
    static final StructLayout const$3 = MemoryLayout.structLayout(
        JAVA_INT.withName("proto_major"),
        JAVA_INT.withName("proto_minor"),
        JAVA_INT.withName("max_write"),
        JAVA_INT.withName("max_read"),
        JAVA_INT.withName("max_readahead"),
        JAVA_INT.withName("capable"),
        JAVA_INT.withName("want"),
        JAVA_INT.withName("max_background"),
        JAVA_INT.withName("congestion_threshold"),
        JAVA_INT.withName("time_gran"),
        MemoryLayout.sequenceLayout(22, JAVA_INT).withName("reserved")
    ).withName("fuse_conn_info");
    static final VarHandle const$4 = constants$2.const$3.varHandle(MemoryLayout.PathElement.groupElement("proto_major"));
    static final VarHandle const$5 = constants$2.const$3.varHandle(MemoryLayout.PathElement.groupElement("proto_minor"));
}


