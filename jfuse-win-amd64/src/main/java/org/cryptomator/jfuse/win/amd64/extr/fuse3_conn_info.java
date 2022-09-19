// Generated by jextract

package org.cryptomator.jfuse.win.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class fuse3_conn_info {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("proto_major"),
        Constants$root.C_LONG$LAYOUT.withName("proto_minor"),
        Constants$root.C_LONG$LAYOUT.withName("max_write"),
        Constants$root.C_LONG$LAYOUT.withName("max_read"),
        Constants$root.C_LONG$LAYOUT.withName("max_readahead"),
        Constants$root.C_LONG$LAYOUT.withName("capable"),
        Constants$root.C_LONG$LAYOUT.withName("want"),
        Constants$root.C_LONG$LAYOUT.withName("max_background"),
        Constants$root.C_LONG$LAYOUT.withName("congestion_threshold"),
        Constants$root.C_LONG$LAYOUT.withName("time_gran"),
        MemoryLayout.sequenceLayout(22, Constants$root.C_LONG$LAYOUT).withName("reserved")
    ).withName("fuse3_conn_info");
    public static MemoryLayout $LAYOUT() {
        return fuse3_conn_info.$struct$LAYOUT;
    }
    static final VarHandle proto_major$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("proto_major"));
    public static VarHandle proto_major$VH() {
        return fuse3_conn_info.proto_major$VH;
    }
    public static int proto_major$get(MemorySegment seg) {
        return (int)fuse3_conn_info.proto_major$VH.get(seg);
    }
    public static void proto_major$set( MemorySegment seg, int x) {
        fuse3_conn_info.proto_major$VH.set(seg, x);
    }
    public static int proto_major$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.proto_major$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void proto_major$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.proto_major$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle proto_minor$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("proto_minor"));
    public static VarHandle proto_minor$VH() {
        return fuse3_conn_info.proto_minor$VH;
    }
    public static int proto_minor$get(MemorySegment seg) {
        return (int)fuse3_conn_info.proto_minor$VH.get(seg);
    }
    public static void proto_minor$set( MemorySegment seg, int x) {
        fuse3_conn_info.proto_minor$VH.set(seg, x);
    }
    public static int proto_minor$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.proto_minor$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void proto_minor$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.proto_minor$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle max_write$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("max_write"));
    public static VarHandle max_write$VH() {
        return fuse3_conn_info.max_write$VH;
    }
    public static int max_write$get(MemorySegment seg) {
        return (int)fuse3_conn_info.max_write$VH.get(seg);
    }
    public static void max_write$set( MemorySegment seg, int x) {
        fuse3_conn_info.max_write$VH.set(seg, x);
    }
    public static int max_write$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.max_write$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void max_write$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.max_write$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle max_read$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("max_read"));
    public static VarHandle max_read$VH() {
        return fuse3_conn_info.max_read$VH;
    }
    public static int max_read$get(MemorySegment seg) {
        return (int)fuse3_conn_info.max_read$VH.get(seg);
    }
    public static void max_read$set( MemorySegment seg, int x) {
        fuse3_conn_info.max_read$VH.set(seg, x);
    }
    public static int max_read$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.max_read$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void max_read$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.max_read$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle max_readahead$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("max_readahead"));
    public static VarHandle max_readahead$VH() {
        return fuse3_conn_info.max_readahead$VH;
    }
    public static int max_readahead$get(MemorySegment seg) {
        return (int)fuse3_conn_info.max_readahead$VH.get(seg);
    }
    public static void max_readahead$set( MemorySegment seg, int x) {
        fuse3_conn_info.max_readahead$VH.set(seg, x);
    }
    public static int max_readahead$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.max_readahead$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void max_readahead$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.max_readahead$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle capable$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("capable"));
    public static VarHandle capable$VH() {
        return fuse3_conn_info.capable$VH;
    }
    public static int capable$get(MemorySegment seg) {
        return (int)fuse3_conn_info.capable$VH.get(seg);
    }
    public static void capable$set( MemorySegment seg, int x) {
        fuse3_conn_info.capable$VH.set(seg, x);
    }
    public static int capable$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.capable$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void capable$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.capable$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle want$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("want"));
    public static VarHandle want$VH() {
        return fuse3_conn_info.want$VH;
    }
    public static int want$get(MemorySegment seg) {
        return (int)fuse3_conn_info.want$VH.get(seg);
    }
    public static void want$set( MemorySegment seg, int x) {
        fuse3_conn_info.want$VH.set(seg, x);
    }
    public static int want$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.want$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void want$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.want$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle max_background$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("max_background"));
    public static VarHandle max_background$VH() {
        return fuse3_conn_info.max_background$VH;
    }
    public static int max_background$get(MemorySegment seg) {
        return (int)fuse3_conn_info.max_background$VH.get(seg);
    }
    public static void max_background$set( MemorySegment seg, int x) {
        fuse3_conn_info.max_background$VH.set(seg, x);
    }
    public static int max_background$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.max_background$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void max_background$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.max_background$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle congestion_threshold$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("congestion_threshold"));
    public static VarHandle congestion_threshold$VH() {
        return fuse3_conn_info.congestion_threshold$VH;
    }
    public static int congestion_threshold$get(MemorySegment seg) {
        return (int)fuse3_conn_info.congestion_threshold$VH.get(seg);
    }
    public static void congestion_threshold$set( MemorySegment seg, int x) {
        fuse3_conn_info.congestion_threshold$VH.set(seg, x);
    }
    public static int congestion_threshold$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.congestion_threshold$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void congestion_threshold$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.congestion_threshold$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle time_gran$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("time_gran"));
    public static VarHandle time_gran$VH() {
        return fuse3_conn_info.time_gran$VH;
    }
    public static int time_gran$get(MemorySegment seg) {
        return (int)fuse3_conn_info.time_gran$VH.get(seg);
    }
    public static void time_gran$set( MemorySegment seg, int x) {
        fuse3_conn_info.time_gran$VH.set(seg, x);
    }
    public static int time_gran$get(MemorySegment seg, long index) {
        return (int)fuse3_conn_info.time_gran$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void time_gran$set(MemorySegment seg, long index, int x) {
        fuse3_conn_info.time_gran$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment reserved$slice(MemorySegment seg) {
        return seg.asSlice(40, 88);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


