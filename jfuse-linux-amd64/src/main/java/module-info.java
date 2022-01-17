import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.linux.amd64.LinuxFuseBuilder;

module org.cryptomator.jfuse.linuxamd64impl {
	requires org.cryptomator.jfuse.api;
	requires jdk.incubator.foreign;

	provides FuseBuilder with LinuxFuseBuilder;
}