import de.skymatic.fusepanama.FuseBuilder;

module de.skymatic.fusepanama.linuxamd64impl {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	opens de.skymatic.fusepanama.linux.amd64 to de.skymatic.fusepanama;

	provides FuseBuilder with de.skymatic.fusepanama.linux.amd64.LinuxFuseBuilder;
}