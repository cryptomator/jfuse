// Generated by jextract

package org.cryptomator.jfuse.linux.aarch64.extr.fuse3;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

public class fuse_h {

    fuse_h() {
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

    private static class fuse_version {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_INT    );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_version");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * int fuse_version()
     * }
     */
    public static FunctionDescriptor fuse_version$descriptor() {
        return fuse_version.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * int fuse_version()
     * }
     */
    public static MethodHandle fuse_version$handle() {
        return fuse_version.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * int fuse_version()
     * }
     */
    public static MemorySegment fuse_version$address() {
        return fuse_version.ADDR;
    }

    /**
     * {@snippet lang=c :
     * int fuse_version()
     * }
     */
    public static int fuse_version() {
        var mh$ = fuse_version.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_version");
            }
            return (int)mh$.invokeExact();
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop_cfg_create {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_POINTER    );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop_cfg_create");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * struct fuse_loop_config *fuse_loop_cfg_create()
     * }
     */
    public static FunctionDescriptor fuse_loop_cfg_create$descriptor() {
        return fuse_loop_cfg_create.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * struct fuse_loop_config *fuse_loop_cfg_create()
     * }
     */
    public static MethodHandle fuse_loop_cfg_create$handle() {
        return fuse_loop_cfg_create.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * struct fuse_loop_config *fuse_loop_cfg_create()
     * }
     */
    public static MemorySegment fuse_loop_cfg_create$address() {
        return fuse_loop_cfg_create.ADDR;
    }

    /**
     * {@snippet lang=c :
     * struct fuse_loop_config *fuse_loop_cfg_create()
     * }
     */
    public static MemorySegment fuse_loop_cfg_create() {
        var mh$ = fuse_loop_cfg_create.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop_cfg_create");
            }
            return (MemorySegment)mh$.invokeExact();
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop_cfg_destroy {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop_cfg_destroy");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_destroy(struct fuse_loop_config *config)
     * }
     */
    public static FunctionDescriptor fuse_loop_cfg_destroy$descriptor() {
        return fuse_loop_cfg_destroy.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_destroy(struct fuse_loop_config *config)
     * }
     */
    public static MethodHandle fuse_loop_cfg_destroy$handle() {
        return fuse_loop_cfg_destroy.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_destroy(struct fuse_loop_config *config)
     * }
     */
    public static MemorySegment fuse_loop_cfg_destroy$address() {
        return fuse_loop_cfg_destroy.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_loop_cfg_destroy(struct fuse_loop_config *config)
     * }
     */
    public static void fuse_loop_cfg_destroy(MemorySegment config) {
        var mh$ = fuse_loop_cfg_destroy.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop_cfg_destroy", config);
            }
            mh$.invokeExact(config);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop_cfg_set_max_threads {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER,
            fuse_h.C_INT
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop_cfg_set_max_threads");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_max_threads(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static FunctionDescriptor fuse_loop_cfg_set_max_threads$descriptor() {
        return fuse_loop_cfg_set_max_threads.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_max_threads(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static MethodHandle fuse_loop_cfg_set_max_threads$handle() {
        return fuse_loop_cfg_set_max_threads.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_max_threads(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static MemorySegment fuse_loop_cfg_set_max_threads$address() {
        return fuse_loop_cfg_set_max_threads.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_max_threads(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static void fuse_loop_cfg_set_max_threads(MemorySegment config, int value) {
        var mh$ = fuse_loop_cfg_set_max_threads.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop_cfg_set_max_threads", config, value);
            }
            mh$.invokeExact(config, value);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop_cfg_set_clone_fd {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER,
            fuse_h.C_INT
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop_cfg_set_clone_fd");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_clone_fd(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static FunctionDescriptor fuse_loop_cfg_set_clone_fd$descriptor() {
        return fuse_loop_cfg_set_clone_fd.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_clone_fd(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static MethodHandle fuse_loop_cfg_set_clone_fd$handle() {
        return fuse_loop_cfg_set_clone_fd.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_clone_fd(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static MemorySegment fuse_loop_cfg_set_clone_fd$address() {
        return fuse_loop_cfg_set_clone_fd.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_loop_cfg_set_clone_fd(struct fuse_loop_config *config, unsigned int value)
     * }
     */
    public static void fuse_loop_cfg_set_clone_fd(MemorySegment config, int value) {
        var mh$ = fuse_loop_cfg_set_clone_fd.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop_cfg_set_clone_fd", config, value);
            }
            mh$.invokeExact(config, value);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_lib_help {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_lib_help");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_lib_help(struct fuse_args *args)
     * }
     */
    public static FunctionDescriptor fuse_lib_help$descriptor() {
        return fuse_lib_help.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_lib_help(struct fuse_args *args)
     * }
     */
    public static MethodHandle fuse_lib_help$handle() {
        return fuse_lib_help.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_lib_help(struct fuse_args *args)
     * }
     */
    public static MemorySegment fuse_lib_help$address() {
        return fuse_lib_help.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_lib_help(struct fuse_args *args)
     * }
     */
    public static void fuse_lib_help(MemorySegment args) {
        var mh$ = fuse_lib_help.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_lib_help", args);
            }
            mh$.invokeExact(args);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_mount {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_INT,
            fuse_h.C_POINTER,
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_mount");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * int fuse_mount(struct fuse *f, const char *mountpoint)
     * }
     */
    public static FunctionDescriptor fuse_mount$descriptor() {
        return fuse_mount.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * int fuse_mount(struct fuse *f, const char *mountpoint)
     * }
     */
    public static MethodHandle fuse_mount$handle() {
        return fuse_mount.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * int fuse_mount(struct fuse *f, const char *mountpoint)
     * }
     */
    public static MemorySegment fuse_mount$address() {
        return fuse_mount.ADDR;
    }

    /**
     * {@snippet lang=c :
     * int fuse_mount(struct fuse *f, const char *mountpoint)
     * }
     */
    public static int fuse_mount(MemorySegment f, MemorySegment mountpoint) {
        var mh$ = fuse_mount.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_mount", f, mountpoint);
            }
            return (int)mh$.invokeExact(f, mountpoint);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_unmount {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_unmount");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_unmount(struct fuse *f)
     * }
     */
    public static FunctionDescriptor fuse_unmount$descriptor() {
        return fuse_unmount.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_unmount(struct fuse *f)
     * }
     */
    public static MethodHandle fuse_unmount$handle() {
        return fuse_unmount.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_unmount(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_unmount$address() {
        return fuse_unmount.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_unmount(struct fuse *f)
     * }
     */
    public static void fuse_unmount(MemorySegment f) {
        var mh$ = fuse_unmount.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_unmount", f);
            }
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_destroy {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_destroy");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_destroy(struct fuse *f)
     * }
     */
    public static FunctionDescriptor fuse_destroy$descriptor() {
        return fuse_destroy.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_destroy(struct fuse *f)
     * }
     */
    public static MethodHandle fuse_destroy$handle() {
        return fuse_destroy.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_destroy(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_destroy$address() {
        return fuse_destroy.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_destroy(struct fuse *f)
     * }
     */
    public static void fuse_destroy(MemorySegment f) {
        var mh$ = fuse_destroy.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_destroy", f);
            }
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_INT,
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * int fuse_loop(struct fuse *f)
     * }
     */
    public static FunctionDescriptor fuse_loop$descriptor() {
        return fuse_loop.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * int fuse_loop(struct fuse *f)
     * }
     */
    public static MethodHandle fuse_loop$handle() {
        return fuse_loop.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * int fuse_loop(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_loop$address() {
        return fuse_loop.ADDR;
    }

    /**
     * {@snippet lang=c :
     * int fuse_loop(struct fuse *f)
     * }
     */
    public static int fuse_loop(MemorySegment f) {
        var mh$ = fuse_loop.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop", f);
            }
            return (int)mh$.invokeExact(f);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_exit {
        public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_exit");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * void fuse_exit(struct fuse *f)
     * }
     */
    public static FunctionDescriptor fuse_exit$descriptor() {
        return fuse_exit.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * void fuse_exit(struct fuse *f)
     * }
     */
    public static MethodHandle fuse_exit$handle() {
        return fuse_exit.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * void fuse_exit(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_exit$address() {
        return fuse_exit.ADDR;
    }

    /**
     * {@snippet lang=c :
     * void fuse_exit(struct fuse *f)
     * }
     */
    public static void fuse_exit(MemorySegment f) {
        var mh$ = fuse_exit.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_exit", f);
            }
            mh$.invokeExact(f);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_loop_mt {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_INT,
            fuse_h.C_POINTER,
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_loop_mt");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * int fuse_loop_mt(struct fuse *f, struct fuse_loop_config *config)
     * }
     */
    public static FunctionDescriptor fuse_loop_mt$descriptor() {
        return fuse_loop_mt.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * int fuse_loop_mt(struct fuse *f, struct fuse_loop_config *config)
     * }
     */
    public static MethodHandle fuse_loop_mt$handle() {
        return fuse_loop_mt.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * int fuse_loop_mt(struct fuse *f, struct fuse_loop_config *config)
     * }
     */
    public static MemorySegment fuse_loop_mt$address() {
        return fuse_loop_mt.ADDR;
    }

    /**
     * {@snippet lang=c :
     * int fuse_loop_mt(struct fuse *f, struct fuse_loop_config *config)
     * }
     */
    public static int fuse_loop_mt(MemorySegment f, MemorySegment config) {
        var mh$ = fuse_loop_mt.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_loop_mt", f, config);
            }
            return (int)mh$.invokeExact(f, config);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static class fuse_get_session {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            fuse_h.C_POINTER,
            fuse_h.C_POINTER
        );

        public static final MemorySegment ADDR = fuse_h.findOrThrow("fuse_get_session");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    /**
     * Function descriptor for:
     * {@snippet lang=c :
     * struct fuse_session *fuse_get_session(struct fuse *f)
     * }
     */
    public static FunctionDescriptor fuse_get_session$descriptor() {
        return fuse_get_session.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang=c :
     * struct fuse_session *fuse_get_session(struct fuse *f)
     * }
     */
    public static MethodHandle fuse_get_session$handle() {
        return fuse_get_session.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang=c :
     * struct fuse_session *fuse_get_session(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_get_session$address() {
        return fuse_get_session.ADDR;
    }

    /**
     * {@snippet lang=c :
     * struct fuse_session *fuse_get_session(struct fuse *f)
     * }
     */
    public static MemorySegment fuse_get_session(MemorySegment f) {
        var mh$ = fuse_get_session.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("fuse_get_session", f);
            }
            return (MemorySegment)mh$.invokeExact(f);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }
}

