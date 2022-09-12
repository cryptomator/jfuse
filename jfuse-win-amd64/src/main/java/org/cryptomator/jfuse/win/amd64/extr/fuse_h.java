// Generated by jextract

package org.cryptomator.jfuse.win.amd64.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class fuse_h  {

    /* package-private */ fuse_h() {}
    public static OfByte C_CHAR = Constants$root.C_CHAR$LAYOUT;
    public static OfShort C_SHORT = Constants$root.C_SHORT$LAYOUT;
    public static OfInt C_INT = Constants$root.C_LONG$LAYOUT;
    public static OfInt C_LONG = Constants$root.C_LONG$LAYOUT;
    public static OfLong C_LONG_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfFloat C_FLOAT = Constants$root.C_FLOAT$LAYOUT;
    public static OfDouble C_DOUBLE = Constants$root.C_DOUBLE$LAYOUT;
    public static OfAddress C_POINTER = Constants$root.C_POINTER$LAYOUT;
    public static int FUSE_READDIR_PLUS() {
        return (int)1L;
    }
    public static int FUSE_FILL_DIR_PLUS() {
        return (int)2L;
    }
    public static MethodHandle fuse3_lib_help$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse3_lib_help$MH,"fuse3_lib_help");
    }
    public static void fuse3_lib_help ( Addressable args) {
        var mh$ = fuse3_lib_help$MH();
        try {
            mh$.invokeExact(args);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_new$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse3_new$MH,"fuse3_new");
    }
    public static MemoryAddress fuse3_new ( Addressable args,  Addressable ops,  long opsize,  Addressable data) {
        var mh$ = fuse3_new$MH();
        try {
            return (java.lang.foreign.MemoryAddress)mh$.invokeExact(args, ops, opsize, data);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_destroy$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse3_destroy$MH,"fuse3_destroy");
    }
    public static void fuse3_destroy ( Addressable f) {
        var mh$ = fuse3_destroy$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_mount$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse3_mount$MH,"fuse3_mount");
    }
    public static int fuse3_mount ( Addressable f,  Addressable mountpoint) {
        var mh$ = fuse3_mount$MH();
        try {
            return (int)mh$.invokeExact(f, mountpoint);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_unmount$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse3_unmount$MH,"fuse3_unmount");
    }
    public static void fuse3_unmount ( Addressable f) {
        var mh$ = fuse3_unmount$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_loop$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse3_loop$MH,"fuse3_loop");
    }
    public static int fuse3_loop ( Addressable f) {
        var mh$ = fuse3_loop$MH();
        try {
            return (int)mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_loop_mt_31$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse3_loop_mt_31$MH,"fuse3_loop_mt_31");
    }
    public static int fuse3_loop_mt_31 ( Addressable f,  int clone_fd) {
        var mh$ = fuse3_loop_mt_31$MH();
        try {
            return (int)mh$.invokeExact(f, clone_fd);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_exit$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse3_exit$MH,"fuse3_exit");
    }
    public static void fuse3_exit ( Addressable f) {
        var mh$ = fuse3_exit$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse3_get_session$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse3_get_session$MH,"fuse3_get_session");
    }
    public static MemoryAddress fuse3_get_session ( Addressable f) {
        var mh$ = fuse3_get_session$MH();
        try {
            return (java.lang.foreign.MemoryAddress)mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}


