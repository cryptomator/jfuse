import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.linux.amd64.LinuxFuseBuilder;

@SuppressWarnings("JavaModuleNaming") // 64 is not a "version", see https://bugs.openjdk.java.net/browse/JDK-8264488
module org.cryptomator.jfuse.linux.amd64 {
	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with LinuxFuseBuilder;
}