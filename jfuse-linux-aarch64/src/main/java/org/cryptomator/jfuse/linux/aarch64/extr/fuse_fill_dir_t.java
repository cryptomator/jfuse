// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public interface fuse_fill_dir_t {

    int apply(java.lang.foreign.MemoryAddress buf, java.lang.foreign.MemoryAddress name, java.lang.foreign.MemoryAddress stbuf, long off);
    static MemorySegment allocate(fuse_fill_dir_t fi, MemorySession session) {
        return RuntimeHelper.upcallStub(fuse_fill_dir_t.class, fi, constants$0.fuse_fill_dir_t$FUNC, session);
    }
    static fuse_fill_dir_t ofAddress(MemoryAddress addr, MemorySession session) {
        MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
        return (java.lang.foreign.MemoryAddress _buf, java.lang.foreign.MemoryAddress _name, java.lang.foreign.MemoryAddress _stbuf, long _off) -> {
            try {
                return (int)constants$0.fuse_fill_dir_t$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)_buf, (java.lang.foreign.Addressable)_name, (java.lang.foreign.Addressable)_stbuf, _off);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


