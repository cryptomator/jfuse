// Generated by jextract

package org.cryptomator.jfuse.win.amd64.extr_fuse2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
public class fuse_2_h  {

    /* package-private */ fuse_2_h() {}
    public static OfByte C_CHAR = Constants$root.C_CHAR$LAYOUT;
    public static OfShort C_SHORT = Constants$root.C_SHORT$LAYOUT;
    public static OfInt C_INT = Constants$root.C_LONG$LAYOUT;
    public static OfInt C_LONG = Constants$root.C_LONG$LAYOUT;
    public static OfLong C_LONG_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfFloat C_FLOAT = Constants$root.C_FLOAT$LAYOUT;
    public static OfDouble C_DOUBLE = Constants$root.C_DOUBLE$LAYOUT;
    public static OfAddress C_POINTER = Constants$root.C_POINTER$LAYOUT;
    public static MethodHandle fuse_parse_cmdline$MH() {
        return RuntimeHelper.requireNonNull(constants$0.fuse_parse_cmdline$MH,"fuse_parse_cmdline");
    }
    public static int fuse_parse_cmdline ( Addressable args,  Addressable mountpoint,  Addressable multithreaded,  Addressable foreground) {
        var mh$ = fuse_parse_cmdline$MH();
        try {
            return (int)mh$.invokeExact(args, mountpoint, multithreaded, foreground);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}

