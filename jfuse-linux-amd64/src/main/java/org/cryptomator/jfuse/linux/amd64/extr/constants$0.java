// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
class constants$0 {

    static final FunctionDescriptor fuse_opt_add_arg$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_opt_add_arg$MH = RuntimeHelper.downcallHandle(
        "fuse_opt_add_arg",
        constants$0.fuse_opt_add_arg$FUNC, false
    );
    static final FunctionDescriptor fuse_mount$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_mount$MH = RuntimeHelper.downcallHandle(
        "fuse_mount",
        constants$0.fuse_mount$FUNC, false
    );
    static final FunctionDescriptor fuse_unmount$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_unmount$MH = RuntimeHelper.downcallHandle(
        "fuse_unmount",
        constants$0.fuse_unmount$FUNC, false
    );
    static final FunctionDescriptor fuse_parse_cmdline$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_parse_cmdline$MH = RuntimeHelper.downcallHandle(
        "fuse_parse_cmdline",
        constants$0.fuse_parse_cmdline$FUNC, false
    );
    static final FunctionDescriptor fuse_fill_dir_t$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG_LONG$LAYOUT
    );
    static final MethodHandle fuse_fill_dir_t$MH = RuntimeHelper.downcallHandle(
        constants$0.fuse_fill_dir_t$FUNC, false
    );
}


