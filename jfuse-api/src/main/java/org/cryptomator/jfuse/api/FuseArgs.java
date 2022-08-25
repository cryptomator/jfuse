package org.cryptomator.jfuse.api;

import java.lang.foreign.MemorySegment;

public record FuseArgs(MemorySegment args, boolean multithreaded, boolean foreground) {
}
