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
 * struct fuse_args {
 *     int argc;
 *     char **argv;
 *     int allocated;
 * }
 * }
 */
public class fuse_args {

    fuse_args() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        fuse_h.C_INT.withName("argc"),
        MemoryLayout.paddingLayout(4),
        fuse_h.C_POINTER.withName("argv"),
        fuse_h.C_INT.withName("allocated"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse_args");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt argc$LAYOUT = (OfInt)$LAYOUT.select(groupElement("argc"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int argc
     * }
     */
    public static final OfInt argc$layout() {
        return argc$LAYOUT;
    }

    private static final long argc$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int argc
     * }
     */
    public static final long argc$offset() {
        return argc$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int argc
     * }
     */
    public static int argc(MemorySegment struct) {
        return struct.get(argc$LAYOUT, argc$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int argc
     * }
     */
    public static void argc(MemorySegment struct, int fieldValue) {
        struct.set(argc$LAYOUT, argc$OFFSET, fieldValue);
    }

    private static final AddressLayout argv$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("argv"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char **argv
     * }
     */
    public static final AddressLayout argv$layout() {
        return argv$LAYOUT;
    }

    private static final long argv$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char **argv
     * }
     */
    public static final long argv$offset() {
        return argv$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char **argv
     * }
     */
    public static MemorySegment argv(MemorySegment struct) {
        return struct.get(argv$LAYOUT, argv$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char **argv
     * }
     */
    public static void argv(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(argv$LAYOUT, argv$OFFSET, fieldValue);
    }

    private static final OfInt allocated$LAYOUT = (OfInt)$LAYOUT.select(groupElement("allocated"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int allocated
     * }
     */
    public static final OfInt allocated$layout() {
        return allocated$LAYOUT;
    }

    private static final long allocated$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int allocated
     * }
     */
    public static final long allocated$offset() {
        return allocated$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int allocated
     * }
     */
    public static int allocated(MemorySegment struct) {
        return struct.get(allocated$LAYOUT, allocated$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int allocated
     * }
     */
    public static void allocated(MemorySegment struct, int fieldValue) {
        struct.set(allocated$LAYOUT, allocated$OFFSET, fieldValue);
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
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

