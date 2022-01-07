import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FuseProvider;

module de.skymatic.fusepanama.macintel {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	provides Errno with de.skymatic.fusepanama.mac.MacErrno;
	provides FuseProvider with de.skymatic.fusepanama.mac.MacFuseProvider;
}