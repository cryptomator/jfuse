package de.skymatic.fusepanama;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


public class BenchmarkTest {

	public static void main(String[] args) throws IOException {
		org.openjdk.jmh.Main.main(args);
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

	@Benchmark
	@Warmup(iterations = 2)
	@Fork(value = 1)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public void testListDirJnrSingle() throws IOException {
		Files.list(Path.of("/Volumes/bars")).close();
	}

	@Benchmark
	@Warmup(iterations = 2)
	@Fork(value = 1)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@BenchmarkMode(Mode.AverageTime)
	public void testListDirPanamaSingle() throws IOException {
		Files.list(Path.of("/Volumes/foos")).close();
	}

}
