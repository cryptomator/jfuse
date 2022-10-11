// Generated by jextract

package org.cryptomator.jfuse.win.extr;

import java.lang.invoke.VarHandle;
import java.lang.foreign.*;

public class fuse3_file_info {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("flags"),
        MemoryLayout.structLayout(
            MemoryLayout.paddingLayout(1).withName("writepage"),
            MemoryLayout.paddingLayout(1).withName("direct_io"),
            MemoryLayout.paddingLayout(1).withName("keep_cache"),
            MemoryLayout.paddingLayout(1).withName("flush"),
            MemoryLayout.paddingLayout(1).withName("nonseekable"),
            MemoryLayout.paddingLayout(1).withName("flock_release"),
            MemoryLayout.paddingLayout(26),
            MemoryLayout.paddingLayout(27).withName("padding"),
            MemoryLayout.paddingLayout(37)
        ),
        Constants$root.C_LONG_LONG$LAYOUT.withName("fh"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("lock_owner"),
        Constants$root.C_LONG$LAYOUT.withName("poll_events"),
        MemoryLayout.paddingLayout(32)
    ).withName("fuse3_file_info");
    public static MemoryLayout $LAYOUT() {
        return fuse3_file_info.$struct$LAYOUT;
    }
    static final VarHandle flags$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("flags"));
    public static VarHandle flags$VH() {
        return fuse3_file_info.flags$VH;
    }
    public static int flags$get(MemorySegment seg) {
        return (int)fuse3_file_info.flags$VH.get(seg);
    }
    public static void flags$set( MemorySegment seg, int x) {
        fuse3_file_info.flags$VH.set(seg, x);
    }
    public static int flags$get(MemorySegment seg, long index) {
        return (int)fuse3_file_info.flags$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void flags$set(MemorySegment seg, long index, int x) {
        fuse3_file_info.flags$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle fh$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("fh"));
    public static VarHandle fh$VH() {
        return fuse3_file_info.fh$VH;
    }
    public static long fh$get(MemorySegment seg) {
        return (long)fuse3_file_info.fh$VH.get(seg);
    }
    public static void fh$set( MemorySegment seg, long x) {
        fuse3_file_info.fh$VH.set(seg, x);
    }
    public static long fh$get(MemorySegment seg, long index) {
        return (long)fuse3_file_info.fh$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void fh$set(MemorySegment seg, long index, long x) {
        fuse3_file_info.fh$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle lock_owner$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("lock_owner"));
    public static VarHandle lock_owner$VH() {
        return fuse3_file_info.lock_owner$VH;
    }
    public static long lock_owner$get(MemorySegment seg) {
        return (long)fuse3_file_info.lock_owner$VH.get(seg);
    }
    public static void lock_owner$set( MemorySegment seg, long x) {
        fuse3_file_info.lock_owner$VH.set(seg, x);
    }
    public static long lock_owner$get(MemorySegment seg, long index) {
        return (long)fuse3_file_info.lock_owner$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void lock_owner$set(MemorySegment seg, long index, long x) {
        fuse3_file_info.lock_owner$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle poll_events$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("poll_events"));
    public static VarHandle poll_events$VH() {
        return fuse3_file_info.poll_events$VH;
    }
    public static int poll_events$get(MemorySegment seg) {
        return (int)fuse3_file_info.poll_events$VH.get(seg);
    }
    public static void poll_events$set( MemorySegment seg, int x) {
        fuse3_file_info.poll_events$VH.set(seg, x);
    }
    public static int poll_events$get(MemorySegment seg, long index) {
        return (int)fuse3_file_info.poll_events$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void poll_events$set(MemorySegment seg, long index, int x) {
        fuse3_file_info.poll_events$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}

