// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
class constants$1 {

    static final FunctionDescriptor fuse_destroy$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_destroy$MH = RuntimeHelper.downcallHandle(
        "fuse_destroy",
        constants$1.fuse_destroy$FUNC
    );
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
    static final FunctionDescriptor fuse_get_session$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_get_session$MH = RuntimeHelper.downcallHandle(
        "fuse_get_session",
        constants$1.fuse_get_session$FUNC
    );
}


