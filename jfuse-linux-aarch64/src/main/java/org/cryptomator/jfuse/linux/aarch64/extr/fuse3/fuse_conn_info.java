// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

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
 * struct fuse_conn_info {
 *     unsigned int proto_major;
 *     unsigned int proto_minor;
 *     unsigned int max_write;
 *     unsigned int max_read;
 *     unsigned int max_readahead;
 *     unsigned int capable;
 *     unsigned int want;
 *     unsigned int max_background;
 *     unsigned int congestion_threshold;
 *     unsigned int time_gran;
 *     unsigned int reserved[22];
 * }
 * }
 */
public class fuse_conn_info {

    fuse_conn_info() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        fuse_h.C_INT.withName("proto_major"),
        fuse_h.C_INT.withName("proto_minor"),
        fuse_h.C_INT.withName("max_write"),
        fuse_h.C_INT.withName("max_read"),
        fuse_h.C_INT.withName("max_readahead"),
        fuse_h.C_INT.withName("capable"),
        fuse_h.C_INT.withName("want"),
        fuse_h.C_INT.withName("max_background"),
        fuse_h.C_INT.withName("congestion_threshold"),
        fuse_h.C_INT.withName("time_gran"),
        MemoryLayout.sequenceLayout(22, fuse_h.C_INT).withName("reserved")
    ).withName("fuse_conn_info");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt proto_major$LAYOUT = (OfInt)$LAYOUT.select(groupElement("proto_major"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int proto_major
     * }
     */
    public static final OfInt proto_major$layout() {
        return proto_major$LAYOUT;
    }

    private static final long proto_major$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int proto_major
     * }
     */
    public static final long proto_major$offset() {
        return proto_major$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int proto_major
     * }
     */
    public static int proto_major(MemorySegment struct) {
        return struct.get(proto_major$LAYOUT, proto_major$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int proto_major
     * }
     */
    public static void proto_major(MemorySegment struct, int fieldValue) {
        struct.set(proto_major$LAYOUT, proto_major$OFFSET, fieldValue);
    }

    private static final OfInt proto_minor$LAYOUT = (OfInt)$LAYOUT.select(groupElement("proto_minor"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int proto_minor
     * }
     */
    public static final OfInt proto_minor$layout() {
        return proto_minor$LAYOUT;
    }

    private static final long proto_minor$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int proto_minor
     * }
     */
    public static final long proto_minor$offset() {
        return proto_minor$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int proto_minor
     * }
     */
    public static int proto_minor(MemorySegment struct) {
        return struct.get(proto_minor$LAYOUT, proto_minor$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int proto_minor
     * }
     */
    public static void proto_minor(MemorySegment struct, int fieldValue) {
        struct.set(proto_minor$LAYOUT, proto_minor$OFFSET, fieldValue);
    }

    private static final OfInt max_write$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_write"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_write
     * }
     */
    public static final OfInt max_write$layout() {
        return max_write$LAYOUT;
    }

    private static final long max_write$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_write
     * }
     */
    public static final long max_write$offset() {
        return max_write$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_write
     * }
     */
    public static int max_write(MemorySegment struct) {
        return struct.get(max_write$LAYOUT, max_write$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_write
     * }
     */
    public static void max_write(MemorySegment struct, int fieldValue) {
        struct.set(max_write$LAYOUT, max_write$OFFSET, fieldValue);
    }

    private static final OfInt max_read$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_read"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_read
     * }
     */
    public static final OfInt max_read$layout() {
        return max_read$LAYOUT;
    }

    private static final long max_read$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_read
     * }
     */
    public static final long max_read$offset() {
        return max_read$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_read
     * }
     */
    public static int max_read(MemorySegment struct) {
        return struct.get(max_read$LAYOUT, max_read$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_read
     * }
     */
    public static void max_read(MemorySegment struct, int fieldValue) {
        struct.set(max_read$LAYOUT, max_read$OFFSET, fieldValue);
    }

    private static final OfInt max_readahead$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_readahead"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_readahead
     * }
     */
    public static final OfInt max_readahead$layout() {
        return max_readahead$LAYOUT;
    }

    private static final long max_readahead$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_readahead
     * }
     */
    public static final long max_readahead$offset() {
        return max_readahead$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_readahead
     * }
     */
    public static int max_readahead(MemorySegment struct) {
        return struct.get(max_readahead$LAYOUT, max_readahead$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_readahead
     * }
     */
    public static void max_readahead(MemorySegment struct, int fieldValue) {
        struct.set(max_readahead$LAYOUT, max_readahead$OFFSET, fieldValue);
    }

    private static final OfInt capable$LAYOUT = (OfInt)$LAYOUT.select(groupElement("capable"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int capable
     * }
     */
    public static final OfInt capable$layout() {
        return capable$LAYOUT;
    }

    private static final long capable$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int capable
     * }
     */
    public static final long capable$offset() {
        return capable$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int capable
     * }
     */
    public static int capable(MemorySegment struct) {
        return struct.get(capable$LAYOUT, capable$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int capable
     * }
     */
    public static void capable(MemorySegment struct, int fieldValue) {
        struct.set(capable$LAYOUT, capable$OFFSET, fieldValue);
    }

    private static final OfInt want$LAYOUT = (OfInt)$LAYOUT.select(groupElement("want"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int want
     * }
     */
    public static final OfInt want$layout() {
        return want$LAYOUT;
    }

    private static final long want$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int want
     * }
     */
    public static final long want$offset() {
        return want$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int want
     * }
     */
    public static int want(MemorySegment struct) {
        return struct.get(want$LAYOUT, want$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int want
     * }
     */
    public static void want(MemorySegment struct, int fieldValue) {
        struct.set(want$LAYOUT, want$OFFSET, fieldValue);
    }

    private static final OfInt max_background$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_background"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_background
     * }
     */
    public static final OfInt max_background$layout() {
        return max_background$LAYOUT;
    }

    private static final long max_background$OFFSET = 28;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_background
     * }
     */
    public static final long max_background$offset() {
        return max_background$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_background
     * }
     */
    public static int max_background(MemorySegment struct) {
        return struct.get(max_background$LAYOUT, max_background$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_background
     * }
     */
    public static void max_background(MemorySegment struct, int fieldValue) {
        struct.set(max_background$LAYOUT, max_background$OFFSET, fieldValue);
    }

    private static final OfInt congestion_threshold$LAYOUT = (OfInt)$LAYOUT.select(groupElement("congestion_threshold"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int congestion_threshold
     * }
     */
    public static final OfInt congestion_threshold$layout() {
        return congestion_threshold$LAYOUT;
    }

    private static final long congestion_threshold$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int congestion_threshold
     * }
     */
    public static final long congestion_threshold$offset() {
        return congestion_threshold$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int congestion_threshold
     * }
     */
    public static int congestion_threshold(MemorySegment struct) {
        return struct.get(congestion_threshold$LAYOUT, congestion_threshold$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int congestion_threshold
     * }
     */
    public static void congestion_threshold(MemorySegment struct, int fieldValue) {
        struct.set(congestion_threshold$LAYOUT, congestion_threshold$OFFSET, fieldValue);
    }

    private static final OfInt time_gran$LAYOUT = (OfInt)$LAYOUT.select(groupElement("time_gran"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int time_gran
     * }
     */
    public static final OfInt time_gran$layout() {
        return time_gran$LAYOUT;
    }

    private static final long time_gran$OFFSET = 36;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int time_gran
     * }
     */
    public static final long time_gran$offset() {
        return time_gran$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int time_gran
     * }
     */
    public static int time_gran(MemorySegment struct) {
        return struct.get(time_gran$LAYOUT, time_gran$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int time_gran
     * }
     */
    public static void time_gran(MemorySegment struct, int fieldValue) {
        struct.set(time_gran$LAYOUT, time_gran$OFFSET, fieldValue);
    }

    private static final SequenceLayout reserved$LAYOUT = (SequenceLayout)$LAYOUT.select(groupElement("reserved"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static final SequenceLayout reserved$layout() {
        return reserved$LAYOUT;
    }

    private static final long reserved$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static final long reserved$offset() {
        return reserved$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static MemorySegment reserved(MemorySegment struct) {
        return struct.asSlice(reserved$OFFSET, reserved$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static void reserved(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, reserved$OFFSET, reserved$LAYOUT.byteSize());
    }

    private static long[] reserved$DIMS = { 22 };

    /**
     * Dimensions for array field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static long[] reserved$dimensions() {
        return reserved$DIMS;
    }
    private static final VarHandle reserved$ELEM_HANDLE = reserved$LAYOUT.varHandle(sequenceElement());

    /**
     * Indexed getter for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static int reserved(MemorySegment struct, long index0) {
        return (int)reserved$ELEM_HANDLE.get(struct, 0L, index0);
    }

    /**
     * Indexed setter for field:
     * {@snippet lang=c :
     * unsigned int reserved[22]
     * }
     */
    public static void reserved(MemorySegment struct, long index0, int fieldValue) {
        reserved$ELEM_HANDLE.set(struct, 0L, index0, fieldValue);
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

