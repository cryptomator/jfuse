[![Build](https://github.com/cryptomator/jfuse/actions/workflows/build.yml/badge.svg)](https://github.com/cryptomator/jfuse/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cryptomator_jfuse&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=cryptomator_jfuse)

# jFUSE

Zero-Dependency Java bindings for FUSE using [JEP 424](https://openjdk.org/jeps/424).

## Status

This is currently an experimental library requiring JDK 19. As long as the [Foreign Function & Memory API](https://openjdk.org/jeps/424) is incubating, the required JDK will increase.

We attempt to support libfuse 3.x on Linux and Windows while also remaining compatible with libfuse 2.x on macOS, leading to some compromises in the API.

For libfuse 3 to ensure that the `readdir` operation runs in readdirplus mode, you have to add `FuseOperations.Operation.INIT` to the set returend by `FuseOperations::supportedOperations` method to the supported operations. An implementation of `init` is not necessary.

### Supported `fuse_operations`

Not all [`fuse_operations`](https://libfuse.github.io/doxygen/structfuse__operations.html) are supported yet. 

|                 | Status                                  |
|-----------------|-----------------------------------------|
| getattr         | :white_check_mark:                      |
| ~fgetattr~      | use getattr                             |
| readlink        | :white_check_mark:                      |
| ~getdir~        | use readdir                             |
| ~mknod~         | use create                              |
| mkdir           | :white_check_mark:                      |
| unlink          | :white_check_mark:                      |
| rmdir           | :white_check_mark:                      |
| symlink         | :white_check_mark:                      |
| rename          | :white_check_mark:                      |
| link            | :x:                                     |
| chmod           | :white_check_mark:                      |
| chown           | :x:                                     |
| truncate        | :white_check_mark:                      |
| ~ftruncate~     | use truncate                            |
| ~utime~         | use utimens                             |
| open            | :white_check_mark:                      |
| read            | :white_check_mark:                      |
| write           | :white_check_mark:                      |
| statfs          | :white_check_mark:                      |
| flush           | :x:                                     |
| release         | :white_check_mark:                      |
| fsync           | :x:                                     |
| setxattr        | :x:                                     |
| getxattr        | :x:                                     |
| listxattr       | :x:                                     |
| removexattr     | :x:                                     |
| opendir         | :white_check_mark:                      |
| readdir         | :white_check_mark:                      |
| releasedir      | :white_check_mark:                      |
| fsyncdir        | :x:                                     |
| init            | :white_check_mark:                      |
| destroy         | :white_check_mark:                      |
| access          | :white_check_mark: (ignored on Windows) |
| create          | :white_check_mark:                      |
| lock            | :x:                                     |
| utimens         | :white_check_mark:                      |
| bmap            | :x:                                     |
| ioctl           | :x:                                     |
| poll            | :x:                                     |
| write_buf       | :x:                                     |
| read_buf        | :x:                                     |
| flock           | :x:                                     |
| fallocate       | :x:                                     |
| copy_file_range | :x:                                     |
| lseek           | :x:                                     |

## Usage

Usage examples can be found under [`/jfuse-examples/`](jfuse-examples). You basically need to implement `FuseOperations` and pass it to the `Fuse.builder()`:

```java
var builder = Fuse.builder();
var fs = new MyFileSystem(builder.errno());
try (var fuse = builder.build(fs)) {
	fuse.mount("my-awesome-fs", mountPoint);
	// wait as long as the mounted volume is in use
} // closing will force-unmount (previous graceful unmount recommended)
```

During runtime, you will need to add allow native access from platform-specific implementations via `--enable-native-access`, e.g.:

```bash
java -p path/to/mods \
  -m com.example.mymodule/com.example.mymodule \
  --enable-native-access=org.cryptomator.jfuse.mac \
  --enable-preview
```

## Supported Platforms

Due to slight differences in memory layout, each platform needs its own implementation. Currently, the following operating systems and architectures are supported:

|        | Linux                                      | Mac (macFUSE)            | Windows (WinFSP)       |
|--------|--------------------------------------------|--------------------------|------------------------|
| x86_64 | [jfuse-linux-amd64](jfuse-linux-amd64)     | [jfuse-mac](jfuse-mac)   | [jfuse-win](jfuse-win) |
| arm64  | [jfuse-linux-aarch64](jfuse-linux-aarch64) | [jfuse-mac](jfuse-mac)   | [jfuse-win](jfuse-win) |

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