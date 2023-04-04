// Generated by jextract

package org.cryptomator.jfuse.win.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * struct fuse3_loop_config {
 *     int clone_fd;
 *     unsigned int max_idle_threads;
 * };
 * }
 */
public class fuse3_loop_config {

    static final StructLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("clone_fd"),
        Constants$root.C_LONG$LAYOUT.withName("max_idle_threads")
    ).withName("fuse3_loop_config");
    public static MemoryLayout $LAYOUT() {
        return fuse3_loop_config.$struct$LAYOUT;
    }
    static final VarHandle clone_fd$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("clone_fd"));
    public static VarHandle clone_fd$VH() {
        return fuse3_loop_config.clone_fd$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int clone_fd;
     * }
     */
    public static int clone_fd$get(MemorySegment seg) {
        return (int)fuse3_loop_config.clone_fd$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int clone_fd;
     * }
     */
    public static void clone_fd$set(MemorySegment seg, int x) {
        fuse3_loop_config.clone_fd$VH.set(seg, x);
    }
    public static int clone_fd$get(MemorySegment seg, long index) {
        return (int)fuse3_loop_config.clone_fd$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void clone_fd$set(MemorySegment seg, long index, int x) {
        fuse3_loop_config.clone_fd$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle max_idle_threads$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("max_idle_threads"));
    public static VarHandle max_idle_threads$VH() {
        return fuse3_loop_config.max_idle_threads$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int max_idle_threads;
     * }
     */
    public static int max_idle_threads$get(MemorySegment seg) {
        return (int)fuse3_loop_config.max_idle_threads$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int max_idle_threads;
     * }
     */
    public static void max_idle_threads$set(MemorySegment seg, int x) {
        fuse3_loop_config.max_idle_threads$VH.set(seg, x);
    }
    public static int max_idle_threads$get(MemorySegment seg, long index) {
        return (int)fuse3_loop_config.max_idle_threads$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void max_idle_threads$set(MemorySegment seg, long index, int x) {
        fuse3_loop_config.max_idle_threads$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(long len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, SegmentScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


