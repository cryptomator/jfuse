package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnmountedFuseMountTest {

	private final UnmountedFuseMount unmountedMount = new UnmountedFuseMount();

	@Test
	public void testLoop() {
		Assertions.assertEquals(0, unmountedMount.loop());
	}

	@Test
	public void testUnmount() {
		Assertions.assertDoesNotThrow(unmountedMount::unmount);
	}

	@Test
	public void testDestroy() {
		Assertions.assertDoesNotThrow(unmountedMount::destroy);
	}

}