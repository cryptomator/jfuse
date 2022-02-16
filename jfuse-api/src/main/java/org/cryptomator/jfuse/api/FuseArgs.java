package org.cryptomator.jfuse.api;

import jdk.incubator.foreign.MemorySegment;

public record FuseArgs(MemorySegment args, boolean multithreaded, boolean foreground) {
}
