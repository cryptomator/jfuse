package org.cryptomator.jfuse.api;

public interface FuseConfig {

	boolean getSetGid();

	void setSetGid(boolean setGid);

	int gid();

	void getSetGid(int gid);

	boolean getSetUid();

	void setSetUid(boolean setUid);

	int uid();

	void getSetUid(int uid);

	boolean getSetMode();

	void setSetMode(boolean setMode);

	int umask();

	void setUmask(int umask);


	double entryTimeout();

	void setEntryTimeout(double entryTimeout);

	double negativeTimeout();

	void setNegativeTimeout(double negativeTimeout);

	double attrTimeout();

	void setAttrTimeout(double attrTimeout);


	boolean intr();

	void setIntr(boolean intr);

	int intrSignal();

	void setIntrSignal(int intrSignal);

	int remember();

	void setRemember(int secondsToRemember);

	boolean hardRemove();

	void setHardRemove(boolean hardRemove);

	boolean useIno();

	void setUseIno(boolean useIno);

	boolean readdirIno();

	void setReaddirIno(boolean readdirIno);

	boolean directIo();

	void setDirectIo(boolean directIo);

	boolean kernelCache();

	void setKernelCache(boolean kernelCache);

	boolean autoCache();

	void setAutoCache(boolean autoCache);

	boolean acAttrTimeoutSet();

	void setAcAttrTimeoutSet(boolean acAttrTimeoutSet);

	double acAttrTimeout();

	void setAcAttrTimeout(double acAttrTimeout);

	boolean nullpathOk();

	void setNullpathOk(boolean nullpathOk);

	//not supported by winfsp due to being on libfuse 3.2
	//int no_rofd_flush();

}
