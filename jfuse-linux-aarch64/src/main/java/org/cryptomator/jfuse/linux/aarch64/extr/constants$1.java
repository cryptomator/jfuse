// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
class constants$1 {

    static final FunctionDescriptor fuse_loop$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_loop$MH = RuntimeHelper.downcallHandle(
        "fuse_loop",
        constants$1.fuse_loop$FUNC
    );
    static final FunctionDescriptor fuse_exit$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_exit$MH = RuntimeHelper.downcallHandle(
        "fuse_exit",
        constants$1.fuse_exit$FUNC
    );
    static final FunctionDescriptor fuse_loop_mt$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_loop_mt$MH = RuntimeHelper.downcallHandle(
        "fuse_loop_mt",
        constants$1.fuse_loop_mt$FUNC
    );
    static final FunctionDescriptor fuse_get_context$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle fuse_get_context$MH = RuntimeHelper.downcallHandle(
        "fuse_get_context",
        constants$1.fuse_get_context$FUNC
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
        constants$1.fuse_main_real$FUNC
    );
    static final FunctionDescriptor fuse_get_session$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_get_session$MH = RuntimeHelper.downcallHandle(
        "fuse_get_session",
        constants$1.fuse_get_session$FUNC
    );
}

