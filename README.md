[![Build](https://github.com/cryptomator/jfuse/actions/workflows/build.yml/badge.svg)](https://github.com/cryptomator/jfuse/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cryptomator_jfuse&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=cryptomator_jfuse)

# jFUSE

Zero-Dependency Java bindings for FUSE using [JEP 419](https://openjdk.java.net/jeps/419).

## Status

This is currently an experimental library requiring JDK 18. As long as the [Foreign Function & Memory API](https://openjdk.java.net/jeps/419) is incubating, the required JDK will increase.

Currently, it only provides bindings for [libfuse 2.x](https://github.com/libfuse/libfuse/). Once stable, a new branch for libfuse 3.x will be added.

### Supported `fuse_operations`

Not all `fuse_operations` are supported yet. 

|        | Status |
|--------|-------|
| getattr | :white_check_mark: |
| readlink | :white_check_mark: |
| ~getdir~ | use readdir |
| ~mknod~ | use create |
| mkdir | :white_check_mark: |
| unlink | :white_check_mark: |
| rmdir | :white_check_mark: |
| symlink | :white_check_mark: |
| rename | :white_check_mark: |
| link | :x: |
| chmod | :white_check_mark: |
| chown | :x: |
| truncate | :white_check_mark: |
| ~utime~ | use utimens |
| open | :white_check_mark: |
| read | :white_check_mark: |
| write | :white_check_mark: |
| statfs | :white_check_mark: |
| flush | :x: |
| release | :white_check_mark: |
| fsync | :x: |
| setxattr | :x: |
| getxattr | :x: |
| listxattr | :x: |
| removexattr | :x: |
| opendir | :white_check_mark: |
| readdir | :white_check_mark: |
| releasedir | :white_check_mark: |
| fsyncdir | :x: |
| init | :white_check_mark: |
| destroy | :white_check_mark: |
| access | :white_check_mark: |
| create | :white_check_mark: |
| ftruncate | :x: |
| fgetattr | :x: |
| lock | :x: |
| utimens | :white_check_mark: |
| bmap | :x: |
| ioctl | :x: |
| poll | :x: |
| write_buf | :x: |
| read_buf | :x: |
| flock | :x: |
| fallocate | :x: |

## Usage

Usage examples can be found under [`/jfuse-examples/`](jfuse-examples). You basically need to implement `FuseOperations` and pass it to the `Fuse.builder()`:

```java
var builder = Fuse.builder();
var fs = new MyFileSystem(builder.errno());
try (var fuse = builder.build(fs)) {
	int result = fuse.mount("my-awesome-fs", mountPoint);
	// thread will now block until unmounted or failed
}
```

During runtime, you will need to add allow native access from platform-specific implementations via `--enable-native-access`, e.g.:

```bash
java -p path/to/mods \
  -m com.example.mymodule/com.example.mymodule \
  --enable-native-access=org.cryptomator.jfuse.mac \
  --add-modules jdk.incubator.foreign
```

## Supported Platforms

Due to slight differences in memory layout, each platform needs its own implementation. Currently, the following operating systems and architectures are supported:

|        | Linux                                    | Mac (macFUSE) | Windows (WinFSP) |
|--------|------------------------------------------|-----|---------|
| x86_64 | [jfuse-linux-amd64](jfuse-linux-amd64)   | [jfuse-mac](jfuse-mac) | [jfuse-win-amd64](jfuse-win-amd64) |
| arm64  | [jfuse-linux-aarch64](jfuse-linux-aarch64) | [jfuse-mac](jfuse-mac) |         |

## Building

### Changing Java implementation

Due to the magic of the Foreign Function & Memory API, you can build all modules on any platform that you can find a JDK for.

### Running jextract (on demand)

Each platform has its own module. In rare cases, we need to update jextracted classes.

In most cases this requires you to run the build on the target platform, as you need access to its system-specific header files and (most likely) build tools. See module readme for specific requirements.

In order to run `jextract`, use the corresponding Maven profile (`-Pjextract`).

### Adding a new platform

Before adding a new module, you might want to change the header search path in one of the existing modules and run jextract. If there is no diff at all, you can most likely add a `@SupportedPlatform` annotation to its `FuseBuilder` and check if it works.

Otherwise, you'd need to add a copy of the module. Make sure to open it to the api in the `module-info.java`, as the api module needs reflective access.

## Alternatives

This library is not ready for production use. Currently, your best bet would be the awesome [jnr-fuse](https://github.com/SerCeMan/jnr-fuse), which will eventually become the benchmark we want to beat.