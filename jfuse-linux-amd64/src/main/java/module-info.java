import org.cryptomator.jfuse.FuseBuilder;
import org.cryptomator.jfuse.linux.amd64.LinuxFuseBuilder;

module org.cryptomator.jfuse.linuxamd64impl {
	requires org.cryptomator.jfuse;
	requires jdk.incubator.foreign;

	opens org.cryptomator.jfuse.linux.amd64 to org.cryptomator.jfuse;

	provides FuseBuilder with LinuxFuseBuilder;
}