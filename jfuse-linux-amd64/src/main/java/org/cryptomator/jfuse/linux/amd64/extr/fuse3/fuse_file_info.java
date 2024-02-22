// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct fuse_file_info {
 *     int flags;
 *     unsigned int writepage : 1;
 *     unsigned int direct_io : 1;
 *     unsigned int keep_cache : 1;
 *     unsigned int flush : 1;
 *     unsigned int nonseekable : 1;
 *     unsigned int flock_release : 1;
 *     unsigned int cache_readdir : 1;
 *     unsigned int noflush : 1;
 *     unsigned int padding : 24;
 *     unsigned int padding2 : 32;
 *     uint64_t fh;
 *     uint64_t lock_owner;
 *     uint32_t poll_events;
 * }
 * }
 */
public class fuse_file_info {

    fuse_file_info() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        fuse_h.C_INT.withName("flags"),
        MemoryLayout.paddingLayout(12),
        fuse_h.C_LONG.withName("fh"),
        fuse_h.C_LONG.withName("lock_owner"),
        fuse_h.C_INT.withName("poll_events"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse_file_info");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt flags$LAYOUT = (OfInt)$LAYOUT.select(groupElement("flags"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int flags
     * }
     */
    public static final OfInt flags$layout() {
        return flags$LAYOUT;
    }

    private static final long flags$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int flags
     * }
     */
    public static final long flags$offset() {
        return flags$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int flags
     * }
     */
    public static int flags(MemorySegment struct) {
        return struct.get(flags$LAYOUT, flags$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int flags
     * }
     */
    public static void flags(MemorySegment struct, int fieldValue) {
        struct.set(flags$LAYOUT, flags$OFFSET, fieldValue);
    }

    private static final OfLong fh$LAYOUT = (OfLong)$LAYOUT.select(groupElement("fh"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint64_t fh
     * }
     */
    public static final OfLong fh$layout() {
        return fh$LAYOUT;
    }

    private static final long fh$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint64_t fh
     * }
     */
    public static final long fh$offset() {
        return fh$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint64_t fh
     * }
     */
    public static long fh(MemorySegment struct) {
        return struct.get(fh$LAYOUT, fh$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint64_t fh
     * }
     */
    public static void fh(MemorySegment struct, long fieldValue) {
        struct.set(fh$LAYOUT, fh$OFFSET, fieldValue);
    }

    private static final OfLong lock_owner$LAYOUT = (OfLong)$LAYOUT.select(groupElement("lock_owner"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint64_t lock_owner
     * }
     */
    public static final OfLong lock_owner$layout() {
        return lock_owner$LAYOUT;
    }

    private static final long lock_owner$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint64_t lock_owner
     * }
     */
    public static final long lock_owner$offset() {
        return lock_owner$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint64_t lock_owner
     * }
     */
    public static long lock_owner(MemorySegment struct) {
        return struct.get(lock_owner$LAYOUT, lock_owner$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint64_t lock_owner
     * }
     */
    public static void lock_owner(MemorySegment struct, long fieldValue) {
        struct.set(lock_owner$LAYOUT, lock_owner$OFFSET, fieldValue);
    }

    private static final OfInt poll_events$LAYOUT = (OfInt)$LAYOUT.select(groupElement("poll_events"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t poll_events
     * }
     */
    public static final OfInt poll_events$layout() {
        return poll_events$LAYOUT;
    }

    private static final long poll_events$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t poll_events
     * }
     */
    public static final long poll_events$offset() {
        return poll_events$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t poll_events
     * }
     */
    public static int poll_events(MemorySegment struct) {
        return struct.get(poll_events$LAYOUT, poll_events$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t poll_events
     * }
     */
    public static void poll_events(MemorySegment struct, int fieldValue) {
        struct.set(poll_events$LAYOUT, poll_events$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

