// Generated by jextract

package de.skymatic.fusepanama.mac.lowlevel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
public class stat_h  {

    /* package-private */ stat_h() {}
    public static OfByte C_CHAR = Constants$root.C_CHAR$LAYOUT;
    public static OfShort C_SHORT = Constants$root.C_SHORT$LAYOUT;
    public static OfInt C_INT = Constants$root.C_INT$LAYOUT;
    public static OfLong C_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfLong C_LONG_LONG = Constants$root.C_LONG_LONG$LAYOUT;
    public static OfFloat C_FLOAT = Constants$root.C_FLOAT$LAYOUT;
    public static OfDouble C_DOUBLE = Constants$root.C_DOUBLE$LAYOUT;
    public static OfAddress C_POINTER = Constants$root.C_POINTER$LAYOUT;
    public static int S_IFDIR() {
        return (int)16384L;
    }
    public static int S_IFREG() {
        return (int)32768L;
    }
    public static int S_IFLNK() {
        return (int)40960L;
    }
    public static int S_IRUSR() {
        return (int)256L;
    }
    public static int S_IWUSR() {
        return (int)128L;
    }
    public static int S_IXUSR() {
        return (int)64L;
    }
    public static int S_IRGRP() {
        return (int)32L;
    }
    public static int S_IWGRP() {
        return (int)16L;
    }
    public static int S_IXGRP() {
        return (int)8L;
    }
    public static int S_IROTH() {
        return (int)4L;
    }
    public static int S_IWOTH() {
        return (int)2L;
    }
    public static int S_IXOTH() {
        return (int)1L;
    }
    public static int UTIME_NOW() {
        return (int)-1L;
    }
    public static int UTIME_OMIT() {
        return (int)-2L;
    }
}


