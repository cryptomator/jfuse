// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * struct stat {
 *     unsigned long st_dev;
 *     unsigned long st_ino;
 *     unsigned long st_nlink;
 *     unsigned int st_mode;
 *     unsigned int st_uid;
 *     unsigned int st_gid;
 *     int __pad0;
 *     unsigned long st_rdev;
 *     long st_size;
 *     long st_blksize;
 *     long st_blocks;
 *     struct timespec st_atim;
 *     struct timespec st_mtim;
 *     struct timespec st_ctim;
 *     long __glibc_reserved[3];
 * };
 * }
 */
public class stat {

    public static MemoryLayout $LAYOUT() {
        return constants$5.const$5;
    }
    public static VarHandle st_dev$VH() {
        return constants$6.const$0;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long st_dev;
     * }
     */
    public static long st_dev$get(MemorySegment seg) {
        return (long)constants$6.const$0.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long st_dev;
     * }
     */
    public static void st_dev$set(MemorySegment seg, long x) {
        constants$6.const$0.set(seg, x);
    }
    public static long st_dev$get(MemorySegment seg, long index) {
        return (long)constants$6.const$0.get(seg.asSlice(index*sizeof()));
    }
    public static void st_dev$set(MemorySegment seg, long index, long x) {
        constants$6.const$0.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_ino$VH() {
        return constants$6.const$1;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long st_ino;
     * }
     */
    public static long st_ino$get(MemorySegment seg) {
        return (long)constants$6.const$1.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long st_ino;
     * }
     */
    public static void st_ino$set(MemorySegment seg, long x) {
        constants$6.const$1.set(seg, x);
    }
    public static long st_ino$get(MemorySegment seg, long index) {
        return (long)constants$6.const$1.get(seg.asSlice(index*sizeof()));
    }
    public static void st_ino$set(MemorySegment seg, long index, long x) {
        constants$6.const$1.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_nlink$VH() {
        return constants$6.const$2;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long st_nlink;
     * }
     */
    public static long st_nlink$get(MemorySegment seg) {
        return (long)constants$6.const$2.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long st_nlink;
     * }
     */
    public static void st_nlink$set(MemorySegment seg, long x) {
        constants$6.const$2.set(seg, x);
    }
    public static long st_nlink$get(MemorySegment seg, long index) {
        return (long)constants$6.const$2.get(seg.asSlice(index*sizeof()));
    }
    public static void st_nlink$set(MemorySegment seg, long index, long x) {
        constants$6.const$2.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_mode$VH() {
        return constants$6.const$3;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int st_mode;
     * }
     */
    public static int st_mode$get(MemorySegment seg) {
        return (int)constants$6.const$3.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int st_mode;
     * }
     */
    public static void st_mode$set(MemorySegment seg, int x) {
        constants$6.const$3.set(seg, x);
    }
    public static int st_mode$get(MemorySegment seg, long index) {
        return (int)constants$6.const$3.get(seg.asSlice(index*sizeof()));
    }
    public static void st_mode$set(MemorySegment seg, long index, int x) {
        constants$6.const$3.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_uid$VH() {
        return constants$6.const$4;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int st_uid;
     * }
     */
    public static int st_uid$get(MemorySegment seg) {
        return (int)constants$6.const$4.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int st_uid;
     * }
     */
    public static void st_uid$set(MemorySegment seg, int x) {
        constants$6.const$4.set(seg, x);
    }
    public static int st_uid$get(MemorySegment seg, long index) {
        return (int)constants$6.const$4.get(seg.asSlice(index*sizeof()));
    }
    public static void st_uid$set(MemorySegment seg, long index, int x) {
        constants$6.const$4.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_gid$VH() {
        return constants$6.const$5;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int st_gid;
     * }
     */
    public static int st_gid$get(MemorySegment seg) {
        return (int)constants$6.const$5.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int st_gid;
     * }
     */
    public static void st_gid$set(MemorySegment seg, int x) {
        constants$6.const$5.set(seg, x);
    }
    public static int st_gid$get(MemorySegment seg, long index) {
        return (int)constants$6.const$5.get(seg.asSlice(index*sizeof()));
    }
    public static void st_gid$set(MemorySegment seg, long index, int x) {
        constants$6.const$5.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle __pad0$VH() {
        return constants$7.const$0;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int __pad0;
     * }
     */
    public static int __pad0$get(MemorySegment seg) {
        return (int)constants$7.const$0.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int __pad0;
     * }
     */
    public static void __pad0$set(MemorySegment seg, int x) {
        constants$7.const$0.set(seg, x);
    }
    public static int __pad0$get(MemorySegment seg, long index) {
        return (int)constants$7.const$0.get(seg.asSlice(index*sizeof()));
    }
    public static void __pad0$set(MemorySegment seg, long index, int x) {
        constants$7.const$0.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_rdev$VH() {
        return constants$7.const$1;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned long st_rdev;
     * }
     */
    public static long st_rdev$get(MemorySegment seg) {
        return (long)constants$7.const$1.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned long st_rdev;
     * }
     */
    public static void st_rdev$set(MemorySegment seg, long x) {
        constants$7.const$1.set(seg, x);
    }
    public static long st_rdev$get(MemorySegment seg, long index) {
        return (long)constants$7.const$1.get(seg.asSlice(index*sizeof()));
    }
    public static void st_rdev$set(MemorySegment seg, long index, long x) {
        constants$7.const$1.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_size$VH() {
        return constants$7.const$2;
    }
    /**
     * Getter for field:
     * {@snippet :
     * long st_size;
     * }
     */
    public static long st_size$get(MemorySegment seg) {
        return (long)constants$7.const$2.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * long st_size;
     * }
     */
    public static void st_size$set(MemorySegment seg, long x) {
        constants$7.const$2.set(seg, x);
    }
    public static long st_size$get(MemorySegment seg, long index) {
        return (long)constants$7.const$2.get(seg.asSlice(index*sizeof()));
    }
    public static void st_size$set(MemorySegment seg, long index, long x) {
        constants$7.const$2.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_blksize$VH() {
        return constants$7.const$3;
    }
    /**
     * Getter for field:
     * {@snippet :
     * long st_blksize;
     * }
     */
    public static long st_blksize$get(MemorySegment seg) {
        return (long)constants$7.const$3.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * long st_blksize;
     * }
     */
    public static void st_blksize$set(MemorySegment seg, long x) {
        constants$7.const$3.set(seg, x);
    }
    public static long st_blksize$get(MemorySegment seg, long index) {
        return (long)constants$7.const$3.get(seg.asSlice(index*sizeof()));
    }
    public static void st_blksize$set(MemorySegment seg, long index, long x) {
        constants$7.const$3.set(seg.asSlice(index*sizeof()), x);
    }
    public static VarHandle st_blocks$VH() {
        return constants$7.const$4;
    }
    /**
     * Getter for field:
     * {@snippet :
     * long st_blocks;
     * }
     */
    public static long st_blocks$get(MemorySegment seg) {
        return (long)constants$7.const$4.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * long st_blocks;
     * }
     */
    public static void st_blocks$set(MemorySegment seg, long x) {
        constants$7.const$4.set(seg, x);
    }
    public static long st_blocks$get(MemorySegment seg, long index) {
        return (long)constants$7.const$4.get(seg.asSlice(index*sizeof()));
    }
    public static void st_blocks$set(MemorySegment seg, long index, long x) {
        constants$7.const$4.set(seg.asSlice(index*sizeof()), x);
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
        return seg.asSlice(120, 24);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(long len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, Arena scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}

