import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.FuseProvider;
import de.skymatic.fusepanama.OpenFlags;

module de.skymatic.fusepanama.mac {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	provides Errno with de.skymatic.fusepanama.mac.MacErrno;
	provides FileModes with de.skymatic.fusepanama.mac.MacFileModes;
	provides FuseProvider with de.skymatic.fusepanama.mac.MacFuseProvider;
	provides OpenFlags with de.skymatic.fusepanama.mac.MacOpenFlags;
}