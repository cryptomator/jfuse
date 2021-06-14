// Generated by jextract

package de.skymatic.fusepanama.lowlevel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public class statvfs {

    static final MemoryLayout $struct$LAYOUT = MemoryLayout.structLayout(
        C_LONG.withName("f_bsize"),
        C_LONG.withName("f_frsize"),
        C_INT.withName("f_blocks"),
        C_INT.withName("f_bfree"),
        C_INT.withName("f_bavail"),
        C_INT.withName("f_files"),
        C_INT.withName("f_ffree"),
        C_INT.withName("f_favail"),
        C_LONG.withName("f_fsid"),
        C_LONG.withName("f_flag"),
        C_LONG.withName("f_namemax")
    ).withName("statvfs");
    public static MemoryLayout $LAYOUT() {
        return statvfs.$struct$LAYOUT;
    }
    static final VarHandle f_bsize$VH = $struct$LAYOUT.varHandle(long.class, MemoryLayout.PathElement.groupElement("f_bsize"));
    public static VarHandle f_bsize$VH() {
        return statvfs.f_bsize$VH;
    }
    public static long f_bsize$get(MemorySegment seg) {
        return (long)statvfs.f_bsize$VH.get(seg);
    }
    public static void f_bsize$set( MemorySegment seg, long x) {
        statvfs.f_bsize$VH.set(seg, x);
    }
    public static long f_bsize$get(MemorySegment seg, long index) {
        return (long)statvfs.f_bsize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_bsize$set(MemorySegment seg, long index, long x) {
        statvfs.f_bsize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_frsize$VH = $struct$LAYOUT.varHandle(long.class, MemoryLayout.PathElement.groupElement("f_frsize"));
    public static VarHandle f_frsize$VH() {
        return statvfs.f_frsize$VH;
    }
    public static long f_frsize$get(MemorySegment seg) {
        return (long)statvfs.f_frsize$VH.get(seg);
    }
    public static void f_frsize$set( MemorySegment seg, long x) {
        statvfs.f_frsize$VH.set(seg, x);
    }
    public static long f_frsize$get(MemorySegment seg, long index) {
        return (long)statvfs.f_frsize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_frsize$set(MemorySegment seg, long index, long x) {
        statvfs.f_frsize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_blocks$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_blocks"));
    public static VarHandle f_blocks$VH() {
        return statvfs.f_blocks$VH;
    }
    public static int f_blocks$get(MemorySegment seg) {
        return (int)statvfs.f_blocks$VH.get(seg);
    }
    public static void f_blocks$set( MemorySegment seg, int x) {
        statvfs.f_blocks$VH.set(seg, x);
    }
    public static int f_blocks$get(MemorySegment seg, long index) {
        return (int)statvfs.f_blocks$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_blocks$set(MemorySegment seg, long index, int x) {
        statvfs.f_blocks$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_bfree$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_bfree"));
    public static VarHandle f_bfree$VH() {
        return statvfs.f_bfree$VH;
    }
    public static int f_bfree$get(MemorySegment seg) {
        return (int)statvfs.f_bfree$VH.get(seg);
    }
    public static void f_bfree$set( MemorySegment seg, int x) {
        statvfs.f_bfree$VH.set(seg, x);
    }
    public static int f_bfree$get(MemorySegment seg, long index) {
        return (int)statvfs.f_bfree$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_bfree$set(MemorySegment seg, long index, int x) {
        statvfs.f_bfree$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_bavail$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_bavail"));
    public static VarHandle f_bavail$VH() {
        return statvfs.f_bavail$VH;
    }
    public static int f_bavail$get(MemorySegment seg) {
        return (int)statvfs.f_bavail$VH.get(seg);
    }
    public static void f_bavail$set( MemorySegment seg, int x) {
        statvfs.f_bavail$VH.set(seg, x);
    }
    public static int f_bavail$get(MemorySegment seg, long index) {
        return (int)statvfs.f_bavail$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_bavail$set(MemorySegment seg, long index, int x) {
        statvfs.f_bavail$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_files$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_files"));
    public static VarHandle f_files$VH() {
        return statvfs.f_files$VH;
    }
    public static int f_files$get(MemorySegment seg) {
        return (int)statvfs.f_files$VH.get(seg);
    }
    public static void f_files$set( MemorySegment seg, int x) {
        statvfs.f_files$VH.set(seg, x);
    }
    public static int f_files$get(MemorySegment seg, long index) {
        return (int)statvfs.f_files$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_files$set(MemorySegment seg, long index, int x) {
        statvfs.f_files$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_ffree$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_ffree"));
    public static VarHandle f_ffree$VH() {
        return statvfs.f_ffree$VH;
    }
    public static int f_ffree$get(MemorySegment seg) {
        return (int)statvfs.f_ffree$VH.get(seg);
    }
    public static void f_ffree$set( MemorySegment seg, int x) {
        statvfs.f_ffree$VH.set(seg, x);
    }
    public static int f_ffree$get(MemorySegment seg, long index) {
        return (int)statvfs.f_ffree$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_ffree$set(MemorySegment seg, long index, int x) {
        statvfs.f_ffree$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_favail$VH = $struct$LAYOUT.varHandle(int.class, MemoryLayout.PathElement.groupElement("f_favail"));
    public static VarHandle f_favail$VH() {
        return statvfs.f_favail$VH;
    }
    public static int f_favail$get(MemorySegment seg) {
        return (int)statvfs.f_favail$VH.get(seg);
    }
    public static void f_favail$set( MemorySegment seg, int x) {
        statvfs.f_favail$VH.set(seg, x);
    }
    public static int f_favail$get(MemorySegment seg, long index) {
        return (int)statvfs.f_favail$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_favail$set(MemorySegment seg, long index, int x) {
        statvfs.f_favail$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_fsid$VH = $struct$LAYOUT.varHandle(long.class, MemoryLayout.PathElement.groupElement("f_fsid"));
    public static VarHandle f_fsid$VH() {
        return statvfs.f_fsid$VH;
    }
    public static long f_fsid$get(MemorySegment seg) {
        return (long)statvfs.f_fsid$VH.get(seg);
    }
    public static void f_fsid$set( MemorySegment seg, long x) {
        statvfs.f_fsid$VH.set(seg, x);
    }
    public static long f_fsid$get(MemorySegment seg, long index) {
        return (long)statvfs.f_fsid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_fsid$set(MemorySegment seg, long index, long x) {
        statvfs.f_fsid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_flag$VH = $struct$LAYOUT.varHandle(long.class, MemoryLayout.PathElement.groupElement("f_flag"));
    public static VarHandle f_flag$VH() {
        return statvfs.f_flag$VH;
    }
    public static long f_flag$get(MemorySegment seg) {
        return (long)statvfs.f_flag$VH.get(seg);
    }
    public static void f_flag$set( MemorySegment seg, long x) {
        statvfs.f_flag$VH.set(seg, x);
    }
    public static long f_flag$get(MemorySegment seg, long index) {
        return (long)statvfs.f_flag$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_flag$set(MemorySegment seg, long index, long x) {
        statvfs.f_flag$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle f_namemax$VH = $struct$LAYOUT.varHandle(long.class, MemoryLayout.PathElement.groupElement("f_namemax"));
    public static VarHandle f_namemax$VH() {
        return statvfs.f_namemax$VH;
    }
    public static long f_namemax$get(MemorySegment seg) {
        return (long)statvfs.f_namemax$VH.get(seg);
    }
    public static void f_namemax$set( MemorySegment seg, long x) {
        statvfs.f_namemax$VH.set(seg, x);
    }
    public static long f_namemax$get(MemorySegment seg, long index) {
        return (long)statvfs.f_namemax$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void f_namemax$set(MemorySegment seg, long index, long x) {
        statvfs.f_namemax$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocate(ResourceScope scope) { return allocate(SegmentAllocator.ofScope(scope)); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment allocateArray(int len, ResourceScope scope) {
        return allocateArray(len, SegmentAllocator.ofScope(scope));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, ResourceScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


