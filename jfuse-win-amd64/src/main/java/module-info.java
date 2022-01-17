import org.cryptomator.jfuse.FuseBuilder;
import org.cryptomator.jfuse.win.amd64.WinFuseBuilder;

module org.cryptomator.jfuse.winamd64impl {
	requires org.cryptomator.jfuse;
	requires jdk.incubator.foreign;

	opens org.cryptomator.jfuse.win.amd64 to org.cryptomator.jfuse;

	provides FuseBuilder with WinFuseBuilder;
}