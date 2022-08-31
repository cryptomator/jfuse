import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.mac.MacFuseBuilder;

module org.cryptomator.jfuse.mac {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with MacFuseBuilder;
}