import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.linux.amd64.LinuxFuseBuilder;

/**
 * @provides FuseBuilder FUSE builder for Linux (x86_64)
 */
@SuppressWarnings("JavaModuleNaming") // 64 is not a "version", see https://bugs.openjdk.java.net/browse/JDK-8264488
module org.cryptomator.jfuse.linux.amd64 {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with LinuxFuseBuilder;
}