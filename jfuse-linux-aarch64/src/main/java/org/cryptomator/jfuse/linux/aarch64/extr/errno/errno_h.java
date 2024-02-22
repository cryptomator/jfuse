// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.errno;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

public class errno_h {

    errno_h() {
        // Should not be called directly
    }

    static final Arena LIBRARY_ARENA = Arena.ofAuto();
    static final boolean TRACE_DOWNCALLS = Boolean.getBoolean("jextract.trace.downcalls");

    static void traceDowncall(String name, Object... args) {
         String traceArgs = Arrays.stream(args)
                       .map(Object::toString)
                       .collect(Collectors.joining(", "));
         System.out.printf("%s(%s)\n", name, traceArgs);
    }

    static MemorySegment findOrThrow(String symbol) {
        return SYMBOL_LOOKUP.find(symbol)
            .orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
    }

    static MethodHandle upcallHandle(Class<?> fi, String name, FunctionDescriptor fdesc) {
        try {
            return MethodHandles.lookup().findVirtual(fi, name, fdesc.toMethodType());
        } catch (ReflectiveOperationException ex) {
            throw new AssertionError(ex);
        }
    }

    static MemoryLayout align(MemoryLayout layout, long align) {
        return switch (layout) {
            case PaddingLayout p -> p;
            case ValueLayout v -> v.withByteAlignment(align);
            case GroupLayout g -> {
                MemoryLayout[] alignedMembers = g.memberLayouts().stream()
                        .map(m -> align(m, align)).toArray(MemoryLayout[]::new);
                yield g instanceof StructLayout ?
                        MemoryLayout.structLayout(alignedMembers) : MemoryLayout.unionLayout(alignedMembers);
            }
            case SequenceLayout s -> MemoryLayout.sequenceLayout(s.elementCount(), align(s.elementLayout(), align));
        };
    }

    static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup()
            .or(Linker.nativeLinker().defaultLookup());

    public static final ValueLayout.OfBoolean C_BOOL = ValueLayout.JAVA_BOOLEAN;
    public static final ValueLayout.OfByte C_CHAR = ValueLayout.JAVA_BYTE;
    public static final ValueLayout.OfShort C_SHORT = ValueLayout.JAVA_SHORT;
    public static final ValueLayout.OfInt C_INT = ValueLayout.JAVA_INT;
    public static final ValueLayout.OfLong C_LONG_LONG = ValueLayout.JAVA_LONG;
    public static final ValueLayout.OfFloat C_FLOAT = ValueLayout.JAVA_FLOAT;
    public static final ValueLayout.OfDouble C_DOUBLE = ValueLayout.JAVA_DOUBLE;
    public static final AddressLayout C_POINTER = ValueLayout.ADDRESS
            .withTargetLayout(MemoryLayout.sequenceLayout(java.lang.Long.MAX_VALUE, JAVA_BYTE));
    public static final ValueLayout.OfLong C_LONG = ValueLayout.JAVA_LONG;
    private static final int ENOENT = (int)2L;
    /**
     * {@snippet lang=c :
     * #define ENOENT 2
     * }
     */
    public static int ENOENT() {
        return ENOENT;
    }
    private static final int EIO = (int)5L;
    /**
     * {@snippet lang=c :
     * #define EIO 5
     * }
     */
    public static int EIO() {
        return EIO;
    }
    private static final int E2BIG = (int)7L;
    /**
     * {@snippet lang=c :
     * #define E2BIG 7
     * }
     */
    public static int E2BIG() {
        return E2BIG;
    }
    private static final int EBADF = (int)9L;
    /**
     * {@snippet lang=c :
     * #define EBADF 9
     * }
     */
    public static int EBADF() {
        return EBADF;
    }
    private static final int ENOMEM = (int)12L;
    /**
     * {@snippet lang=c :
     * #define ENOMEM 12
     * }
     */
    public static int ENOMEM() {
        return ENOMEM;
    }
    private static final int EACCES = (int)13L;
    /**
     * {@snippet lang=c :
     * #define EACCES 13
     * }
     */
    public static int EACCES() {
        return EACCES;
    }
    private static final int EEXIST = (int)17L;
    /**
     * {@snippet lang=c :
     * #define EEXIST 17
     * }
     */
    public static int EEXIST() {
        return EEXIST;
    }
    private static final int ENOTDIR = (int)20L;
    /**
     * {@snippet lang=c :
     * #define ENOTDIR 20
     * }
     */
    public static int ENOTDIR() {
        return ENOTDIR;
    }
    private static final int EISDIR = (int)21L;
    /**
     * {@snippet lang=c :
     * #define EISDIR 21
     * }
     */
    public static int EISDIR() {
        return EISDIR;
    }
    private static final int EINVAL = (int)22L;
    /**
     * {@snippet lang=c :
     * #define EINVAL 22
     * }
     */
    public static int EINVAL() {
        return EINVAL;
    }
    private static final int EROFS = (int)30L;
    /**
     * {@snippet lang=c :
     * #define EROFS 30
     * }
     */
    public static int EROFS() {
        return EROFS;
    }
    private static final int ERANGE = (int)34L;
    /**
     * {@snippet lang=c :
     * #define ERANGE 34
     * }
     */
    public static int ERANGE() {
        return ERANGE;
    }
    private static final int ENAMETOOLONG = (int)36L;
    /**
     * {@snippet lang=c :
     * #define ENAMETOOLONG 36
     * }
     */
    public static int ENAMETOOLONG() {
        return ENAMETOOLONG;
    }
    private static final int ENOLCK = (int)37L;
    /**
     * {@snippet lang=c :
     * #define ENOLCK 37
     * }
     */
    public static int ENOLCK() {
        return ENOLCK;
    }
    private static final int ENOSYS = (int)38L;
    /**
     * {@snippet lang=c :
     * #define ENOSYS 38
     * }
     */
    public static int ENOSYS() {
        return ENOSYS;
    }
    private static final int ENOTEMPTY = (int)39L;
    /**
     * {@snippet lang=c :
     * #define ENOTEMPTY 39
     * }
     */
    public static int ENOTEMPTY() {
        return ENOTEMPTY;
    }
    private static final int ENODATA = (int)61L;
    /**
     * {@snippet lang=c :
     * #define ENODATA 61
     * }
     */
    public static int ENODATA() {
        return ENODATA;
    }
    private static final int ENOTSUP = (int)95L;
    /**
     * {@snippet lang=c :
     * #define ENOTSUP 95
     * }
     */
    public static int ENOTSUP() {
        return ENOTSUP;
    }
}

