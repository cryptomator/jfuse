// Generated by jextract

package org.cryptomator.jfuse.linux.amd64.extr.fuse3_lowlevel;

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
 * struct fuse_cmdline_opts {
 *     int singlethread;
 *     int foreground;
 *     int debug;
 *     int nodefault_subtype;
 *     char *mountpoint;
 *     int show_version;
 *     int show_help;
 *     int clone_fd;
 *     unsigned int max_idle_threads;
 *     unsigned int max_threads;
 * }
 * }
 */
public class fuse_cmdline_opts {

    fuse_cmdline_opts() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        fuse_lowlevel_h.C_INT.withName("singlethread"),
        fuse_lowlevel_h.C_INT.withName("foreground"),
        fuse_lowlevel_h.C_INT.withName("debug"),
        fuse_lowlevel_h.C_INT.withName("nodefault_subtype"),
        fuse_lowlevel_h.C_POINTER.withName("mountpoint"),
        fuse_lowlevel_h.C_INT.withName("show_version"),
        fuse_lowlevel_h.C_INT.withName("show_help"),
        fuse_lowlevel_h.C_INT.withName("clone_fd"),
        fuse_lowlevel_h.C_INT.withName("max_idle_threads"),
        fuse_lowlevel_h.C_INT.withName("max_threads"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse_cmdline_opts");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt singlethread$LAYOUT = (OfInt)$LAYOUT.select(groupElement("singlethread"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int singlethread
     * }
     */
    public static final OfInt singlethread$layout() {
        return singlethread$LAYOUT;
    }

    private static final long singlethread$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int singlethread
     * }
     */
    public static final long singlethread$offset() {
        return singlethread$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int singlethread
     * }
     */
    public static int singlethread(MemorySegment struct) {
        return struct.get(singlethread$LAYOUT, singlethread$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int singlethread
     * }
     */
    public static void singlethread(MemorySegment struct, int fieldValue) {
        struct.set(singlethread$LAYOUT, singlethread$OFFSET, fieldValue);
    }

    private static final OfInt foreground$LAYOUT = (OfInt)$LAYOUT.select(groupElement("foreground"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int foreground
     * }
     */
    public static final OfInt foreground$layout() {
        return foreground$LAYOUT;
    }

    private static final long foreground$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int foreground
     * }
     */
    public static final long foreground$offset() {
        return foreground$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int foreground
     * }
     */
    public static int foreground(MemorySegment struct) {
        return struct.get(foreground$LAYOUT, foreground$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int foreground
     * }
     */
    public static void foreground(MemorySegment struct, int fieldValue) {
        struct.set(foreground$LAYOUT, foreground$OFFSET, fieldValue);
    }

    private static final OfInt debug$LAYOUT = (OfInt)$LAYOUT.select(groupElement("debug"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int debug
     * }
     */
    public static final OfInt debug$layout() {
        return debug$LAYOUT;
    }

    private static final long debug$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int debug
     * }
     */
    public static final long debug$offset() {
        return debug$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int debug
     * }
     */
    public static int debug(MemorySegment struct) {
        return struct.get(debug$LAYOUT, debug$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int debug
     * }
     */
    public static void debug(MemorySegment struct, int fieldValue) {
        struct.set(debug$LAYOUT, debug$OFFSET, fieldValue);
    }

    private static final OfInt nodefault_subtype$LAYOUT = (OfInt)$LAYOUT.select(groupElement("nodefault_subtype"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int nodefault_subtype
     * }
     */
    public static final OfInt nodefault_subtype$layout() {
        return nodefault_subtype$LAYOUT;
    }

    private static final long nodefault_subtype$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int nodefault_subtype
     * }
     */
    public static final long nodefault_subtype$offset() {
        return nodefault_subtype$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int nodefault_subtype
     * }
     */
    public static int nodefault_subtype(MemorySegment struct) {
        return struct.get(nodefault_subtype$LAYOUT, nodefault_subtype$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int nodefault_subtype
     * }
     */
    public static void nodefault_subtype(MemorySegment struct, int fieldValue) {
        struct.set(nodefault_subtype$LAYOUT, nodefault_subtype$OFFSET, fieldValue);
    }

    private static final AddressLayout mountpoint$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("mountpoint"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *mountpoint
     * }
     */
    public static final AddressLayout mountpoint$layout() {
        return mountpoint$LAYOUT;
    }

    private static final long mountpoint$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *mountpoint
     * }
     */
    public static final long mountpoint$offset() {
        return mountpoint$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *mountpoint
     * }
     */
    public static MemorySegment mountpoint(MemorySegment struct) {
        return struct.get(mountpoint$LAYOUT, mountpoint$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *mountpoint
     * }
     */
    public static void mountpoint(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(mountpoint$LAYOUT, mountpoint$OFFSET, fieldValue);
    }

    private static final OfInt show_version$LAYOUT = (OfInt)$LAYOUT.select(groupElement("show_version"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int show_version
     * }
     */
    public static final OfInt show_version$layout() {
        return show_version$LAYOUT;
    }

    private static final long show_version$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int show_version
     * }
     */
    public static final long show_version$offset() {
        return show_version$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int show_version
     * }
     */
    public static int show_version(MemorySegment struct) {
        return struct.get(show_version$LAYOUT, show_version$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int show_version
     * }
     */
    public static void show_version(MemorySegment struct, int fieldValue) {
        struct.set(show_version$LAYOUT, show_version$OFFSET, fieldValue);
    }

    private static final OfInt show_help$LAYOUT = (OfInt)$LAYOUT.select(groupElement("show_help"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int show_help
     * }
     */
    public static final OfInt show_help$layout() {
        return show_help$LAYOUT;
    }

    private static final long show_help$OFFSET = 28;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int show_help
     * }
     */
    public static final long show_help$offset() {
        return show_help$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int show_help
     * }
     */
    public static int show_help(MemorySegment struct) {
        return struct.get(show_help$LAYOUT, show_help$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int show_help
     * }
     */
    public static void show_help(MemorySegment struct, int fieldValue) {
        struct.set(show_help$LAYOUT, show_help$OFFSET, fieldValue);
    }

    private static final OfInt clone_fd$LAYOUT = (OfInt)$LAYOUT.select(groupElement("clone_fd"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int clone_fd
     * }
     */
    public static final OfInt clone_fd$layout() {
        return clone_fd$LAYOUT;
    }

    private static final long clone_fd$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int clone_fd
     * }
     */
    public static final long clone_fd$offset() {
        return clone_fd$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int clone_fd
     * }
     */
    public static int clone_fd(MemorySegment struct) {
        return struct.get(clone_fd$LAYOUT, clone_fd$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int clone_fd
     * }
     */
    public static void clone_fd(MemorySegment struct, int fieldValue) {
        struct.set(clone_fd$LAYOUT, clone_fd$OFFSET, fieldValue);
    }

    private static final OfInt max_idle_threads$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_idle_threads"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_idle_threads
     * }
     */
    public static final OfInt max_idle_threads$layout() {
        return max_idle_threads$LAYOUT;
    }

    private static final long max_idle_threads$OFFSET = 36;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_idle_threads
     * }
     */
    public static final long max_idle_threads$offset() {
        return max_idle_threads$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_idle_threads
     * }
     */
    public static int max_idle_threads(MemorySegment struct) {
        return struct.get(max_idle_threads$LAYOUT, max_idle_threads$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_idle_threads
     * }
     */
    public static void max_idle_threads(MemorySegment struct, int fieldValue) {
        struct.set(max_idle_threads$LAYOUT, max_idle_threads$OFFSET, fieldValue);
    }

    private static final OfInt max_threads$LAYOUT = (OfInt)$LAYOUT.select(groupElement("max_threads"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int max_threads
     * }
     */
    public static final OfInt max_threads$layout() {
        return max_threads$LAYOUT;
    }

    private static final long max_threads$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int max_threads
     * }
     */
    public static final long max_threads$offset() {
        return max_threads$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int max_threads
     * }
     */
    public static int max_threads(MemorySegment struct) {
        return struct.get(max_threads$LAYOUT, max_threads$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int max_threads
     * }
     */
    public static void max_threads(MemorySegment struct, int fieldValue) {
        struct.set(max_threads$LAYOUT, max_threads$OFFSET, fieldValue);
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

