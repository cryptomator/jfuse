// Generated by jextract

package de.skymatic.fusepanama.linux.lowlevel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
class constants$0 {

    static final FunctionDescriptor fuse_fill_dir_t$FUNC = FunctionDescriptor.of(C_INT,
        C_POINTER,
        C_POINTER,
        C_POINTER,
        C_LONG
    );
    static final MethodHandle fuse_fill_dir_t$MH = RuntimeHelper.downcallHandle(
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;J)I",
        constants$0.fuse_fill_dir_t$FUNC, false
    );
    static final FunctionDescriptor fuse_exit$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER
    );
    static final MethodHandle fuse_exit$MH = RuntimeHelper.downcallHandle(
        fuse_h.LIBRARIES, "fuse_exit",
        "(Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$0.fuse_exit$FUNC, false
    );
    static final FunctionDescriptor fuse_get_context$FUNC = FunctionDescriptor.of(C_POINTER);
    static final MethodHandle fuse_get_context$MH = RuntimeHelper.downcallHandle(
        fuse_h.LIBRARIES, "fuse_get_context",
        "()Ljdk/incubator/foreign/MemoryAddress;",
        constants$0.fuse_get_context$FUNC, false
    );
    static final FunctionDescriptor fuse_main_real$FUNC = FunctionDescriptor.of(C_INT,
        C_INT,
        C_POINTER,
        C_POINTER,
        C_LONG,
        C_POINTER
    );
    static final MethodHandle fuse_main_real$MH = RuntimeHelper.downcallHandle(
        fuse_h.LIBRARIES, "fuse_main_real",
        "(ILjdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;JLjdk/incubator/foreign/MemoryAddress;)I",
        constants$0.fuse_main_real$FUNC, false
    );
}

