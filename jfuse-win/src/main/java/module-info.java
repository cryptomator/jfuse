import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.win.WinFuseBuilder;

module org.cryptomator.jfuse.win {
	requires static org.jetbrains.annotations;

	requires org.cryptomator.jfuse.api;

	provides FuseBuilder with WinFuseBuilder;
}