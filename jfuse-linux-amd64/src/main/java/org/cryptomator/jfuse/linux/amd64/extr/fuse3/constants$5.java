// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$5 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$5() {}
    static final FunctionDescriptor const$0 = FunctionDescriptor.ofVoid(
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$1 = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_destroy",
        constants$5.const$0
    );
    static final FunctionDescriptor const$2 = FunctionDescriptor.ofVoid(
        RuntimeHelper.POINTER,
        JAVA_INT
    );
    static final MethodHandle const$3 = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_set_max_threads",
        constants$5.const$2
    );
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_set_clone_fd",
        constants$5.const$2
    );
    static final StructLayout const$5 = MemoryLayout.structLayout(
        JAVA_LONG.withName("st_dev"),
        JAVA_LONG.withName("st_ino"),
        JAVA_LONG.withName("st_nlink"),
        JAVA_INT.withName("st_mode"),
        JAVA_INT.withName("st_uid"),
        JAVA_INT.withName("st_gid"),
        JAVA_INT.withName("__pad0"),
        JAVA_LONG.withName("st_rdev"),
        JAVA_LONG.withName("st_size"),
        JAVA_LONG.withName("st_blksize"),
        JAVA_LONG.withName("st_blocks"),
        MemoryLayout.structLayout(
            JAVA_LONG.withName("tv_sec"),
            JAVA_LONG.withName("tv_nsec")
        ).withName("st_atim"),
        MemoryLayout.structLayout(
            JAVA_LONG.withName("tv_sec"),
            JAVA_LONG.withName("tv_nsec")
        ).withName("st_mtim"),
        MemoryLayout.structLayout(
            JAVA_LONG.withName("tv_sec"),
            JAVA_LONG.withName("tv_nsec")
        ).withName("st_ctim"),
        MemoryLayout.sequenceLayout(3, JAVA_LONG).withName("__glibc_reserved")
    ).withName("stat");
}


