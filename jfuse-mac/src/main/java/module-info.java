import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.mac.MacFuseBuilder;

module org.cryptomator.jfuse.mac {
	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with MacFuseBuilder;
}