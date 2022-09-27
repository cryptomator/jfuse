package org.cryptomator.jfuse.api;

public interface FuseConfig {

	int set_gid();

	int set_uid();

	double entry_timeout();

	double negative_timeout();

	double attr_timeout();

	boolean intr();

	int intr_signal();

	int remember();

	boolean hard_remove();

	boolean use_ino();

	boolean readdir_ino();

	boolean direct_io();

	boolean kernel_cache();

	boolean auto_cache();

	int ac_attr_timeout_set();

	boolean nullpath_ok();

	//not supported by winfsp due to being on libfuse 3.2
	//int no_rofd_flush();

}
