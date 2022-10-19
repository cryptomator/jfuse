import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.mac.MacFuseBuilder;

/**
 * @provides FuseBuilder FUSE builder for macOS
 */
module org.cryptomator.jfuse.mac {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with MacFuseBuilder;
}