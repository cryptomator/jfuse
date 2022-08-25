package org.cryptomator.jfuse.api;

import java.lang.foreign.MemoryAddress;
import java.nio.file.Path;

public record FuseSession(Path mountPoint, MemoryAddress ch, MemoryAddress fuse) {
}
