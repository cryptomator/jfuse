package de.skymatic.fusepanama;

import java.foreign.Scope;
import java.foreign.memory.Callback;
import java.foreign.memory.Pointer;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.libfuse.fuse_common_h;
import com.github.libfuse.fuse_h;
import com.github.libfuse.fuse_lib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFuseFileSystem implements AutoCloseable, FuseOperations {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractFuseFileSystem.class);

	protected final Scope scope;
	protected final fuse_h.fuse_operations fuseOperations;
	private final Set<String> notImplementedMethods;
	private Pointer<fuse_h.fuse> fuseHandle;

	public AbstractFuseFileSystem() {
		this.scope = Scope.globalScope().fork();
		this.fuseOperations = scope.allocateStruct(fuse_h.fuse_operations.class);
		this.notImplementedMethods = Arrays.stream(getClass().getMethods())
				.filter(method -> method.getAnnotation(NotImplemented.class) != null)
				.map(Method::getName)
				.collect(Collectors.toSet());
		fuseOperations.getattr$set(allocateIfImplemented("getattr", this::getattr));
		fuseOperations.readlink$set(allocateIfImplemented("readlink", this::readlink));
		fuseOperations.getdir$set(allocateIfImplemented("getdir", this::getdir));
		fuseOperations.mknod$set(allocateIfImplemented("mknod", this::mknod));
		fuseOperations.mkdir$set(allocateIfImplemented("mkdir", this::mkdir));
		fuseOperations.unlink$set(allocateIfImplemented("unlink", this::unlink));
		fuseOperations.rmdir$set(allocateIfImplemented("rmdir", this::rmdir));
		fuseOperations.symlink$set(allocateIfImplemented("symlink", this::symlink));
		fuseOperations.rename$set(allocateIfImplemented("rename", this::rename));
		fuseOperations.link$set(allocateIfImplemented("link", this::link));
		fuseOperations.chmod$set(allocateIfImplemented("chmod", this::chmod));
		fuseOperations.chown$set(allocateIfImplemented("chown", this::chown));
		fuseOperations.truncate$set(allocateIfImplemented("truncate", this::truncate));
		fuseOperations.utime$set(allocateIfImplemented("utime", this::utime));
		fuseOperations.open$set(allocateIfImplemented("open", this::open));
		fuseOperations.read$set(allocateIfImplemented("read", this::read));
		fuseOperations.write$set(allocateIfImplemented("write", this::write));
		fuseOperations.statfs$set(allocateIfImplemented("statfs", this::statfs));
		fuseOperations.flush$set(allocateIfImplemented("flush", this::flush));
		fuseOperations.release$set(allocateIfImplemented("release", this::release));
		fuseOperations.fsync$set(allocateIfImplemented("fsync", this::fsync));
		fuseOperations.setxattr$set(allocateIfImplemented("setxattr", this::setxattr));
		fuseOperations.getxattr$set(allocateIfImplemented("getxattr", this::getxattr));
		fuseOperations.listxattr$set(allocateIfImplemented("listxattr", this::listxattr));
		fuseOperations.removexattr$set(allocateIfImplemented("removexattr", this::removexattr));
		fuseOperations.opendir$set(allocateIfImplemented("opendir", this::opendir));
		fuseOperations.readdir$set(allocateIfImplemented("readdir", this::readdir));
		fuseOperations.releasedir$set(allocateIfImplemented("releasedir", this::releasedir));
		fuseOperations.fsyncdir$set(allocateIfImplemented("fsyncdir", this::fsyncdir));
		fuseOperations.init$set(allocateIfImplemented("init", this::init));
		fuseOperations.destroy$set(allocateIfImplemented("destroy", this::destroy));
		fuseOperations.access$set(allocateIfImplemented("access", this::access));
		fuseOperations.create$set(allocateIfImplemented("create", this::create));
		fuseOperations.ftruncate$set(allocateIfImplemented("ftruncate", this::ftruncate));
		fuseOperations.fgetattr$set(allocateIfImplemented("fgetattr", this::fgetattr));
		fuseOperations.lock$set(allocateIfImplemented("lock", this::lock));
		fuseOperations.utimens$set(allocateIfImplemented("utimens", this::utimens));
		fuseOperations.bmap$set(allocateIfImplemented("bmap", this::bmap));
		fuseOperations.ioctl$set(allocateIfImplemented("ioctl", this::ioctl));
		fuseOperations.poll$set(allocateIfImplemented("poll", this::poll));
		fuseOperations.write_buf$set(allocateIfImplemented("writeBuf", this::writeBuf));
		fuseOperations.read_buf$set(allocateIfImplemented("readBuf", this::readBuf));
		fuseOperations.flock$set(allocateIfImplemented("flock", this::flock));
		fuseOperations.fallocate$set(allocateIfImplemented("fallocate", this::fallocate));
	}
	
	private <T> Callback<T> allocateIfImplemented(String methodName, T callback) {
		if (notImplementedMethods.contains(methodName)) {
			return Callback.ofNull();
		} else {
			return scope.allocateCallback(callback);
		}
	}
	
	@Override
	public Pointer<Void> init(Pointer<fuse_common_h.fuse_conn_info> conn) {
		this.fuseHandle = fuse_lib.fuse_get_context().get().fuse$get();
		LOG.debug("init()");
		return Pointer.ofNull();
	}

	protected Pointer<fuse_h.fuse> getFuseHandle() {
		return fuseHandle;
	}

	public fuse_h.fuse_operations getFuseOperations() {
		return fuseOperations;
	}

	@Override
	public void destroy(Pointer<?> pointer) {
		this.fuseHandle = Pointer.ofNull();
		LOG.debug("destroy()");
	}

	@Override
	public void close() {
		scope.close();
	}
}
