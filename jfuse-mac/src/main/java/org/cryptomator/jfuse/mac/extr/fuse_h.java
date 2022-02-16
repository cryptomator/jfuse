// Generated by jextract

package org.cryptomator.jfuse.mac.extr;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
public class fuse_h  {

    /* package-private */ fuse_h() {}
    public static OfByte C_CHAR = Constants$root.C_CHAR$LAYOUT;
    public static OfShort C_SHORT = Constants$root.C_SHORT$LAYOUT;
    public static OfInt C_INT = Constants$root.C_INT$LAYOUT;
    public static OfLong C_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfLong C_LONG_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfFloat C_FLOAT = Constants$root.C_FLOAT$LAYOUT;
    public static OfDouble C_DOUBLE = Constants$root.C_DOUBLE$LAYOUT;
    public static OfAddress C_POINTER = Constants$root.C_POINTER$LAYOUT;
    public static MethodHandle fuse_mount$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_mount$MH,"fuse_mount");
    }
    public static MemoryAddress fuse_mount ( Addressable mountpoint,  Addressable args) {
        var mh$ = RuntimeHelper.requireNonNull(constants$0.fuse_mount$MH, "fuse_mount");
        try {
            return (jdk.incubator.foreign.MemoryAddress)mh$.invokeExact(mountpoint, args);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_unmount$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_unmount$MH,"fuse_unmount");
    }
    public static void fuse_unmount ( Addressable mountpoint,  Addressable ch) {
        var mh$ = RuntimeHelper.requireNonNull(constants$0.fuse_unmount$MH, "fuse_unmount");
        try {
            mh$.invokeExact(mountpoint, ch);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_parse_cmdline$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_parse_cmdline$MH,"fuse_parse_cmdline");
    }
    public static int fuse_parse_cmdline ( Addressable args,  Addressable mountpoint,  Addressable multithreaded,  Addressable foreground) {
        var mh$ = RuntimeHelper.requireNonNull(constants$0.fuse_parse_cmdline$MH, "fuse_parse_cmdline");
        try {
            return (int)mh$.invokeExact(args, mountpoint, multithreaded, foreground);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_new$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_new$MH,"fuse_new");
    }
    public static MemoryAddress fuse_new ( Addressable ch,  Addressable args,  Addressable op,  long op_size,  Addressable user_data) {
        var mh$ = RuntimeHelper.requireNonNull(constants$0.fuse_new$MH, "fuse_new");
        try {
            return (jdk.incubator.foreign.MemoryAddress)mh$.invokeExact(ch, args, op, op_size, user_data);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_destroy$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_destroy$MH,"fuse_destroy");
    }
    public static void fuse_destroy ( Addressable f) {
        var mh$ = RuntimeHelper.requireNonNull(constants$1.fuse_destroy$MH, "fuse_destroy");
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
        var mh$ = RuntimeHelper.requireNonNull(constants$1.fuse_loop$MH, "fuse_loop");
        try {
            return (int)mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_exit$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_exit$MH,"fuse_exit");
    }
    public static void fuse_exit ( Addressable f) {
        var mh$ = RuntimeHelper.requireNonNull(constants$1.fuse_exit$MH, "fuse_exit");
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_get_context$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_get_context$MH,"fuse_get_context");
    }
    public static MemoryAddress fuse_get_context () {
        var mh$ = RuntimeHelper.requireNonNull(constants$1.fuse_get_context$MH, "fuse_get_context");
        try {
            return (jdk.incubator.foreign.MemoryAddress)mh$.invokeExact();
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_main_real$MH() {
        return RuntimeHelper.requireNonNull(constants$1.fuse_main_real$MH,"fuse_main_real");
    }
    public static int fuse_main_real ( int argc,  Addressable argv,  Addressable op,  long op_size,  Addressable user_data) {
        var mh$ = RuntimeHelper.requireNonNull(constants$1.fuse_main_real$MH, "fuse_main_real");
        try {
            return (int)mh$.invokeExact(argc, argv, op, op_size, user_data);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}


