package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.FuseConfig;
import org.cryptomator.jfuse.win.amd64.extr.fuse3_config;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

record FuseConfigImpl(MemorySegment segment) implements FuseConfig {

	public FuseConfigImpl(MemoryAddress address, MemorySession scope) {
		this(fuse3_config.ofAddress(address, scope));
	}

	@Override
	public int getSetGid() {
		return fuse3_config.set_gid$get(segment);
	}

	@Override
	public void setSetGid(int setGid) {
		fuse3_config.set_gid$set(segment, setGid);
	}

	@Override
	public int gid() {
		return fuse3_config.gid$get(segment);
	}

	@Override
	public void getSetGid(int gid) {
		fuse3_config.gid$set(segment, gid);
	}

	@Override
	public int getSetUid() {
		return fuse3_config.set_uid$get(segment);
	}

	@Override
	public void setSetUid(int setUid) {
		fuse3_config.set_uid$set(segment, setUid);
	}

	@Override
	public int uid() {
		return fuse3_config.uid$get(segment);
	}

	@Override
	public void getSetUid(int uid) {
		fuse3_config.uid$set(segment, uid);
	}

	@Override
	public int getSetMode() {
		return fuse3_config.set_mode$get(segment);
	}

	@Override
	public void setSetMode(int setMode) {
		fuse3_config.set_mode$set(segment, setMode);
	}

	@Override
	public int umask() {
		return fuse3_config.umask$get(segment);
	}

	@Override
	public void setUmask(int umask) {
		fuse3_config.uid$set(segment, umask);
	}

	@Override
	public double entryTimeout() {
		return fuse3_config.entry_timeout$get(segment);
	}

	@Override
	public void setEntryTimeout(double entryTimeout) {
		fuse3_config.entry_timeout$set(segment, entryTimeout);
	}

	@Override
	public double negativeTimeout() {
		return fuse3_config.negative_timeout$get(segment);
	}

	@Override
	public void setNegativeTimeout(double negativeTimeout) {
		fuse3_config.negative_timeout$set(segment, negativeTimeout);
	}

	@Override
	public double attrTimeout() {
		return fuse3_config.attr_timeout$get(segment);
	}

	@Override
	public void setAttrTimeout(double attrTimeout) {
		fuse3_config.attr_timeout$set(segment, attrTimeout);
	}

	@Override
	public int intr() {
		return fuse3_config.intr$get(segment);
	}

	@Override
	public void setIntr(int intr) {
		fuse3_config.intr$set(segment, intr);
	}

	@Override
	public int intrSignal() {
		return fuse3_config.intr_signal$get(segment);
	}

	@Override
	public void setIntrSignal(int intrSignal) {
		fuse3_config.intr_signal$set(segment, intrSignal);
	}

	@Override
	public int remember() {
		return fuse3_config.remember$get(segment);
	}

	@Override
	public void setRemember(int secondsToRemember) {
		fuse3_config.remember$set(segment, secondsToRemember);
	}

	@Override
	public int hardRemove() {
		return fuse3_config.hard_remove$get(segment);
	}

	@Override
	public void setHardRemove(int hardRemove) {
		fuse3_config.hard_remove$set(segment, hardRemove);
	}

	@Override
	public int useIno() {
		return fuse3_config.use_ino$get(segment);
	}

	@Override
	public void setUseIno(int useIno) {
		fuse3_config.use_ino$set(segment, useIno);
	}

	@Override
	public int readdirIno() {
		return fuse3_config.readdir_ino$get(segment);
	}

	@Override
	public void setReaddirIno(int readdirIno) {
		fuse3_config.readdir_ino$set(segment, readdirIno);
	}

	@Override
	public int directIo() {
		return fuse3_config.direct_io$get(segment);
	}

	@Override
	public void setDirectIo(int directIo) {
		fuse3_config.direct_io$set(segment, directIo);
	}

	@Override
	public int kernelCache() {
		return fuse3_config.kernel_cache$get(segment);
	}

	@Override
	public void setKernelCache(int kernelCache) {
		fuse3_config.kernel_cache$set(segment, kernelCache);
	}

	@Override
	public int autoCache() {
		return fuse3_config.auto_cache$get(segment);
	}

	@Override
	public void setAutoCache(int autoCache) {
		fuse3_config.auto_cache$set(segment, autoCache);
	}

	@Override
	public int acAttrTimeoutSet() {
		return fuse3_config.ac_attr_timeout_set$get(segment);
	}

	@Override
	public void setAcAttrTimeoutSet(int acAttrTimeoutSet) {
		fuse3_config.ac_attr_timeout_set$set(segment, acAttrTimeoutSet);
	}

	@Override
	public double acAttrTimeout() {
		return fuse3_config.ac_attr_timeout$get(segment);
	}

	@Override
	public void setAcAttrTimeout(double acAttrTimeout) {
		fuse3_config.ac_attr_timeout$set(segment, acAttrTimeout);
	}

	@Override
	public int nullpathOk() {
		return fuse3_config.nullpath_ok$get(segment);
	}

	@Override
	public void setNullpathOk(int nullpathOk) {
		fuse3_config.nullpath_ok$set(segment, nullpathOk);
	}

}
