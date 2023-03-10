// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
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
 *     char* modules;
 *     int debug;
 * };
 * }
 */
public class fuse_config {

    static final StructLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_INT$LAYOUT.withName("set_gid"),
        Constants$root.C_INT$LAYOUT.withName("gid"),
        Constants$root.C_INT$LAYOUT.withName("set_uid"),
        Constants$root.C_INT$LAYOUT.withName("uid"),
        Constants$root.C_INT$LAYOUT.withName("set_mode"),
        Constants$root.C_INT$LAYOUT.withName("umask"),
        Constants$root.C_DOUBLE$LAYOUT.withName("entry_timeout"),
        Constants$root.C_DOUBLE$LAYOUT.withName("negative_timeout"),
        Constants$root.C_DOUBLE$LAYOUT.withName("attr_timeout"),
        Constants$root.C_INT$LAYOUT.withName("intr"),
        Constants$root.C_INT$LAYOUT.withName("intr_signal"),
        Constants$root.C_INT$LAYOUT.withName("remember"),
        Constants$root.C_INT$LAYOUT.withName("hard_remove"),
        Constants$root.C_INT$LAYOUT.withName("use_ino"),
        Constants$root.C_INT$LAYOUT.withName("readdir_ino"),
        Constants$root.C_INT$LAYOUT.withName("direct_io"),
        Constants$root.C_INT$LAYOUT.withName("kernel_cache"),
        Constants$root.C_INT$LAYOUT.withName("auto_cache"),
        Constants$root.C_INT$LAYOUT.withName("no_rofd_flush"),
        Constants$root.C_INT$LAYOUT.withName("ac_attr_timeout_set"),
        MemoryLayout.paddingLayout(32),
        Constants$root.C_DOUBLE$LAYOUT.withName("ac_attr_timeout"),
        Constants$root.C_INT$LAYOUT.withName("nullpath_ok"),
        Constants$root.C_INT$LAYOUT.withName("show_help"),
        Constants$root.C_POINTER$LAYOUT.withName("modules"),
        Constants$root.C_INT$LAYOUT.withName("debug"),
        MemoryLayout.paddingLayout(32)
    ).withName("fuse_config");
    public static MemoryLayout $LAYOUT() {
        return fuse_config.$struct$LAYOUT;
    }
    static final VarHandle set_gid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("set_gid"));
    public static VarHandle set_gid$VH() {
        return fuse_config.set_gid$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int set_gid;
     * }
     */
    public static int set_gid$get(MemorySegment seg) {
        return (int)fuse_config.set_gid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int set_gid;
     * }
     */
    public static void set_gid$set(MemorySegment seg, int x) {
        fuse_config.set_gid$VH.set(seg, x);
    }
    public static int set_gid$get(MemorySegment seg, long index) {
        return (int)fuse_config.set_gid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void set_gid$set(MemorySegment seg, long index, int x) {
        fuse_config.set_gid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle gid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("gid"));
    public static VarHandle gid$VH() {
        return fuse_config.gid$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int gid;
     * }
     */
    public static int gid$get(MemorySegment seg) {
        return (int)fuse_config.gid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int gid;
     * }
     */
    public static void gid$set(MemorySegment seg, int x) {
        fuse_config.gid$VH.set(seg, x);
    }
    public static int gid$get(MemorySegment seg, long index) {
        return (int)fuse_config.gid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void gid$set(MemorySegment seg, long index, int x) {
        fuse_config.gid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle set_uid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("set_uid"));
    public static VarHandle set_uid$VH() {
        return fuse_config.set_uid$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int set_uid;
     * }
     */
    public static int set_uid$get(MemorySegment seg) {
        return (int)fuse_config.set_uid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int set_uid;
     * }
     */
    public static void set_uid$set(MemorySegment seg, int x) {
        fuse_config.set_uid$VH.set(seg, x);
    }
    public static int set_uid$get(MemorySegment seg, long index) {
        return (int)fuse_config.set_uid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void set_uid$set(MemorySegment seg, long index, int x) {
        fuse_config.set_uid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle uid$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("uid"));
    public static VarHandle uid$VH() {
        return fuse_config.uid$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int uid;
     * }
     */
    public static int uid$get(MemorySegment seg) {
        return (int)fuse_config.uid$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int uid;
     * }
     */
    public static void uid$set(MemorySegment seg, int x) {
        fuse_config.uid$VH.set(seg, x);
    }
    public static int uid$get(MemorySegment seg, long index) {
        return (int)fuse_config.uid$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void uid$set(MemorySegment seg, long index, int x) {
        fuse_config.uid$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle set_mode$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("set_mode"));
    public static VarHandle set_mode$VH() {
        return fuse_config.set_mode$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int set_mode;
     * }
     */
    public static int set_mode$get(MemorySegment seg) {
        return (int)fuse_config.set_mode$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int set_mode;
     * }
     */
    public static void set_mode$set(MemorySegment seg, int x) {
        fuse_config.set_mode$VH.set(seg, x);
    }
    public static int set_mode$get(MemorySegment seg, long index) {
        return (int)fuse_config.set_mode$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void set_mode$set(MemorySegment seg, long index, int x) {
        fuse_config.set_mode$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle umask$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("umask"));
    public static VarHandle umask$VH() {
        return fuse_config.umask$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * unsigned int umask;
     * }
     */
    public static int umask$get(MemorySegment seg) {
        return (int)fuse_config.umask$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * unsigned int umask;
     * }
     */
    public static void umask$set(MemorySegment seg, int x) {
        fuse_config.umask$VH.set(seg, x);
    }
    public static int umask$get(MemorySegment seg, long index) {
        return (int)fuse_config.umask$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void umask$set(MemorySegment seg, long index, int x) {
        fuse_config.umask$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle entry_timeout$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("entry_timeout"));
    public static VarHandle entry_timeout$VH() {
        return fuse_config.entry_timeout$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * double entry_timeout;
     * }
     */
    public static double entry_timeout$get(MemorySegment seg) {
        return (double)fuse_config.entry_timeout$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * double entry_timeout;
     * }
     */
    public static void entry_timeout$set(MemorySegment seg, double x) {
        fuse_config.entry_timeout$VH.set(seg, x);
    }
    public static double entry_timeout$get(MemorySegment seg, long index) {
        return (double)fuse_config.entry_timeout$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void entry_timeout$set(MemorySegment seg, long index, double x) {
        fuse_config.entry_timeout$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle negative_timeout$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("negative_timeout"));
    public static VarHandle negative_timeout$VH() {
        return fuse_config.negative_timeout$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * double negative_timeout;
     * }
     */
    public static double negative_timeout$get(MemorySegment seg) {
        return (double)fuse_config.negative_timeout$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * double negative_timeout;
     * }
     */
    public static void negative_timeout$set(MemorySegment seg, double x) {
        fuse_config.negative_timeout$VH.set(seg, x);
    }
    public static double negative_timeout$get(MemorySegment seg, long index) {
        return (double)fuse_config.negative_timeout$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void negative_timeout$set(MemorySegment seg, long index, double x) {
        fuse_config.negative_timeout$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle attr_timeout$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("attr_timeout"));
    public static VarHandle attr_timeout$VH() {
        return fuse_config.attr_timeout$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * double attr_timeout;
     * }
     */
    public static double attr_timeout$get(MemorySegment seg) {
        return (double)fuse_config.attr_timeout$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * double attr_timeout;
     * }
     */
    public static void attr_timeout$set(MemorySegment seg, double x) {
        fuse_config.attr_timeout$VH.set(seg, x);
    }
    public static double attr_timeout$get(MemorySegment seg, long index) {
        return (double)fuse_config.attr_timeout$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void attr_timeout$set(MemorySegment seg, long index, double x) {
        fuse_config.attr_timeout$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle intr$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("intr"));
    public static VarHandle intr$VH() {
        return fuse_config.intr$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int intr;
     * }
     */
    public static int intr$get(MemorySegment seg) {
        return (int)fuse_config.intr$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int intr;
     * }
     */
    public static void intr$set(MemorySegment seg, int x) {
        fuse_config.intr$VH.set(seg, x);
    }
    public static int intr$get(MemorySegment seg, long index) {
        return (int)fuse_config.intr$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void intr$set(MemorySegment seg, long index, int x) {
        fuse_config.intr$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle intr_signal$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("intr_signal"));
    public static VarHandle intr_signal$VH() {
        return fuse_config.intr_signal$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int intr_signal;
     * }
     */
    public static int intr_signal$get(MemorySegment seg) {
        return (int)fuse_config.intr_signal$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int intr_signal;
     * }
     */
    public static void intr_signal$set(MemorySegment seg, int x) {
        fuse_config.intr_signal$VH.set(seg, x);
    }
    public static int intr_signal$get(MemorySegment seg, long index) {
        return (int)fuse_config.intr_signal$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void intr_signal$set(MemorySegment seg, long index, int x) {
        fuse_config.intr_signal$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle remember$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("remember"));
    public static VarHandle remember$VH() {
        return fuse_config.remember$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int remember;
     * }
     */
    public static int remember$get(MemorySegment seg) {
        return (int)fuse_config.remember$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int remember;
     * }
     */
    public static void remember$set(MemorySegment seg, int x) {
        fuse_config.remember$VH.set(seg, x);
    }
    public static int remember$get(MemorySegment seg, long index) {
        return (int)fuse_config.remember$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void remember$set(MemorySegment seg, long index, int x) {
        fuse_config.remember$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle hard_remove$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("hard_remove"));
    public static VarHandle hard_remove$VH() {
        return fuse_config.hard_remove$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int hard_remove;
     * }
     */
    public static int hard_remove$get(MemorySegment seg) {
        return (int)fuse_config.hard_remove$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int hard_remove;
     * }
     */
    public static void hard_remove$set(MemorySegment seg, int x) {
        fuse_config.hard_remove$VH.set(seg, x);
    }
    public static int hard_remove$get(MemorySegment seg, long index) {
        return (int)fuse_config.hard_remove$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void hard_remove$set(MemorySegment seg, long index, int x) {
        fuse_config.hard_remove$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle use_ino$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("use_ino"));
    public static VarHandle use_ino$VH() {
        return fuse_config.use_ino$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int use_ino;
     * }
     */
    public static int use_ino$get(MemorySegment seg) {
        return (int)fuse_config.use_ino$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int use_ino;
     * }
     */
    public static void use_ino$set(MemorySegment seg, int x) {
        fuse_config.use_ino$VH.set(seg, x);
    }
    public static int use_ino$get(MemorySegment seg, long index) {
        return (int)fuse_config.use_ino$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void use_ino$set(MemorySegment seg, long index, int x) {
        fuse_config.use_ino$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle readdir_ino$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("readdir_ino"));
    public static VarHandle readdir_ino$VH() {
        return fuse_config.readdir_ino$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int readdir_ino;
     * }
     */
    public static int readdir_ino$get(MemorySegment seg) {
        return (int)fuse_config.readdir_ino$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int readdir_ino;
     * }
     */
    public static void readdir_ino$set(MemorySegment seg, int x) {
        fuse_config.readdir_ino$VH.set(seg, x);
    }
    public static int readdir_ino$get(MemorySegment seg, long index) {
        return (int)fuse_config.readdir_ino$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void readdir_ino$set(MemorySegment seg, long index, int x) {
        fuse_config.readdir_ino$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle direct_io$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("direct_io"));
    public static VarHandle direct_io$VH() {
        return fuse_config.direct_io$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int direct_io;
     * }
     */
    public static int direct_io$get(MemorySegment seg) {
        return (int)fuse_config.direct_io$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int direct_io;
     * }
     */
    public static void direct_io$set(MemorySegment seg, int x) {
        fuse_config.direct_io$VH.set(seg, x);
    }
    public static int direct_io$get(MemorySegment seg, long index) {
        return (int)fuse_config.direct_io$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void direct_io$set(MemorySegment seg, long index, int x) {
        fuse_config.direct_io$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle kernel_cache$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("kernel_cache"));
    public static VarHandle kernel_cache$VH() {
        return fuse_config.kernel_cache$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int kernel_cache;
     * }
     */
    public static int kernel_cache$get(MemorySegment seg) {
        return (int)fuse_config.kernel_cache$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int kernel_cache;
     * }
     */
    public static void kernel_cache$set(MemorySegment seg, int x) {
        fuse_config.kernel_cache$VH.set(seg, x);
    }
    public static int kernel_cache$get(MemorySegment seg, long index) {
        return (int)fuse_config.kernel_cache$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void kernel_cache$set(MemorySegment seg, long index, int x) {
        fuse_config.kernel_cache$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle auto_cache$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("auto_cache"));
    public static VarHandle auto_cache$VH() {
        return fuse_config.auto_cache$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int auto_cache;
     * }
     */
    public static int auto_cache$get(MemorySegment seg) {
        return (int)fuse_config.auto_cache$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int auto_cache;
     * }
     */
    public static void auto_cache$set(MemorySegment seg, int x) {
        fuse_config.auto_cache$VH.set(seg, x);
    }
    public static int auto_cache$get(MemorySegment seg, long index) {
        return (int)fuse_config.auto_cache$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void auto_cache$set(MemorySegment seg, long index, int x) {
        fuse_config.auto_cache$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle no_rofd_flush$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("no_rofd_flush"));
    public static VarHandle no_rofd_flush$VH() {
        return fuse_config.no_rofd_flush$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int no_rofd_flush;
     * }
     */
    public static int no_rofd_flush$get(MemorySegment seg) {
        return (int)fuse_config.no_rofd_flush$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int no_rofd_flush;
     * }
     */
    public static void no_rofd_flush$set(MemorySegment seg, int x) {
        fuse_config.no_rofd_flush$VH.set(seg, x);
    }
    public static int no_rofd_flush$get(MemorySegment seg, long index) {
        return (int)fuse_config.no_rofd_flush$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void no_rofd_flush$set(MemorySegment seg, long index, int x) {
        fuse_config.no_rofd_flush$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ac_attr_timeout_set$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("ac_attr_timeout_set"));
    public static VarHandle ac_attr_timeout_set$VH() {
        return fuse_config.ac_attr_timeout_set$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int ac_attr_timeout_set;
     * }
     */
    public static int ac_attr_timeout_set$get(MemorySegment seg) {
        return (int)fuse_config.ac_attr_timeout_set$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int ac_attr_timeout_set;
     * }
     */
    public static void ac_attr_timeout_set$set(MemorySegment seg, int x) {
        fuse_config.ac_attr_timeout_set$VH.set(seg, x);
    }
    public static int ac_attr_timeout_set$get(MemorySegment seg, long index) {
        return (int)fuse_config.ac_attr_timeout_set$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ac_attr_timeout_set$set(MemorySegment seg, long index, int x) {
        fuse_config.ac_attr_timeout_set$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ac_attr_timeout$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("ac_attr_timeout"));
    public static VarHandle ac_attr_timeout$VH() {
        return fuse_config.ac_attr_timeout$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * double ac_attr_timeout;
     * }
     */
    public static double ac_attr_timeout$get(MemorySegment seg) {
        return (double)fuse_config.ac_attr_timeout$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * double ac_attr_timeout;
     * }
     */
    public static void ac_attr_timeout$set(MemorySegment seg, double x) {
        fuse_config.ac_attr_timeout$VH.set(seg, x);
    }
    public static double ac_attr_timeout$get(MemorySegment seg, long index) {
        return (double)fuse_config.ac_attr_timeout$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ac_attr_timeout$set(MemorySegment seg, long index, double x) {
        fuse_config.ac_attr_timeout$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle nullpath_ok$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("nullpath_ok"));
    public static VarHandle nullpath_ok$VH() {
        return fuse_config.nullpath_ok$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int nullpath_ok;
     * }
     */
    public static int nullpath_ok$get(MemorySegment seg) {
        return (int)fuse_config.nullpath_ok$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int nullpath_ok;
     * }
     */
    public static void nullpath_ok$set(MemorySegment seg, int x) {
        fuse_config.nullpath_ok$VH.set(seg, x);
    }
    public static int nullpath_ok$get(MemorySegment seg, long index) {
        return (int)fuse_config.nullpath_ok$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void nullpath_ok$set(MemorySegment seg, long index, int x) {
        fuse_config.nullpath_ok$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle show_help$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("show_help"));
    public static VarHandle show_help$VH() {
        return fuse_config.show_help$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int show_help;
     * }
     */
    public static int show_help$get(MemorySegment seg) {
        return (int)fuse_config.show_help$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int show_help;
     * }
     */
    public static void show_help$set(MemorySegment seg, int x) {
        fuse_config.show_help$VH.set(seg, x);
    }
    public static int show_help$get(MemorySegment seg, long index) {
        return (int)fuse_config.show_help$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void show_help$set(MemorySegment seg, long index, int x) {
        fuse_config.show_help$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle modules$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("modules"));
    public static VarHandle modules$VH() {
        return fuse_config.modules$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * char* modules;
     * }
     */
    public static MemorySegment modules$get(MemorySegment seg) {
        return (java.lang.foreign.MemorySegment)fuse_config.modules$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * char* modules;
     * }
     */
    public static void modules$set(MemorySegment seg, MemorySegment x) {
        fuse_config.modules$VH.set(seg, x);
    }
    public static MemorySegment modules$get(MemorySegment seg, long index) {
        return (java.lang.foreign.MemorySegment)fuse_config.modules$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void modules$set(MemorySegment seg, long index, MemorySegment x) {
        fuse_config.modules$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle debug$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("debug"));
    public static VarHandle debug$VH() {
        return fuse_config.debug$VH;
    }
    /**
     * Getter for field:
     * {@snippet :
     * int debug;
     * }
     */
    public static int debug$get(MemorySegment seg) {
        return (int)fuse_config.debug$VH.get(seg);
    }
    /**
     * Setter for field:
     * {@snippet :
     * int debug;
     * }
     */
    public static void debug$set(MemorySegment seg, int x) {
        fuse_config.debug$VH.set(seg, x);
    }
    public static int debug$get(MemorySegment seg, long index) {
        return (int)fuse_config.debug$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void debug$set(MemorySegment seg, long index, int x) {
        fuse_config.debug$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(long len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemorySegment addr, SegmentScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


