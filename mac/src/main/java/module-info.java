import de.skymatic.fusepanama.FuseBuilder;

module de.skymatic.fusepanama.mac {
	requires de.skymatic.fusepanama;
	requires jdk.incubator.foreign;

	opens de.skymatic.fusepanama.mac to de.skymatic.fusepanama;

	provides FuseBuilder with de.skymatic.fusepanama.mac.MacFuseBuilder;
}