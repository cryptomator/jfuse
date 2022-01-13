import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.FuseProvider;
import de.skymatic.fusepanama.OpenFlags;
import de.skymatic.fusepanama.win.amd64.WinErrno;
import de.skymatic.fusepanama.win.amd64.WinFileModes;
import de.skymatic.fusepanama.win.amd64.WinFuseProvider;
import de.skymatic.fusepanama.win.amd64.WinOpenFlags;

module de.skymatic.fusepanama.winamd64impl {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	provides Errno with WinErrno;
	provides FileModes with WinFileModes;
	provides FuseProvider with WinFuseProvider;
	provides OpenFlags with WinOpenFlags;
}