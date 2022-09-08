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
    public static MethodHandle fuse_lib_help$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_lib_help$MH,"fuse_lib_help");
    }
    public static void fuse_lib_help ( Addressable args) {
        var mh$ = fuse_lib_help$MH();
        try {
            mh$.invokeExact(args);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_new$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_new$MH,"fuse_new");
    }
    public static MemoryAddress fuse_new ( Addressable args,  Addressable ops,  long opsize,  Addressable data) {
        var mh$ = fuse_new$MH();
        try {
            return (java.lang.foreign.MemoryAddress)mh$.invokeExact(args, ops, opsize, data);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_destroy$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_destroy$MH,"fuse_destroy");
    }
    public static void fuse_destroy ( Addressable f) {
        var mh$ = fuse_destroy$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_mount$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_mount$MH,"fuse_mount");
    }
    public static int fuse_mount ( Addressable f,  Addressable mountpoint) {
        var mh$ = fuse_mount$MH();
        try {
            return (int)mh$.invokeExact(f, mountpoint);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_unmount$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_unmount$MH,"fuse_unmount");
    }
    public static void fuse_unmount ( Addressable f) {
        var mh$ = fuse_unmount$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_loop$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_loop$MH,"fuse_loop");
    }
    public static int fuse_loop ( Addressable f) {
        var mh$ = fuse_loop$MH();
        try {
            return (int)mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_loop_mt$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_loop_mt$MH,"fuse_loop_mt");
    }
    public static int fuse_loop_mt ( Addressable f,  Addressable config) {
        var mh$ = fuse_loop_mt$MH();
        try {
            return (int)mh$.invokeExact(f, config);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_exit$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_exit$MH,"fuse_exit");
    }
    public static void fuse_exit ( Addressable f) {
        var mh$ = fuse_exit$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_get_session$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_get_session$MH,"fuse_get_session");
    }
    public static MemoryAddress fuse_get_session ( Addressable f) {
        var mh$ = fuse_get_session$MH();
        try {
            return (java.lang.foreign.MemoryAddress)mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}


