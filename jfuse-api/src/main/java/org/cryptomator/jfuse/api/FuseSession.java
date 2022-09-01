package org.cryptomator.jfuse.api;

import java.lang.foreign.MemoryAddress;
import java.nio.file.Path;

public record FuseSession(MemoryAddress fuse, MemoryAddress session) {
}
