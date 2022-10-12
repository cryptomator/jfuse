import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.linux.aarch64.LinuxFuseBuilder;

/**
 * @provides FuseBuilder FUSE builder for Linux (aarch64)
 */
@SuppressWarnings("JavaModuleNaming") // 64 is not a "version", see https://bugs.openjdk.java.net/browse/JDK-8264488
module org.cryptomator.jfuse.linux.aarch64 {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with LinuxFuseBuilder;
}