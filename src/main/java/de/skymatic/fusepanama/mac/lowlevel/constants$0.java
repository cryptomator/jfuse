// Generated by jextract

package de.skymatic.fusepanama.mac.lowlevel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
class constants$0 {

    static final FunctionDescriptor fuse_fill_dir_t$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle fuse_fill_dir_t$MH = RuntimeHelper.downcallHandle(
        constants$0.fuse_fill_dir_t$FUNC, false
    );
    static final FunctionDescriptor fuse_exit$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_exit$MH = RuntimeHelper.downcallHandle(
        "fuse_exit",
        constants$0.fuse_exit$FUNC, false
    );
    static final FunctionDescriptor fuse_get_context$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle fuse_get_context$MH = RuntimeHelper.downcallHandle(
        "fuse_get_context",
        constants$0.fuse_get_context$FUNC, false
    );
    static final FunctionDescriptor fuse_main_real$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_main_real$MH = RuntimeHelper.downcallHandle(
        "fuse_main_real",
        constants$0.fuse_main_real$FUNC, false
    );
}


