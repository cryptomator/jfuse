// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.ll;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
class constants$0 {

    static final FunctionDescriptor fuse_parse_cmdline$FUNC = FunctionDescriptor.of(Constants$root.C_INT$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle fuse_parse_cmdline$MH = RuntimeHelper.downcallHandle(
        "fuse_parse_cmdline@FUSE_3.0",
        constants$0.fuse_parse_cmdline$FUNC
    );
}


