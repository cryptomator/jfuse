import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.win.WinFuseBuilder;

/**
 * @provides FuseBuilder FUSE builder for Windows
 */
module org.cryptomator.jfuse.win {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with WinFuseBuilder;
}