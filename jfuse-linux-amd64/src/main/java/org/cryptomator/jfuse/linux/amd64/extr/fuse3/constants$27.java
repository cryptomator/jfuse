// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$27 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$27() {}
    static final MethodHandle const$0 = RuntimeHelper.upcallHandle(fuse_operations.readdir.class, "apply", constants$26.const$5);
    static final MethodHandle const$1 = RuntimeHelper.downcallHandle(
        constants$26.const$5
    );
    static final VarHandle const$2 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("readdir"));
    static final MethodHandle const$3 = RuntimeHelper.upcallHandle(fuse_operations.releasedir.class, "apply", constants$18.const$3);
    static final VarHandle const$4 = constants$14.const$4.varHandle(MemoryLayout.PathElement.groupElement("releasedir"));
    static final MethodHandle const$5 = RuntimeHelper.upcallHandle(fuse_operations.fsyncdir.class, "apply", constants$20.const$1);
}

