# FUSE-Panama

This project is (currently) a proof of concept for [Panama-based](https://openjdk.java.net/projects/panama/) Java-bindings of [libfuse 2.x](https://github.com/libfuse/libfuse/).

Its long-term goal is to evolve into a zero-dependency library for Java programs that wish to create a FUSE drive.

## Usage

Usage examples can be found under `src/test/java/de/skymatic/fusepanama/examples/`.

Library loading is currently hard-coded. You may want to adjust [this line](https://github.com/skymatic/fuse-panama/blob/4482fa138cffc6de0f947a3cac7ca246d43f2e70/src/main/java/de/skymatic/fusepanama/linux/lowlevel/fuse_h.java#L14)
to point to the correct `libfuse.so`.

## Alternatives

This library is not ready for production use. Currently, your best bet would be the awesome [jnr-fuse](https://github.com/SerCeMan/jnr-fuse), which will eventually
become the benchmark we want to beat.