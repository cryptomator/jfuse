// Generated by jextract

package org.cryptomator.jfuse.win.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$8 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$8() {}
    static final StructLayout const$0 = MemoryLayout.structLayout(
        JAVA_INT.withName("set_gid"),
        JAVA_INT.withName("gid"),
        JAVA_INT.withName("set_uid"),
        JAVA_INT.withName("uid"),
        JAVA_INT.withName("set_mode"),
        JAVA_INT.withName("umask"),
        JAVA_DOUBLE.withName("entry_timeout"),
        JAVA_DOUBLE.withName("negative_timeout"),
        JAVA_DOUBLE.withName("attr_timeout"),
        JAVA_INT.withName("intr"),
        JAVA_INT.withName("intr_signal"),
        JAVA_INT.withName("remember"),
        JAVA_INT.withName("hard_remove"),
        JAVA_INT.withName("use_ino"),
        JAVA_INT.withName("readdir_ino"),
        JAVA_INT.withName("direct_io"),
        JAVA_INT.withName("kernel_cache"),
        JAVA_INT.withName("auto_cache"),
        JAVA_INT.withName("ac_attr_timeout_set"),
        JAVA_DOUBLE.withName("ac_attr_timeout"),
        JAVA_INT.withName("nullpath_ok"),
        JAVA_INT.withName("show_help"),
        RuntimeHelper.POINTER.withName("modules"),
        JAVA_INT.withName("debug"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse3_config");
    static final VarHandle const$1 = constants$8.const$0.varHandle(MemoryLayout.PathElement.groupElement("set_gid"));
    static final VarHandle const$2 = constants$8.const$0.varHandle(MemoryLayout.PathElement.groupElement("gid"));
    static final VarHandle const$3 = constants$8.const$0.varHandle(MemoryLayout.PathElement.groupElement("set_uid"));
    static final VarHandle const$4 = constants$8.const$0.varHandle(MemoryLayout.PathElement.groupElement("uid"));
    static final VarHandle const$5 = constants$8.const$0.varHandle(MemoryLayout.PathElement.groupElement("set_mode"));
}


