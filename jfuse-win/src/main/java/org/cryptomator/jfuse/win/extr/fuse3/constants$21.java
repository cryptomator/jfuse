// Generated by jextract

package org.cryptomator.jfuse.win.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$21 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$21() {}
    static final MethodHandle const$0 = RuntimeHelper.upcallHandle(fuse3_operations.statfs.class, "apply", constants$16.const$0);
    static final VarHandle const$1 = constants$12.const$1.varHandle(MemoryLayout.PathElement.groupElement("statfs"));
    static final MethodHandle const$2 = RuntimeHelper.upcallHandle(fuse3_operations.flush.class, "apply", constants$16.const$0);
    static final VarHandle const$3 = constants$12.const$1.varHandle(MemoryLayout.PathElement.groupElement("flush"));
    static final MethodHandle const$4 = RuntimeHelper.upcallHandle(fuse3_operations.release.class, "apply", constants$16.const$0);
    static final VarHandle const$5 = constants$12.const$1.varHandle(MemoryLayout.PathElement.groupElement("release"));
}

