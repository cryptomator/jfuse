// Generated by jextract

package org.cryptomator.jfuse.win.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
class constants$0 {

    static final FunctionDescriptor fuse_fill_dir_t$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_LONG$LAYOUT
    );
    static final MethodHandle fuse_fill_dir_t$MH = RuntimeHelper.downcallHandle(
        constants$0.fuse_fill_dir_t$FUNC
    );
    static final FunctionDescriptor fuse_lib_help$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_lib_help$MH = RuntimeHelper.downcallHandle(
        "fuse_lib_help",
        constants$0.fuse_lib_help$FUNC
    );
    static final FunctionDescriptor fuse_new$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_new$MH = RuntimeHelper.downcallHandle(
        "fuse_new",
        constants$0.fuse_new$FUNC
    );
    static final FunctionDescriptor fuse_destroy$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_destroy$MH = RuntimeHelper.downcallHandle(
        "fuse_destroy",
        constants$0.fuse_destroy$FUNC
    );
    static final FunctionDescriptor fuse_mount$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_mount$MH = RuntimeHelper.downcallHandle(
        "fuse_mount",
        constants$0.fuse_mount$FUNC
    );
}


