import de.skymatic.fusepanama.FuseBuilder;
import de.skymatic.fusepanama.win.amd64.WinFuseBuilder;

module de.skymatic.fusepanama.winamd64impl {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	opens de.skymatic.fusepanama.win.amd64 to de.skymatic.fusepanama;

	provides FuseBuilder with WinFuseBuilder;
}