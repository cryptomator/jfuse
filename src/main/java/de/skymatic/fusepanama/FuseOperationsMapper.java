package de.skymatic.fusepanama;

import jdk.incubator.foreign.MemorySegment;

import java.util.concurrent.CompletableFuture;

public interface FuseOperationsMapper {

	CompletableFuture<Integer> initialized();

	CompletableFuture<Void> destroyed();

	MemorySegment fuseOperationsStruct();

}
