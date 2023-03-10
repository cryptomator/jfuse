// Generated by jextract

package org.cryptomator.jfuse.win.extr.fuse2;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SegmentScope;
import java.lang.foreign.StructLayout;
import java.lang.invoke.VarHandle;

public class fuse_args {

    static final StructLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("argc"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_POINTER$LAYOUT.withName("argv"),
        Constants$root.C_LONG$LAYOUT.withName("allocated"),
        MemoryLayout.paddingLayout(32)
    ).withName("fuse_args");
    public static MemoryLayout $LAYOUT() {
        return fuse_args.$struct$LAYOUT;
    }
    static final VarHandle argc$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("argc"));
    public static VarHandle argc$VH() {
        return fuse_args.argc$VH;
    }
    public static int argc$get(MemorySegment seg) {
        return (int)fuse_args.argc$VH.get(seg);
    }
    public static void argc$set( MemorySegment seg, int x) {
        fuse_args.argc$VH.set(seg, x);
    }
    public static int argc$get(MemorySegment seg, long index) {
        return (int)fuse_args.argc$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void argc$set(MemorySegment seg, long index, int x) {
        fuse_args.argc$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle argv$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("argv"));
    public static VarHandle argv$VH() {
        return fuse_args.argv$VH;
    }
    public static MemorySegment argv$get(MemorySegment seg) {
        return (java.lang.foreign.MemorySegment)fuse_args.argv$VH.get(seg);
    }
    public static void argv$set( MemorySegment seg, MemorySegment x) {
        fuse_args.argv$VH.set(seg, x);
    }
    public static MemorySegment argv$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemorySegment)fuse_args.argv$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void argv$set(MemorySegment seg, long index, MemorySegment x) {
        fuse_args.argv$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle allocated$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("allocated"));
    public static VarHandle allocated$VH() {
        return fuse_args.allocated$VH;
    }
    public static int allocated$get(MemorySegment seg) {
        return (int)fuse_args.allocated$VH.get(seg);
    }
    public static void allocated$set( MemorySegment seg, int x) {
        fuse_args.allocated$VH.set(seg, x);
    }
    public static int allocated$get(MemorySegment seg, long index) {
        return (int)fuse_args.allocated$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void allocated$set(MemorySegment seg, long index, int x) {
        fuse_args.allocated$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, SegmentScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


