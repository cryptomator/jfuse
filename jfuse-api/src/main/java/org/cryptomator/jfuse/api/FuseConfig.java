package org.cryptomator.jfuse.api;

/**
 * Configuration Struct used in {@link FuseOperations#init(FuseConnInfo, FuseConfig)}.
 * Libfuse 3 only struct.
 */
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

	/**
	 * version > 3.3 only
	 *
	 * @return no_rofd_flush value of fuse_config or 0 if not supported
	 */
	int noRofdFlush();

	/**
	 * version > 3.3 only
	 * <p>
	 * Set no_rofd_flush field in fuse_config or no-op if not supported
	 */
	void setNoRofdFlush(int noRofdFlush);

}
