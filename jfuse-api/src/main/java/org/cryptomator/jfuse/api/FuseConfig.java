package org.cryptomator.jfuse.api;

public interface FuseConfig {

	boolean getSetGid();

	void setSetGid(boolean setGid);

	int getGid();

	void setGid(int gid);

	boolean getSetUid();

	void setSetUid(boolean setUid);

	int getUid();

	void setUid(int uid);

	boolean getSetMode();

	void setSetMode(boolean setMode);

	int getUmask();

	void setUmask(int umask);


	double getEntryTimeout();

	void setEntryTimeout(double entryTimeout);

	double getNegativeTimeout();

	void setNegativeTimeout(double negativeTimeout);

	double getAttrTimeout();

	void setAttrTimeout(double attrTimeout);


	boolean getIntr();

	void setIntr(boolean intr);

	int getIntrSignal();

	void setIntrSignal(int intrSignal);

	int getRemember();

	void setRemember(int secondsToRemember);

	boolean getHardRemove();

	void setHardRemove(boolean hardRemove);

	boolean getUseIno();

	void setUseIno(boolean useIno);

	boolean getReaddirIno();

	void setReaddirIno(boolean readdirIno);

	boolean getDirectIo();

	void setDirectIo(boolean directIo);

	boolean getKernelCache();

	void setKernelCache(boolean kernelCache);

	boolean getAutoCache();

	void setAutoCache(boolean autoCache);

	boolean getAcAttrTimeoutSet();

	void setAcAttrTimeoutSet(boolean acAttrTimeoutSet);

	double getAcAttrTimeout();

	void setAcAttrTimeout(double acAttrTimeout);

	boolean getNullpathOk();

	void setNullpathOk(boolean nullpathOk);

	//not supported by winfsp due to being on libfuse 3.2
	//int no_rofd_flush();

}
