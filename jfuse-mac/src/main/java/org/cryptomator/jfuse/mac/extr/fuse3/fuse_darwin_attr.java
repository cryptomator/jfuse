package org.cryptomator.jfuse.mac.extr.fuse3;

import java.lang.foreign.*;
import java.util.function.Consumer;

import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct fuse_darwin_attr {
 *     ino_t ino;              // offset 0, 8 bytes
 *     mode_t mode;            // offset 8, 2 bytes
 *     nlink_t nlink;          // offset 10, 2 bytes
 *     uid_t uid;              // offset 12, 4 bytes
 *     gid_t gid;              // offset 16, 4 bytes
 *     dev_t rdev;             // offset 20, 4 bytes
 *     struct timespec atimespec;    // offset 24, 16 bytes
 *     struct timespec mtimespec;    // offset 40, 16 bytes
 *     struct timespec ctimespec;    // offset 56, 16 bytes
 *     struct timespec btimespec;    // offset 72, 16 bytes
 *     struct timespec bkuptimespec; // offset 88, 16 bytes
 *     off_t size;             // offset 104, 8 bytes
 *     blkcnt_t blocks;        // offset 112, 8 bytes
 *     blksize_t blksize;      // offset 120, 4 bytes
 *     unsigned int flags;     // offset 124, 4 bytes
 *     uint64_t reserved[8];   // offset 128, 64 bytes
 * }
 * }
 */
public class fuse_darwin_attr {

    fuse_darwin_attr() {}

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
            fuse_h.C_LONG_LONG.withName("ino"),
            fuse_h.C_SHORT.withName("mode"),
            fuse_h.C_SHORT.withName("nlink"),
            fuse_h.C_INT.withName("uid"),
            fuse_h.C_INT.withName("gid"),
            fuse_h.C_INT.withName("rdev"),
            timespec.layout().withName("atimespec"),
            timespec.layout().withName("mtimespec"),
            timespec.layout().withName("ctimespec"),
            timespec.layout().withName("btimespec"),
            timespec.layout().withName("bkuptimespec"),
            fuse_h.C_LONG_LONG.withName("size"),
            fuse_h.C_LONG_LONG.withName("blocks"),
            fuse_h.C_INT.withName("blksize"),
            fuse_h.C_INT.withName("flags"),
            MemoryLayout.sequenceLayout(8, fuse_h.C_LONG_LONG).withName("reserved")
    ).withName("fuse_darwin_attr");

    public static GroupLayout layout() {
        return $LAYOUT;
    }

    // ino: offset 0
    private static final long ino$OFFSET = 0;
    public static long ino(MemorySegment struct) {
        return struct.get(fuse_h.C_LONG_LONG, ino$OFFSET);
    }
    public static void ino(MemorySegment struct, long fieldValue) {
        struct.set(fuse_h.C_LONG_LONG, ino$OFFSET, fieldValue);
    }

    // mode: offset 8
    private static final long mode$OFFSET = 8;
    public static short mode(MemorySegment struct) {
        return struct.get(fuse_h.C_SHORT, mode$OFFSET);
    }
    public static void mode(MemorySegment struct, short fieldValue) {
        struct.set(fuse_h.C_SHORT, mode$OFFSET, fieldValue);
    }

    // nlink: offset 10
    private static final long nlink$OFFSET = 10;
    public static short nlink(MemorySegment struct) {
        return struct.get(fuse_h.C_SHORT, nlink$OFFSET);
    }
    public static void nlink(MemorySegment struct, short fieldValue) {
        struct.set(fuse_h.C_SHORT, nlink$OFFSET, fieldValue);
    }

    // uid: offset 12
    private static final long uid$OFFSET = 12;
    public static int uid(MemorySegment struct) {
        return struct.get(fuse_h.C_INT, uid$OFFSET);
    }
    public static void uid(MemorySegment struct, int fieldValue) {
        struct.set(fuse_h.C_INT, uid$OFFSET, fieldValue);
    }

    // gid: offset 16
    private static final long gid$OFFSET = 16;
    public static int gid(MemorySegment struct) {
        return struct.get(fuse_h.C_INT, gid$OFFSET);
    }
    public static void gid(MemorySegment struct, int fieldValue) {
        struct.set(fuse_h.C_INT, gid$OFFSET, fieldValue);
    }

    // rdev: offset 20
    private static final long rdev$OFFSET = 20;
    public static int rdev(MemorySegment struct) {
        return struct.get(fuse_h.C_INT, rdev$OFFSET);
    }
    public static void rdev(MemorySegment struct, int fieldValue) {
        struct.set(fuse_h.C_INT, rdev$OFFSET, fieldValue);
    }

    // atimespec: offset 24
    private static final long atimespec$OFFSET = 24;
    public static MemorySegment atimespec(MemorySegment struct) {
        return struct.asSlice(atimespec$OFFSET, timespec.layout().byteSize());
    }
    public static void atimespec(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, atimespec$OFFSET, timespec.layout().byteSize());
    }

    // mtimespec: offset 40
    private static final long mtimespec$OFFSET = 40;
    public static MemorySegment mtimespec(MemorySegment struct) {
        return struct.asSlice(mtimespec$OFFSET, timespec.layout().byteSize());
    }
    public static void mtimespec(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, mtimespec$OFFSET, timespec.layout().byteSize());
    }

    // ctimespec: offset 56
    private static final long ctimespec$OFFSET = 56;
    public static MemorySegment ctimespec(MemorySegment struct) {
        return struct.asSlice(ctimespec$OFFSET, timespec.layout().byteSize());
    }
    public static void ctimespec(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, ctimespec$OFFSET, timespec.layout().byteSize());
    }

    // btimespec (birth time): offset 72
    private static final long btimespec$OFFSET = 72;
    public static MemorySegment btimespec(MemorySegment struct) {
        return struct.asSlice(btimespec$OFFSET, timespec.layout().byteSize());
    }
    public static void btimespec(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, btimespec$OFFSET, timespec.layout().byteSize());
    }

    // bkuptimespec (backup time): offset 88
    private static final long bkuptimespec$OFFSET = 88;
    public static MemorySegment bkuptimespec(MemorySegment struct) {
        return struct.asSlice(bkuptimespec$OFFSET, timespec.layout().byteSize());
    }
    public static void bkuptimespec(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, bkuptimespec$OFFSET, timespec.layout().byteSize());
    }

    // size: offset 104
    private static final long size$OFFSET = 104;
    public static long size(MemorySegment struct) {
        return struct.get(fuse_h.C_LONG_LONG, size$OFFSET);
    }
    public static void size(MemorySegment struct, long fieldValue) {
        struct.set(fuse_h.C_LONG_LONG, size$OFFSET, fieldValue);
    }

    // blocks: offset 112
    private static final long blocks$OFFSET = 112;
    public static long blocks(MemorySegment struct) {
        return struct.get(fuse_h.C_LONG_LONG, blocks$OFFSET);
    }
    public static void blocks(MemorySegment struct, long fieldValue) {
        struct.set(fuse_h.C_LONG_LONG, blocks$OFFSET, fieldValue);
    }

    // blksize: offset 120
    private static final long blksize$OFFSET = 120;
    public static int blksize(MemorySegment struct) {
        return struct.get(fuse_h.C_INT, blksize$OFFSET);
    }
    public static void blksize(MemorySegment struct, int fieldValue) {
        struct.set(fuse_h.C_INT, blksize$OFFSET, fieldValue);
    }

    // flags: offset 124
    private static final long flags$OFFSET = 124;
    public static int flags(MemorySegment struct) {
        return struct.get(fuse_h.C_INT, flags$OFFSET);
    }
    public static void flags(MemorySegment struct, int fieldValue) {
        struct.set(fuse_h.C_INT, flags$OFFSET, fieldValue);
    }

    public static long sizeof() { return layout().byteSize(); }

    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }
}
