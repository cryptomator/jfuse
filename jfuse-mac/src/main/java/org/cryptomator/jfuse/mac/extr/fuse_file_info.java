// Generated by jextract

package org.cryptomator.jfuse.mac.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class fuse_file_info {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_INT$LAYOUT.withName("flags"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_LONG_LONG$LAYOUT.withName("fh_old"),
        Constants$root.C_INT$LAYOUT.withName("writepage"),
        MemoryLayout.structLayout(
            MemoryLayout.paddingLayout(1).withName("direct_io"),
            MemoryLayout.paddingLayout(1).withName("keep_cache"),
            MemoryLayout.paddingLayout(1).withName("flush"),
            MemoryLayout.paddingLayout(1).withName("nonseekable"),
            MemoryLayout.paddingLayout(1).withName("flock_release"),
            MemoryLayout.paddingLayout(27).withName("padding")
        ),
        Constants$root.C_LONG_LONG$LAYOUT.withName("fh"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("lock_owner")
    ).withName("fuse_file_info");
    public static MemoryLayout $LAYOUT() {
        return fuse_file_info.$struct$LAYOUT;
    }
    static final VarHandle flags$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("flags"));
    public static VarHandle flags$VH() {
        return fuse_file_info.flags$VH;
    }
    public static int flags$get(MemorySegment seg) {
        return (int)fuse_file_info.flags$VH.get(seg);
    }
    public static void flags$set( MemorySegment seg, int x) {
        fuse_file_info.flags$VH.set(seg, x);
    }
    public static int flags$get(MemorySegment seg, long index) {
        return (int)fuse_file_info.flags$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void flags$set(MemorySegment seg, long index, int x) {
        fuse_file_info.flags$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle fh_old$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("fh_old"));
    public static VarHandle fh_old$VH() {
        return fuse_file_info.fh_old$VH;
    }
    public static long fh_old$get(MemorySegment seg) {
        return (long)fuse_file_info.fh_old$VH.get(seg);
    }
    public static void fh_old$set( MemorySegment seg, long x) {
        fuse_file_info.fh_old$VH.set(seg, x);
    }
    public static long fh_old$get(MemorySegment seg, long index) {
        return (long)fuse_file_info.fh_old$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void fh_old$set(MemorySegment seg, long index, long x) {
        fuse_file_info.fh_old$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle writepage$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("writepage"));
    public static VarHandle writepage$VH() {
        return fuse_file_info.writepage$VH;
    }
    public static int writepage$get(MemorySegment seg) {
        return (int)fuse_file_info.writepage$VH.get(seg);
    }
    public static void writepage$set( MemorySegment seg, int x) {
        fuse_file_info.writepage$VH.set(seg, x);
    }
    public static int writepage$get(MemorySegment seg, long index) {
        return (int)fuse_file_info.writepage$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void writepage$set(MemorySegment seg, long index, int x) {
        fuse_file_info.writepage$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle fh$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("fh"));
    public static VarHandle fh$VH() {
        return fuse_file_info.fh$VH;
    }
    public static long fh$get(MemorySegment seg) {
        return (long)fuse_file_info.fh$VH.get(seg);
    }
    public static void fh$set( MemorySegment seg, long x) {
        fuse_file_info.fh$VH.set(seg, x);
    }
    public static long fh$get(MemorySegment seg, long index) {
        return (long)fuse_file_info.fh$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void fh$set(MemorySegment seg, long index, long x) {
        fuse_file_info.fh$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle lock_owner$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("lock_owner"));
    public static VarHandle lock_owner$VH() {
        return fuse_file_info.lock_owner$VH;
    }
    public static long lock_owner$get(MemorySegment seg) {
        return (long)fuse_file_info.lock_owner$VH.get(seg);
    }
    public static void lock_owner$set( MemorySegment seg, long x) {
        fuse_file_info.lock_owner$VH.set(seg, x);
    }
    public static long lock_owner$get(MemorySegment seg, long index) {
        return (long)fuse_file_info.lock_owner$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void lock_owner$set(MemorySegment seg, long index, long x) {
        fuse_file_info.lock_owner$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


