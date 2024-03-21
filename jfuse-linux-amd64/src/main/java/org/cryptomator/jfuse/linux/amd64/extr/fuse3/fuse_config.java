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
 * struct fuse_config {
 *     int set_gid;
 *     unsigned int gid;
 *     int set_uid;
 *     unsigned int uid;
 *     int set_mode;
 *     unsigned int umask;
 *     double entry_timeout;
 *     double negative_timeout;
 *     double attr_timeout;
 *     int intr;
 *     int intr_signal;
 *     int remember;
 *     int hard_remove;
 *     int use_ino;
 *     int readdir_ino;
 *     int direct_io;
 *     int kernel_cache;
 *     int auto_cache;
 *     int no_rofd_flush;
 *     int ac_attr_timeout_set;
 *     double ac_attr_timeout;
 *     int nullpath_ok;
 *     int show_help;
 *     char *modules;
 *     int debug;
 * }
 * }
 */
public class fuse_config {

    fuse_config() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        fuse_h.C_INT.withName("set_gid"),
        fuse_h.C_INT.withName("gid"),
        fuse_h.C_INT.withName("set_uid"),
        fuse_h.C_INT.withName("uid"),
        fuse_h.C_INT.withName("set_mode"),
        fuse_h.C_INT.withName("umask"),
        fuse_h.C_DOUBLE.withName("entry_timeout"),
        fuse_h.C_DOUBLE.withName("negative_timeout"),
        fuse_h.C_DOUBLE.withName("attr_timeout"),
        fuse_h.C_INT.withName("intr"),
        fuse_h.C_INT.withName("intr_signal"),
        fuse_h.C_INT.withName("remember"),
        fuse_h.C_INT.withName("hard_remove"),
        fuse_h.C_INT.withName("use_ino"),
        fuse_h.C_INT.withName("readdir_ino"),
        fuse_h.C_INT.withName("direct_io"),
        fuse_h.C_INT.withName("kernel_cache"),
        fuse_h.C_INT.withName("auto_cache"),
        fuse_h.C_INT.withName("no_rofd_flush"),
        fuse_h.C_INT.withName("ac_attr_timeout_set"),
        MemoryLayout.paddingLayout(4),
        fuse_h.C_DOUBLE.withName("ac_attr_timeout"),
        fuse_h.C_INT.withName("nullpath_ok"),
        fuse_h.C_INT.withName("show_help"),
        fuse_h.C_POINTER.withName("modules"),
        fuse_h.C_INT.withName("debug"),
        MemoryLayout.paddingLayout(4)
    ).withName("fuse_config");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt set_gid$LAYOUT = (OfInt)$LAYOUT.select(groupElement("set_gid"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int set_gid
     * }
     */
    public static final OfInt set_gid$layout() {
        return set_gid$LAYOUT;
    }

    private static final long set_gid$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int set_gid
     * }
     */
    public static final long set_gid$offset() {
        return set_gid$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int set_gid
     * }
     */
    public static int set_gid(MemorySegment struct) {
        return struct.get(set_gid$LAYOUT, set_gid$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int set_gid
     * }
     */
    public static void set_gid(MemorySegment struct, int fieldValue) {
        struct.set(set_gid$LAYOUT, set_gid$OFFSET, fieldValue);
    }

    private static final OfInt gid$LAYOUT = (OfInt)$LAYOUT.select(groupElement("gid"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int gid
     * }
     */
    public static final OfInt gid$layout() {
        return gid$LAYOUT;
    }

    private static final long gid$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int gid
     * }
     */
    public static final long gid$offset() {
        return gid$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int gid
     * }
     */
    public static int gid(MemorySegment struct) {
        return struct.get(gid$LAYOUT, gid$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int gid
     * }
     */
    public static void gid(MemorySegment struct, int fieldValue) {
        struct.set(gid$LAYOUT, gid$OFFSET, fieldValue);
    }

    private static final OfInt set_uid$LAYOUT = (OfInt)$LAYOUT.select(groupElement("set_uid"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int set_uid
     * }
     */
    public static final OfInt set_uid$layout() {
        return set_uid$LAYOUT;
    }

    private static final long set_uid$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int set_uid
     * }
     */
    public static final long set_uid$offset() {
        return set_uid$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int set_uid
     * }
     */
    public static int set_uid(MemorySegment struct) {
        return struct.get(set_uid$LAYOUT, set_uid$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int set_uid
     * }
     */
    public static void set_uid(MemorySegment struct, int fieldValue) {
        struct.set(set_uid$LAYOUT, set_uid$OFFSET, fieldValue);
    }

    private static final OfInt uid$LAYOUT = (OfInt)$LAYOUT.select(groupElement("uid"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int uid
     * }
     */
    public static final OfInt uid$layout() {
        return uid$LAYOUT;
    }

    private static final long uid$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int uid
     * }
     */
    public static final long uid$offset() {
        return uid$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int uid
     * }
     */
    public static int uid(MemorySegment struct) {
        return struct.get(uid$LAYOUT, uid$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int uid
     * }
     */
    public static void uid(MemorySegment struct, int fieldValue) {
        struct.set(uid$LAYOUT, uid$OFFSET, fieldValue);
    }

    private static final OfInt set_mode$LAYOUT = (OfInt)$LAYOUT.select(groupElement("set_mode"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int set_mode
     * }
     */
    public static final OfInt set_mode$layout() {
        return set_mode$LAYOUT;
    }

    private static final long set_mode$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int set_mode
     * }
     */
    public static final long set_mode$offset() {
        return set_mode$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int set_mode
     * }
     */
    public static int set_mode(MemorySegment struct) {
        return struct.get(set_mode$LAYOUT, set_mode$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int set_mode
     * }
     */
    public static void set_mode(MemorySegment struct, int fieldValue) {
        struct.set(set_mode$LAYOUT, set_mode$OFFSET, fieldValue);
    }

    private static final OfInt umask$LAYOUT = (OfInt)$LAYOUT.select(groupElement("umask"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int umask
     * }
     */
    public static final OfInt umask$layout() {
        return umask$LAYOUT;
    }

    private static final long umask$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int umask
     * }
     */
    public static final long umask$offset() {
        return umask$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int umask
     * }
     */
    public static int umask(MemorySegment struct) {
        return struct.get(umask$LAYOUT, umask$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int umask
     * }
     */
    public static void umask(MemorySegment struct, int fieldValue) {
        struct.set(umask$LAYOUT, umask$OFFSET, fieldValue);
    }

    private static final OfDouble entry_timeout$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("entry_timeout"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double entry_timeout
     * }
     */
    public static final OfDouble entry_timeout$layout() {
        return entry_timeout$LAYOUT;
    }

    private static final long entry_timeout$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double entry_timeout
     * }
     */
    public static final long entry_timeout$offset() {
        return entry_timeout$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double entry_timeout
     * }
     */
    public static double entry_timeout(MemorySegment struct) {
        return struct.get(entry_timeout$LAYOUT, entry_timeout$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double entry_timeout
     * }
     */
    public static void entry_timeout(MemorySegment struct, double fieldValue) {
        struct.set(entry_timeout$LAYOUT, entry_timeout$OFFSET, fieldValue);
    }

    private static final OfDouble negative_timeout$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("negative_timeout"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double negative_timeout
     * }
     */
    public static final OfDouble negative_timeout$layout() {
        return negative_timeout$LAYOUT;
    }

    private static final long negative_timeout$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double negative_timeout
     * }
     */
    public static final long negative_timeout$offset() {
        return negative_timeout$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double negative_timeout
     * }
     */
    public static double negative_timeout(MemorySegment struct) {
        return struct.get(negative_timeout$LAYOUT, negative_timeout$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double negative_timeout
     * }
     */
    public static void negative_timeout(MemorySegment struct, double fieldValue) {
        struct.set(negative_timeout$LAYOUT, negative_timeout$OFFSET, fieldValue);
    }

    private static final OfDouble attr_timeout$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("attr_timeout"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double attr_timeout
     * }
     */
    public static final OfDouble attr_timeout$layout() {
        return attr_timeout$LAYOUT;
    }

    private static final long attr_timeout$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double attr_timeout
     * }
     */
    public static final long attr_timeout$offset() {
        return attr_timeout$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double attr_timeout
     * }
     */
    public static double attr_timeout(MemorySegment struct) {
        return struct.get(attr_timeout$LAYOUT, attr_timeout$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double attr_timeout
     * }
     */
    public static void attr_timeout(MemorySegment struct, double fieldValue) {
        struct.set(attr_timeout$LAYOUT, attr_timeout$OFFSET, fieldValue);
    }

    private static final OfInt intr$LAYOUT = (OfInt)$LAYOUT.select(groupElement("intr"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int intr
     * }
     */
    public static final OfInt intr$layout() {
        return intr$LAYOUT;
    }

    private static final long intr$OFFSET = 48;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int intr
     * }
     */
    public static final long intr$offset() {
        return intr$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int intr
     * }
     */
    public static int intr(MemorySegment struct) {
        return struct.get(intr$LAYOUT, intr$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int intr
     * }
     */
    public static void intr(MemorySegment struct, int fieldValue) {
        struct.set(intr$LAYOUT, intr$OFFSET, fieldValue);
    }

    private static final OfInt intr_signal$LAYOUT = (OfInt)$LAYOUT.select(groupElement("intr_signal"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int intr_signal
     * }
     */
    public static final OfInt intr_signal$layout() {
        return intr_signal$LAYOUT;
    }

    private static final long intr_signal$OFFSET = 52;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int intr_signal
     * }
     */
    public static final long intr_signal$offset() {
        return intr_signal$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int intr_signal
     * }
     */
    public static int intr_signal(MemorySegment struct) {
        return struct.get(intr_signal$LAYOUT, intr_signal$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int intr_signal
     * }
     */
    public static void intr_signal(MemorySegment struct, int fieldValue) {
        struct.set(intr_signal$LAYOUT, intr_signal$OFFSET, fieldValue);
    }

    private static final OfInt remember$LAYOUT = (OfInt)$LAYOUT.select(groupElement("remember"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int remember
     * }
     */
    public static final OfInt remember$layout() {
        return remember$LAYOUT;
    }

    private static final long remember$OFFSET = 56;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int remember
     * }
     */
    public static final long remember$offset() {
        return remember$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int remember
     * }
     */
    public static int remember(MemorySegment struct) {
        return struct.get(remember$LAYOUT, remember$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int remember
     * }
     */
    public static void remember(MemorySegment struct, int fieldValue) {
        struct.set(remember$LAYOUT, remember$OFFSET, fieldValue);
    }

    private static final OfInt hard_remove$LAYOUT = (OfInt)$LAYOUT.select(groupElement("hard_remove"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int hard_remove
     * }
     */
    public static final OfInt hard_remove$layout() {
        return hard_remove$LAYOUT;
    }

    private static final long hard_remove$OFFSET = 60;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int hard_remove
     * }
     */
    public static final long hard_remove$offset() {
        return hard_remove$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int hard_remove
     * }
     */
    public static int hard_remove(MemorySegment struct) {
        return struct.get(hard_remove$LAYOUT, hard_remove$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int hard_remove
     * }
     */
    public static void hard_remove(MemorySegment struct, int fieldValue) {
        struct.set(hard_remove$LAYOUT, hard_remove$OFFSET, fieldValue);
    }

    private static final OfInt use_ino$LAYOUT = (OfInt)$LAYOUT.select(groupElement("use_ino"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int use_ino
     * }
     */
    public static final OfInt use_ino$layout() {
        return use_ino$LAYOUT;
    }

    private static final long use_ino$OFFSET = 64;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int use_ino
     * }
     */
    public static final long use_ino$offset() {
        return use_ino$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int use_ino
     * }
     */
    public static int use_ino(MemorySegment struct) {
        return struct.get(use_ino$LAYOUT, use_ino$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int use_ino
     * }
     */
    public static void use_ino(MemorySegment struct, int fieldValue) {
        struct.set(use_ino$LAYOUT, use_ino$OFFSET, fieldValue);
    }

    private static final OfInt readdir_ino$LAYOUT = (OfInt)$LAYOUT.select(groupElement("readdir_ino"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int readdir_ino
     * }
     */
    public static final OfInt readdir_ino$layout() {
        return readdir_ino$LAYOUT;
    }

    private static final long readdir_ino$OFFSET = 68;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int readdir_ino
     * }
     */
    public static final long readdir_ino$offset() {
        return readdir_ino$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int readdir_ino
     * }
     */
    public static int readdir_ino(MemorySegment struct) {
        return struct.get(readdir_ino$LAYOUT, readdir_ino$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int readdir_ino
     * }
     */
    public static void readdir_ino(MemorySegment struct, int fieldValue) {
        struct.set(readdir_ino$LAYOUT, readdir_ino$OFFSET, fieldValue);
    }

    private static final OfInt direct_io$LAYOUT = (OfInt)$LAYOUT.select(groupElement("direct_io"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int direct_io
     * }
     */
    public static final OfInt direct_io$layout() {
        return direct_io$LAYOUT;
    }

    private static final long direct_io$OFFSET = 72;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int direct_io
     * }
     */
    public static final long direct_io$offset() {
        return direct_io$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int direct_io
     * }
     */
    public static int direct_io(MemorySegment struct) {
        return struct.get(direct_io$LAYOUT, direct_io$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int direct_io
     * }
     */
    public static void direct_io(MemorySegment struct, int fieldValue) {
        struct.set(direct_io$LAYOUT, direct_io$OFFSET, fieldValue);
    }

    private static final OfInt kernel_cache$LAYOUT = (OfInt)$LAYOUT.select(groupElement("kernel_cache"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int kernel_cache
     * }
     */
    public static final OfInt kernel_cache$layout() {
        return kernel_cache$LAYOUT;
    }

    private static final long kernel_cache$OFFSET = 76;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int kernel_cache
     * }
     */
    public static final long kernel_cache$offset() {
        return kernel_cache$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int kernel_cache
     * }
     */
    public static int kernel_cache(MemorySegment struct) {
        return struct.get(kernel_cache$LAYOUT, kernel_cache$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int kernel_cache
     * }
     */
    public static void kernel_cache(MemorySegment struct, int fieldValue) {
        struct.set(kernel_cache$LAYOUT, kernel_cache$OFFSET, fieldValue);
    }

    private static final OfInt auto_cache$LAYOUT = (OfInt)$LAYOUT.select(groupElement("auto_cache"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int auto_cache
     * }
     */
    public static final OfInt auto_cache$layout() {
        return auto_cache$LAYOUT;
    }

    private static final long auto_cache$OFFSET = 80;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int auto_cache
     * }
     */
    public static final long auto_cache$offset() {
        return auto_cache$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int auto_cache
     * }
     */
    public static int auto_cache(MemorySegment struct) {
        return struct.get(auto_cache$LAYOUT, auto_cache$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int auto_cache
     * }
     */
    public static void auto_cache(MemorySegment struct, int fieldValue) {
        struct.set(auto_cache$LAYOUT, auto_cache$OFFSET, fieldValue);
    }

    private static final OfInt no_rofd_flush$LAYOUT = (OfInt)$LAYOUT.select(groupElement("no_rofd_flush"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int no_rofd_flush
     * }
     */
    public static final OfInt no_rofd_flush$layout() {
        return no_rofd_flush$LAYOUT;
    }

    private static final long no_rofd_flush$OFFSET = 84;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int no_rofd_flush
     * }
     */
    public static final long no_rofd_flush$offset() {
        return no_rofd_flush$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int no_rofd_flush
     * }
     */
    public static int no_rofd_flush(MemorySegment struct) {
        return struct.get(no_rofd_flush$LAYOUT, no_rofd_flush$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int no_rofd_flush
     * }
     */
    public static void no_rofd_flush(MemorySegment struct, int fieldValue) {
        struct.set(no_rofd_flush$LAYOUT, no_rofd_flush$OFFSET, fieldValue);
    }

    private static final OfInt ac_attr_timeout_set$LAYOUT = (OfInt)$LAYOUT.select(groupElement("ac_attr_timeout_set"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int ac_attr_timeout_set
     * }
     */
    public static final OfInt ac_attr_timeout_set$layout() {
        return ac_attr_timeout_set$LAYOUT;
    }

    private static final long ac_attr_timeout_set$OFFSET = 88;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int ac_attr_timeout_set
     * }
     */
    public static final long ac_attr_timeout_set$offset() {
        return ac_attr_timeout_set$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int ac_attr_timeout_set
     * }
     */
    public static int ac_attr_timeout_set(MemorySegment struct) {
        return struct.get(ac_attr_timeout_set$LAYOUT, ac_attr_timeout_set$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int ac_attr_timeout_set
     * }
     */
    public static void ac_attr_timeout_set(MemorySegment struct, int fieldValue) {
        struct.set(ac_attr_timeout_set$LAYOUT, ac_attr_timeout_set$OFFSET, fieldValue);
    }

    private static final OfDouble ac_attr_timeout$LAYOUT = (OfDouble)$LAYOUT.select(groupElement("ac_attr_timeout"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * double ac_attr_timeout
     * }
     */
    public static final OfDouble ac_attr_timeout$layout() {
        return ac_attr_timeout$LAYOUT;
    }

    private static final long ac_attr_timeout$OFFSET = 96;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * double ac_attr_timeout
     * }
     */
    public static final long ac_attr_timeout$offset() {
        return ac_attr_timeout$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * double ac_attr_timeout
     * }
     */
    public static double ac_attr_timeout(MemorySegment struct) {
        return struct.get(ac_attr_timeout$LAYOUT, ac_attr_timeout$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * double ac_attr_timeout
     * }
     */
    public static void ac_attr_timeout(MemorySegment struct, double fieldValue) {
        struct.set(ac_attr_timeout$LAYOUT, ac_attr_timeout$OFFSET, fieldValue);
    }

    private static final OfInt nullpath_ok$LAYOUT = (OfInt)$LAYOUT.select(groupElement("nullpath_ok"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int nullpath_ok
     * }
     */
    public static final OfInt nullpath_ok$layout() {
        return nullpath_ok$LAYOUT;
    }

    private static final long nullpath_ok$OFFSET = 104;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int nullpath_ok
     * }
     */
    public static final long nullpath_ok$offset() {
        return nullpath_ok$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int nullpath_ok
     * }
     */
    public static int nullpath_ok(MemorySegment struct) {
        return struct.get(nullpath_ok$LAYOUT, nullpath_ok$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int nullpath_ok
     * }
     */
    public static void nullpath_ok(MemorySegment struct, int fieldValue) {
        struct.set(nullpath_ok$LAYOUT, nullpath_ok$OFFSET, fieldValue);
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

    private static final long show_help$OFFSET = 108;

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

    private static final AddressLayout modules$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("modules"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * char *modules
     * }
     */
    public static final AddressLayout modules$layout() {
        return modules$LAYOUT;
    }

    private static final long modules$OFFSET = 112;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * char *modules
     * }
     */
    public static final long modules$offset() {
        return modules$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * char *modules
     * }
     */
    public static MemorySegment modules(MemorySegment struct) {
        return struct.get(modules$LAYOUT, modules$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * char *modules
     * }
     */
    public static void modules(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(modules$LAYOUT, modules$OFFSET, fieldValue);
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

    private static final long debug$OFFSET = 120;

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

