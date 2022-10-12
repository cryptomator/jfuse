package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_config;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	public FuseConfigImpl(MemoryAddress address, MemorySession scope) {
		this(fuse_config.ofAddress(address, scope));
	}

	@Override
	public int getSetGid() {
		return fuse_config.set_gid$get(segment);
	}

	@Override
	public void setSetGid(int setGid) {
		fuse_config.set_gid$set(segment, setGid);
	}

	@Override
	public int gid() {
		return fuse_config.gid$get(segment);
	}

	@Override
	public void getSetGid(int gid) {
		fuse_config.gid$set(segment, gid);
	}

	@Override
	public int getSetUid() {
		return fuse_config.set_uid$get(segment);
	}

	@Override
	public void setSetUid(int setUid) {
		fuse_config.set_uid$set(segment, setUid);
	}

	@Override
	public int uid() {
		return fuse_config.uid$get(segment);
	}

	@Override
	public void getSetUid(int uid) {
		fuse_config.uid$set(segment, uid);
	}

	@Override
	public int getSetMode() {
		return fuse_config.set_mode$get(segment);
	}

	@Override
	public void setSetMode(int setMode) {
		fuse_config.set_mode$set(segment, setMode);
	}

	@Override
	public int umask() {
		return fuse_config.umask$get(segment);
	}

	@Override
	public void setUmask(int umask) {
		fuse_config.uid$set(segment, umask);
	}

	@Override
	public double entryTimeout() {
		return fuse_config.entry_timeout$get(segment);
	}

	@Override
	public void setEntryTimeout(double entryTimeout) {
		fuse_config.entry_timeout$set(segment, entryTimeout);
	}

	@Override
	public double negativeTimeout() {
		return fuse_config.negative_timeout$get(segment);
	}

	@Override
	public void setNegativeTimeout(double negativeTimeout) {
		fuse_config.negative_timeout$set(segment, negativeTimeout);
	}

	@Override
	public double attrTimeout() {
		return fuse_config.attr_timeout$get(segment);
	}

	@Override
	public void setAttrTimeout(double attrTimeout) {
		fuse_config.attr_timeout$set(segment, attrTimeout);
	}

	@Override
	public int intr() {
		return fuse_config.intr$get(segment);
	}

	@Override
	public void setIntr(int intr) {
		fuse_config.intr$set(segment, intr);
	}

	@Override
	public int intrSignal() {
		return fuse_config.intr_signal$get(segment);
	}

	@Override
	public void setIntrSignal(int intrSignal) {
		fuse_config.intr_signal$set(segment, intrSignal);
	}

	@Override
	public int remember() {
		return fuse_config.remember$get(segment);
	}

	@Override
	public void setRemember(int secondsToRemember) {
		fuse_config.remember$set(segment, secondsToRemember);
	}

	@Override
	public int hardRemove() {
		return fuse_config.hard_remove$get(segment);
	}

	@Override
	public void setHardRemove(int hardRemove) {
		fuse_config.hard_remove$set(segment, hardRemove);
	}

	@Override
	public int useIno() {
		return fuse_config.use_ino$get(segment);
	}

	@Override
	public void setUseIno(int useIno) {
		fuse_config.use_ino$set(segment, useIno);
	}

	@Override
	public int readdirIno() {
		return fuse_config.readdir_ino$get(segment);
	}

	@Override
	public void setReaddirIno(int readdirIno) {
		fuse_config.readdir_ino$set(segment, readdirIno);
	}

	@Override
	public int directIo() {
		return fuse_config.direct_io$get(segment);
	}

	@Override
	public void setDirectIo(int directIo) {
		fuse_config.direct_io$set(segment, directIo);
	}

	@Override
	public int kernelCache() {
		return fuse_config.kernel_cache$get(segment);
	}

	@Override
	public void setKernelCache(int kernelCache) {
		fuse_config.kernel_cache$set(segment, kernelCache);
	}

	@Override
	public int autoCache() {
		return fuse_config.auto_cache$get(segment);
	}

	@Override
	public void setAutoCache(int autoCache) {
		fuse_config.auto_cache$set(segment, autoCache);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse_config.ac_attr_timeout_set$get(segment);
	}

	@Override
	public void setAcAttrTimeoutSet(int acAttrTimeoutSet) {
		fuse_config.ac_attr_timeout_set$set(segment, acAttrTimeoutSet);
	}

	@Override
	public double acAttrTimeout() {
		return fuse_config.ac_attr_timeout$get(segment);
	}

	@Override
	public void setAcAttrTimeout(double acAttrTimeout) {
		fuse_config.ac_attr_timeout$set(segment, acAttrTimeout);
	}

	@Override
	public int nullpathOk() {
		return fuse_config.nullpath_ok$get(segment);
	}

	@Override
	public void setNullpathOk(int nullpathOk) {
		fuse_config.nullpath_ok$set(segment, nullpathOk);
	}

}
