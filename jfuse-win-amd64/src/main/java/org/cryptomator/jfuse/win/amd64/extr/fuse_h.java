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
    public static MethodHandle fuse_main_real$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_main_real$MH,"fuse_main_real");
    }
    public static int fuse_main_real ( int argc,  Addressable argv,  Addressable ops,  long opsize,  Addressable data) {
        var mh$ = fuse_main_real$MH();
        try {
            return (int)mh$.invokeExact(argc, argv, ops, opsize, data);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_exit$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_exit$MH,"fuse_exit");
    }
    public static void fuse_exit ( Addressable f) {
        var mh$ = fuse_exit$MH();
        try {
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
    public static MethodHandle fuse_get_context$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_get_context$MH,"fuse_get_context");
    }
    public static MemoryAddress fuse_get_context () {
        var mh$ = fuse_get_context$MH();
        try {
            return (java.lang.foreign.MemoryAddress)mh$.invokeExact();
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}


