// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * int (*fuse_fill_dir_t)(void* buf,char* name,struct stat* stbuf,long off,enum fuse_fill_dir_flags flags);
 * }
 */
public interface fuse_fill_dir_t {

    int apply(java.lang.foreign.MemorySegment buf, java.lang.foreign.MemorySegment name, java.lang.foreign.MemorySegment stbuf, long off, int flags);
    static MemorySegment allocate(fuse_fill_dir_t fi, Arena scope) {
        return RuntimeHelper.upcallStub(constants$10.const$0, fi, constants$9.const$5, scope);
    }
    static fuse_fill_dir_t ofAddress(MemorySegment addr, Arena arena) {
        MemorySegment symbol = addr.reinterpret(arena, null);
        return (java.lang.foreign.MemorySegment _buf, java.lang.foreign.MemorySegment _name, java.lang.foreign.MemorySegment _stbuf, long _off, int _flags) -> {
            try {
                return (int)constants$10.const$1.invokeExact(symbol, _buf, _name, _stbuf, _off, _flags);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}

