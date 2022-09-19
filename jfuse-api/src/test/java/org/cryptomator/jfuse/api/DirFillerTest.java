package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class DirFillerTest {

	@Test
	@DisplayName("fill(path, statFiller)")
	public void testTwoArgFill() throws IOException {
		var filler = Mockito.mock(DirFiller.class);
		Mockito.doCallRealMethod().when(filler).fill(Mockito.any(), Mockito.any());
		Mockito.doReturn(0).when(filler).fill(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyInt());

		filler.fill("name", stat -> {
		});

		Mockito.verify(filler).fill(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyInt());
	}

	@Test
	@DisplayName("fill(path, statFiller) throws IOException")
	public void testExceptionalTwoArgFill() throws IOException {
		var filler = Mockito.mock(DirFiller.class);
		Mockito.doCallRealMethod().when(filler).fill(Mockito.any(), Mockito.any());
		Mockito.doReturn(1).when(filler).fill(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyInt());

		Assertions.assertThrows(IOException.class, () -> {
			filler.fill("name", stat -> {
			});
		});
	}

	@Test
	@DisplayName("fill(path) ")
	public void testOneArgFill() throws IOException {
		var filler = Mockito.mock(DirFiller.class);
		Mockito.doCallRealMethod().when(filler).fill(Mockito.any());
		Mockito.doNothing().when(filler).fill(Mockito.any(), Mockito.any());

		filler.fill("name");

		Mockito.verify(filler).fill(Mockito.any(), Mockito.any());
	}

}