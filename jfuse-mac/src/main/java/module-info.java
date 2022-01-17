import org.cryptomator.jfuse.FuseBuilder;
import org.cryptomator.jfuse.mac.MacFuseBuilder;

module org.cryptomator.jfuse.mac {
	requires org.cryptomator.jfuse;
	requires jdk.incubator.foreign;

	provides FuseBuilder with MacFuseBuilder;
}