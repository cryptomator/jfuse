package org.cryptomator.jfuse.tests;

import jnr.constants.platform.Errno;
import jnr.ffi.Pointer;
import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.examples.RandomFileStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseFileInfo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@SuppressWarnings("OctalInteger")
public class RandomJnrFileSystem extends FuseStubFS {

	private static final int S_IFDIR = 0040000;
	private static final int S_IFREG = 0100000;

	private static final Logger LOG = LoggerFactory.getLogger(RandomJnrFileSystem.class);

	private final RandomFileStructure rfs = RandomFileStructure.init(42L, 100);

	public static void main(String[] args) {
		Path mountPoint = Path.of("/Volumes/bar");
		LOG.info("mounting at {}. Unmount to terminate this process.", mountPoint);
		new RandomJnrFileSystem().mount(mountPoint, true, false, new String[]{"-s"});
	}

	@Override
	public int getattr(String path, FileStat stat) {
		LOG.debug("getattr() {}", path);
		var node = rfs.getNode(path);
		if (node == null) {
			return -Errno.ENOENT.value();
		} else {
			fillStats(node, stat);
			return 0;
		}
	}

	private void fillStats(RandomFileStructure.Node node, FileStat stat){
		if (node.isDir()) {
			stat.st_mode.set(S_IFDIR | 0755);
			stat.st_nlink.set((short) (2 + node.children().size()));
			stat.st_mtim.tv_sec.set(node.lastModified().getEpochSecond());
			stat.st_mtim.tv_nsec.set(node.lastModified().getNano());
		} else {
			stat.st_mode.set(S_IFREG | 0444);
			stat.st_nlink.set((short) 1);
			stat.st_size.set(0);
			stat.st_mtim.tv_sec.set(node.lastModified().getEpochSecond());
			stat.st_mtim.tv_nsec.set(node.lastModified().getNano());
		}
	}

	@Override
	public int readdir(String path, Pointer buf, FuseFillDir filler, long offset, FuseFileInfo fi) {
		LOG.debug("readdir() {}", path);
		var node = rfs.getNode(path);
		if (node == null) {
			return -Errno.ENOENT.value();
		} else if (!node.isDir()) {
			return -Errno.ENOTDIR.value();
		} else {
			var runtime = jnr.ffi.Runtime.getSystemRuntime();
			if (offset == 0 && filler.apply(buf, ".", null, ++offset) != 0)
				return 0;
			if (offset == 1 && filler.apply(buf, "..", null, ++offset) != 0)
				return 0;
			assert offset > 1;
			var childIter = node.children().values().stream().skip(offset - 2).iterator();
			while (childIter.hasNext()) {
				var child = childIter.next();
				var stat = new FileStat(runtime);
				fillStats(node, stat);
				if (filler.apply(buf, child.name(), stat, ++offset) != 0) {
					return 0;
				}
			}
			return 0;
		}
	}

}
