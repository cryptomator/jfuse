import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.win.amd64.WinFuseBuilder;

module org.cryptomator.jfuse.winamd64impl {
	requires org.cryptomator.jfuse.api;
	requires jdk.incubator.foreign;

	provides FuseBuilder with WinFuseBuilder;
}