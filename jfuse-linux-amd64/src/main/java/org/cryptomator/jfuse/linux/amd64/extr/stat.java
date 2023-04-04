// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * struct stat {
 *     __dev_t st_dev;
 *     __ino_t st_ino;
 *     __nlink_t st_nlink;
 *     __mode_t st_mode;
 *     __uid_t st_uid;
 *     __gid_t st_gid;
 *     int __pad0;
 *     __dev_t st_rdev;
 *     __off_t st_size;
 *     __blksize_t st_blksize;
 *     __blkcnt_t st_blocks;
 *     struct timespec st_atim;
 *     struct timespec st_mtim;
 *     struct timespec st_ctim;
 *     __syscall_slong_t __glibc_reserved[3];
 * };
 * }
 */
public class stat {

    static final StructLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_dev"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_ino"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_nlink"),
        Constants$root.C_INT$LAYOUT.withName("st_mode"),
        Constants$root.C_INT$LAYOUT.withName("st_uid"),
        Constants$root.C_INT$LAYOUT.withName("st_gid"),
        Constants$root.C_INT$LAYOUT.withName("__pad0"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_rdev"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_size"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("st_blksize"),
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
        MemoryLayout.sequenceLayout(3, Constants$root.C_LONG_LONG$LAYOUT).withName("__glibc_reserved")
    ).withName("stat");
    public static MemoryLayout $LAYOUT() {
        return stat.$struct$LAYOUT;
    }
    static final VarHandle st_dev$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_dev"));
    public static VarHandle st_dev$VH() {
        return stat.st_dev$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __dev_t st_dev;
     * }
     */
    public static long st_dev$get(MemorySegment seg) {
        return (long)stat.st_dev$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __dev_t st_dev;
     * }
     */
    public static void st_dev$set(MemorySegment seg, long x) {
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
    /**
     * Getter for field:
     * {@snippet :
     * __ino_t st_ino;
     * }
     */
    public static long st_ino$get(MemorySegment seg) {
        return (long)stat.st_ino$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __ino_t st_ino;
     * }
     */
    public static void st_ino$set(MemorySegment seg, long x) {
        stat.st_ino$VH.set(seg, x);
    }
    public static long st_ino$get(MemorySegment seg, long index) {
        return (long)stat.st_ino$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_ino$set(MemorySegment seg, long index, long x) {
        stat.st_ino$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_nlink$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_nlink"));
    public static VarHandle st_nlink$VH() {
        return stat.st_nlink$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __nlink_t st_nlink;
     * }
     */
    public static long st_nlink$get(MemorySegment seg) {
        return (long)stat.st_nlink$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __nlink_t st_nlink;
     * }
     */
    public static void st_nlink$set(MemorySegment seg, long x) {
        stat.st_nlink$VH.set(seg, x);
    }
    public static long st_nlink$get(MemorySegment seg, long index) {
        return (long)stat.st_nlink$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_nlink$set(MemorySegment seg, long index, long x) {
        stat.st_nlink$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_mode$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_mode"));
    public static VarHandle st_mode$VH() {
        return stat.st_mode$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __mode_t st_mode;
     * }
     */
    public static int st_mode$get(MemorySegment seg) {
        return (int)stat.st_mode$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __mode_t st_mode;
     * }
     */
    public static void st_mode$set(MemorySegment seg, int x) {
        stat.st_mode$VH.set(seg, x);
    }
    public static int st_mode$get(MemorySegment seg, long index) {
        return (int)stat.st_mode$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_mode$set(MemorySegment seg, long index, int x) {
        stat.st_mode$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_uid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_uid"));
    public static VarHandle st_uid$VH() {
        return stat.st_uid$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __uid_t st_uid;
     * }
     */
    public static int st_uid$get(MemorySegment seg) {
        return (int)stat.st_uid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __uid_t st_uid;
     * }
     */
    public static void st_uid$set(MemorySegment seg, int x) {
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
    /**
     * Getter for field:
     * {@snippet :
     * __gid_t st_gid;
     * }
     */
    public static int st_gid$get(MemorySegment seg) {
        return (int)stat.st_gid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __gid_t st_gid;
     * }
     */
    public static void st_gid$set(MemorySegment seg, int x) {
        stat.st_gid$VH.set(seg, x);
    }
    public static int st_gid$get(MemorySegment seg, long index) {
        return (int)stat.st_gid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_gid$set(MemorySegment seg, long index, int x) {
        stat.st_gid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle __pad0$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("__pad0"));
    public static VarHandle __pad0$VH() {
        return stat.__pad0$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int __pad0;
     * }
     */
    public static int __pad0$get(MemorySegment seg) {
        return (int)stat.__pad0$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int __pad0;
     * }
     */
    public static void __pad0$set(MemorySegment seg, int x) {
        stat.__pad0$VH.set(seg, x);
    }
    public static int __pad0$get(MemorySegment seg, long index) {
        return (int)stat.__pad0$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void __pad0$set(MemorySegment seg, long index, int x) {
        stat.__pad0$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_rdev$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_rdev"));
    public static VarHandle st_rdev$VH() {
        return stat.st_rdev$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __dev_t st_rdev;
     * }
     */
    public static long st_rdev$get(MemorySegment seg) {
        return (long)stat.st_rdev$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __dev_t st_rdev;
     * }
     */
    public static void st_rdev$set(MemorySegment seg, long x) {
        stat.st_rdev$VH.set(seg, x);
    }
    public static long st_rdev$get(MemorySegment seg, long index) {
        return (long)stat.st_rdev$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_rdev$set(MemorySegment seg, long index, long x) {
        stat.st_rdev$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_size$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_size"));
    public static VarHandle st_size$VH() {
        return stat.st_size$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __off_t st_size;
     * }
     */
    public static long st_size$get(MemorySegment seg) {
        return (long)stat.st_size$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __off_t st_size;
     * }
     */
    public static void st_size$set(MemorySegment seg, long x) {
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
    /**
     * Getter for field:
     * {@snippet :
     * __blksize_t st_blksize;
     * }
     */
    public static long st_blksize$get(MemorySegment seg) {
        return (long)stat.st_blksize$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __blksize_t st_blksize;
     * }
     */
    public static void st_blksize$set(MemorySegment seg, long x) {
        stat.st_blksize$VH.set(seg, x);
    }
    public static long st_blksize$get(MemorySegment seg, long index) {
        return (long)stat.st_blksize$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void st_blksize$set(MemorySegment seg, long index, long x) {
        stat.st_blksize$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle st_blocks$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("st_blocks"));
    public static VarHandle st_blocks$VH() {
        return stat.st_blocks$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * __blkcnt_t st_blocks;
     * }
     */
    public static long st_blocks$get(MemorySegment seg) {
        return (long)stat.st_blocks$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * __blkcnt_t st_blocks;
     * }
     */
    public static void st_blocks$set(MemorySegment seg, long x) {
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
        return seg.asSlice(120, 24);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(long len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, SegmentScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


