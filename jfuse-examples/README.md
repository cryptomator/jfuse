# jFUSE Examples

Assuming you have built the parent project (`mvn package`), you can run these examples directly from the shell.

These examples require JDK 18 EA or newer. You may want to adjust paths, such as the `java.library.path`.

### Running the hello world example:

```sh
$JAVA_HOME/bin/java \
  -Djava.library.path=/usr/local/lib \
  -p target/classes:target/mods \
  --enable-native-access=org.cryptomator.jfuse.mac,org.cryptomator.jfuse.linux.arm64,org.cryptomator.jfuse.linux.amd64 \
  -m org.cryptomator.jfuse.examples/org.cryptomator.jfuse.examples.HelloWorldFileSystem \
  /path/to/mountpoint
```

### Running the POSIX mirror example:

```sh
$JAVA_HOME/bin/java \
  -Djava.library.path=/usr/local/lib \
  -p target/classes:target/mods \
  --enable-native-access=org.cryptomator.jfuse.mac,org.cryptomator.jfuse.linux.arm64,org.cryptomator.jfuse.linux.amd64 \
  -m org.cryptomator.jfuse.examples/org.cryptomator.jfuse.examples.MirrorPosixFileSystem \
  /path/to/to-be-mirrored/dir /path/to/mountpoint
```

### Running the Windows mirror example:

```sh
$JAVA_HOME/bin/java \
  -Djava.library.path=/usr/local/lib \
  -p target/classes:target/mods \
  --enable-native-access=org.cryptomator.jfuse.win.amd64 \
  -m org.cryptomator.jfuse.examples/org.cryptomator.jfuse.examples.MirrorPosixFileSystem \
  C:/path/to/to-be-mirrored/dir M:
```