// Generated by jextract

package org.cryptomator.jfuse.win.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$1 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$1() {}
    static final FunctionDescriptor fuse3_unmount$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse3_unmount$MH = RuntimeHelper.downcallHandle(
        "fuse3_unmount",
        constants$1.fuse3_unmount$FUNC
    );
    static final FunctionDescriptor fuse3_loop$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse3_loop$MH = RuntimeHelper.downcallHandle(
        "fuse3_loop",
        constants$1.fuse3_loop$FUNC
    );
    static final FunctionDescriptor fuse3_loop_mt$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse3_loop_mt$MH = RuntimeHelper.downcallHandle(
        "fuse3_loop_mt",
        constants$1.fuse3_loop_mt$FUNC
    );
    static final FunctionDescriptor fuse3_exit$FUNC = FunctionDescriptor.ofVoid(
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse3_exit$MH = RuntimeHelper.downcallHandle(
        "fuse3_exit",
        constants$1.fuse3_exit$FUNC
    );
    static final FunctionDescriptor fuse3_get_session$FUNC = FunctionDescriptor.of(Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse3_get_session$MH = RuntimeHelper.downcallHandle(
        "fuse3_get_session",
        constants$1.fuse3_get_session$FUNC
    );
}


