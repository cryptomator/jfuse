package org.cryptomator.jfuse.api;

public interface FuseMount {

	void loop();

	void unmount() ;

	void destroy();

}
