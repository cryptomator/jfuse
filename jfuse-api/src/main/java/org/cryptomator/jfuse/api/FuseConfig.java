package org.cryptomator.jfuse.api;

public interface FuseConfig {

	int getSetGid();

	void setSetGid(int setGid);

	int gid();

	void setGid(int gid);

	int getSetUid();

	void setSetUid(int setUid);

	int uid();

	void setUid(int uid);

	int getSetMode();

	void setSetMode(int setMode);

	int umask();

	void setUmask(int umask);


	double entryTimeout();

	void setEntryTimeout(double entryTimeout);

	double negativeTimeout();

	void setNegativeTimeout(double negativeTimeout);

	double attrTimeout();

	void setAttrTimeout(double attrTimeout);

	int intr();

	void setIntr(int intr);

	int intrSignal();

	void setIntrSignal(int intrSignal);

	int remember();

	void setRemember(int secondsToRemember);

	int hardRemove();

	void setHardRemove(int hardRemove);

	int useIno();

	void setUseIno(int useIno);

	int readdirIno();

	void setReaddirIno(int readdirIno);

	int directIo();

	void setDirectIo(int directIo);

	int kernelCache();

	void setKernelCache(int kernelCache);

	int autoCache();

	void setAutoCache(int autoCache);

	int acAttrTimeoutSet();

	void setAcAttrTimeoutSet(int acAttrTimeoutSet);

	double acAttrTimeout();

	void setAcAttrTimeout(double acAttrTimeout);

	int nullpathOk();

	void setNullpathOk(int nullpathOk);

	//not supported by winfsp due to being on libfuse 3.2
	//int no_rofd_flush();

}
