// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$0 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$0() {}
    static final FunctionDescriptor fuse_version$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT);
    static final MethodHandle fuse_version$MH = RuntimeHelper.downcallHandle(
        "fuse_version",
        constants$0.fuse_version$FUNC
    );
    static final FunctionDescriptor fuse_loop_cfg_create$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT);
    static final MethodHandle fuse_loop_cfg_create$MH = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_create",
        constants$0.fuse_loop_cfg_create$FUNC
    );
    static final FunctionDescriptor fuse_loop_cfg_destroy$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_loop_cfg_destroy$MH = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_destroy",
        constants$0.fuse_loop_cfg_destroy$FUNC
    );
    static final FunctionDescriptor fuse_loop_cfg_set_max_threads$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle fuse_loop_cfg_set_max_threads$MH = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_set_max_threads",
        constants$0.fuse_loop_cfg_set_max_threads$FUNC
    );
    static final FunctionDescriptor fuse_loop_cfg_set_clone_fd$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
    static final MethodHandle fuse_loop_cfg_set_clone_fd$MH = RuntimeHelper.downcallHandle(
        "fuse_loop_cfg_set_clone_fd",
        constants$0.fuse_loop_cfg_set_clone_fd$FUNC
    );
    static final FunctionDescriptor fuse_fill_dir_t$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT,
        Constants$root.C_INT$LAYOUT
    );
}


