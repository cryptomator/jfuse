package org.cryptomator.jfuse.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


public class BenchmarkTest {

	@Disabled("only on demand")
	@Test
	public void runBenchmarks() throws IOException {
		org.openjdk.jmh.Main.main(new String[0]);
	}

	@Benchmark
	@Warmup(iterations = 2)
	@Fork(value = 1)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public void testListDirJnrMulti() throws IOException {
		Files.list(Path.of("/Volumes/bar")).close();
	}

	@Benchmark
	@Warmup(iterations = 2)
	@Fork(value = 1)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public void testListDirPanamaMulti() throws IOException {
		Files.list(Path.of("/Volumes/foo")).close();
	}

//	@Benchmark
//	@Warmup(iterations = 2)
//	@Fork(value = 1)
//	@OutputTimeUnit(TimeUnit.MICROSECONDS)
//	@BenchmarkMode(Mode.AverageTime)
//	public void testListDirJnrSingle() throws IOException {
//		Files.list(Path.of("/Volumes/bars")).close();
//	}
//
//	@Benchmark
//	@Warmup(iterations = 2)
//	@Fork(value = 1)
//	@OutputTimeUnit(TimeUnit.MICROSECONDS)
//	@BenchmarkMode(Mode.AverageTime)
//	public void testListDirPanamaSingle() throws IOException {
//		Files.list(Path.of("/Volumes/foos")).close();
//	}

}
