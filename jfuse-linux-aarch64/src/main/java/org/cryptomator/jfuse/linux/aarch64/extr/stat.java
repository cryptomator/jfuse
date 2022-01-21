// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
public class stat {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_dev"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_ino"),
        Constants$root.C_INT$LAYOUT.withName("st_mode"),
        Constants$root.C_INT$LAYOUT.withName("st_nlink"),
        Constants$root.C_INT$LAYOUT.withName("st_uid"),
        Constants$root.C_INT$LAYOUT.withName("st_gid"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_rdev"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("__pad1"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_size"),
        Constants$root.C_INT$LAYOUT.withName("st_blksize"),
        Constants$root.C_INT$LAYOUT.withName("__pad2"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_blocks"),
        MemoryLayout.structLayout(
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_sec"),
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_nsec")
        ).withName("st_atim"),
        MemoryLayout.structLayout(
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_sec"),
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_nsec")
        ).withName("st_mtim"),
        MemoryLayout.structLayout(
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_sec"),
            Constants$root.C_LONG_LONG$LAYOUT.withName("tv_nsec")
        ).withName("st_ctim"),
        MemoryLayout.sequenceLayout(2, Constants$root.C_INT$LAYOUT).withName("__glibc_reserved")
    ).withName("stat");
    public static MemoryLayout $LAYOUT() {
        return stat.$struct$LAYOUT;
    }
    static final VarHandle st_dev$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_dev"));
    public static VarHandle st_dev$VH() {
        return stat.st_dev$VH;
    }
    public static long st_dev$get(MemorySegment seg) {
        return (long)stat.st_dev$VH.get(seg);
    }
    public static void st_dev$set( MemorySegment seg, long x) {
        stat.st_dev$VH.set(seg, x);
    }
    public static long st_dev$get(MemorySegment seg, long index) {
        return (long)stat.st_dev$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_dev$set(MemorySegment seg, long index, long x) {
        stat.st_dev$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_ino$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_ino"));
    public static VarHandle st_ino$VH() {
        return stat.st_ino$VH;
    }
    public static long st_ino$get(MemorySegment seg) {
        return (long)stat.st_ino$VH.get(seg);
    }
    public static void st_ino$set( MemorySegment seg, long x) {
        stat.st_ino$VH.set(seg, x);
    }
    public static long st_ino$get(MemorySegment seg, long index) {
        return (long)stat.st_ino$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_ino$set(MemorySegment seg, long index, long x) {
        stat.st_ino$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_mode$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_mode"));
    public static VarHandle st_mode$VH() {
        return stat.st_mode$VH;
    }
    public static int st_mode$get(MemorySegment seg) {
        return (int)stat.st_mode$VH.get(seg);
    }
    public static void st_mode$set( MemorySegment seg, int x) {
        stat.st_mode$VH.set(seg, x);
    }
    public static int st_mode$get(MemorySegment seg, long index) {
        return (int)stat.st_mode$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_mode$set(MemorySegment seg, long index, int x) {
        stat.st_mode$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_nlink$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_nlink"));
    public static VarHandle st_nlink$VH() {
        return stat.st_nlink$VH;
    }
    public static int st_nlink$get(MemorySegment seg) {
        return (int)stat.st_nlink$VH.get(seg);
    }
    public static void st_nlink$set( MemorySegment seg, int x) {
        stat.st_nlink$VH.set(seg, x);
    }
    public static int st_nlink$get(MemorySegment seg, long index) {
        return (int)stat.st_nlink$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_nlink$set(MemorySegment seg, long index, int x) {
        stat.st_nlink$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_uid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_uid"));
    public static VarHandle st_uid$VH() {
        return stat.st_uid$VH;
    }
    public static int st_uid$get(MemorySegment seg) {
        return (int)stat.st_uid$VH.get(seg);
    }
    public static void st_uid$set( MemorySegment seg, int x) {
        stat.st_uid$VH.set(seg, x);
    }
    public static int st_uid$get(MemorySegment seg, long index) {
        return (int)stat.st_uid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_uid$set(MemorySegment seg, long index, int x) {
        stat.st_uid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_gid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_gid"));
    public static VarHandle st_gid$VH() {
        return stat.st_gid$VH;
    }
    public static int st_gid$get(MemorySegment seg) {
        return (int)stat.st_gid$VH.get(seg);
    }
    public static void st_gid$set( MemorySegment seg, int x) {
        stat.st_gid$VH.set(seg, x);
    }
    public static int st_gid$get(MemorySegment seg, long index) {
        return (int)stat.st_gid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_gid$set(MemorySegment seg, long index, int x) {
        stat.st_gid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_rdev$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_rdev"));
    public static VarHandle st_rdev$VH() {
        return stat.st_rdev$VH;
    }
    public static long st_rdev$get(MemorySegment seg) {
        return (long)stat.st_rdev$VH.get(seg);
    }
    public static void st_rdev$set( MemorySegment seg, long x) {
        stat.st_rdev$VH.set(seg, x);
    }
    public static long st_rdev$get(MemorySegment seg, long index) {
        return (long)stat.st_rdev$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_rdev$set(MemorySegment seg, long index, long x) {
        stat.st_rdev$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle __pad1$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("__pad1"));
    public static VarHandle __pad1$VH() {
        return stat.__pad1$VH;
    }
    public static long __pad1$get(MemorySegment seg) {
        return (long)stat.__pad1$VH.get(seg);
    }
    public static void __pad1$set( MemorySegment seg, long x) {
        stat.__pad1$VH.set(seg, x);
    }
    public static long __pad1$get(MemorySegment seg, long index) {
        return (long)stat.__pad1$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void __pad1$set(MemorySegment seg, long index, long x) {
        stat.__pad1$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_size$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_size"));
    public static VarHandle st_size$VH() {
        return stat.st_size$VH;
    }
    public static long st_size$get(MemorySegment seg) {
        return (long)stat.st_size$VH.get(seg);
    }
    public static void st_size$set( MemorySegment seg, long x) {
        stat.st_size$VH.set(seg, x);
    }
    public static long st_size$get(MemorySegment seg, long index) {
        return (long)stat.st_size$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_size$set(MemorySegment seg, long index, long x) {
        stat.st_size$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_blksize$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_blksize"));
    public static VarHandle st_blksize$VH() {
        return stat.st_blksize$VH;
    }
    public static int st_blksize$get(MemorySegment seg) {
        return (int)stat.st_blksize$VH.get(seg);
    }
    public static void st_blksize$set( MemorySegment seg, int x) {
        stat.st_blksize$VH.set(seg, x);
    }
    public static int st_blksize$get(MemorySegment seg, long index) {
        return (int)stat.st_blksize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_blksize$set(MemorySegment seg, long index, int x) {
        stat.st_blksize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle __pad2$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("__pad2"));
    public static VarHandle __pad2$VH() {
        return stat.__pad2$VH;
    }
    public static int __pad2$get(MemorySegment seg) {
        return (int)stat.__pad2$VH.get(seg);
    }
    public static void __pad2$set( MemorySegment seg, int x) {
        stat.__pad2$VH.set(seg, x);
    }
    public static int __pad2$get(MemorySegment seg, long index) {
        return (int)stat.__pad2$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void __pad2$set(MemorySegment seg, long index, int x) {
        stat.__pad2$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_blocks$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_blocks"));
    public static VarHandle st_blocks$VH() {
        return stat.st_blocks$VH;
    }
    public static long st_blocks$get(MemorySegment seg) {
        return (long)stat.st_blocks$VH.get(seg);
    }
    public static void st_blocks$set( MemorySegment seg, long x) {
        stat.st_blocks$VH.set(seg, x);
    }
    public static long st_blocks$get(MemorySegment seg, long index) {
        return (long)stat.st_blocks$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_blocks$set(MemorySegment seg, long index, long x) {
        stat.st_blocks$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment st_atim$slice(MemorySegment seg) {
        return seg.asSlice(72, 16);
    }
    public static MemorySegment st_mtim$slice(MemorySegment seg) {
        return seg.asSlice(88, 16);
    }
    public static MemorySegment st_ctim$slice(MemorySegment seg) {
        return seg.asSlice(104, 16);
    }
    public static MemorySegment __glibc_reserved$slice(MemorySegment seg) {
        return seg.asSlice(120, 8);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment allocate(ResourceScope scope) { return allocate(SegmentAllocator.nativeAllocator(scope)); }
    public static MemorySegment allocateArray(int len, ResourceScope scope) {
        return allocateArray(len, SegmentAllocator.nativeAllocator(scope));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, ResourceScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


