// Generated by jextract

package de.skymatic.fusepanama.lowlevel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public interface fuse_fill_dir_t {

    int apply(jdk.incubator.foreign.MemoryAddress x0, jdk.incubator.foreign.MemoryAddress x1, jdk.incubator.foreign.MemoryAddress x2, long x3);
    static MemoryAddress allocate(fuse_fill_dir_t fi) {
        return RuntimeHelper.upcallStub(fuse_fill_dir_t.class, fi, constants$0.fuse_fill_dir_t$FUNC, "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;J)I");
    }
    static MemoryAddress allocate(fuse_fill_dir_t fi, ResourceScope scope) {
        return RuntimeHelper.upcallStub(fuse_fill_dir_t.class, fi, constants$0.fuse_fill_dir_t$FUNC, "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;J)I", scope);
    }
    static fuse_fill_dir_t ofAddress(MemoryAddress addr) {
        return (jdk.incubator.foreign.MemoryAddress x0, jdk.incubator.foreign.MemoryAddress x1, jdk.incubator.foreign.MemoryAddress x2, long x3) -> {
            try {
                return (int)constants$0.fuse_fill_dir_t$MH.invokeExact((Addressable)addr, x0, x1, x2, x3);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


