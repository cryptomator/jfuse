import de.skymatic.fusepanama.Errno;

module de.skymatic.fusepanama.linuxintel {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	provides Errno with de.skymatic.fusepanama.linux.LinuxErrno;
}