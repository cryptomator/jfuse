package org.cryptomator.jfuse.examples;

import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.concurrent.CompletionException;

public final class MirrorWinFileSystem extends AbstractMirrorFileSystem {

	private static final Logger LOG = LoggerFactory.getLogger(MirrorWinFileSystem.class);

	public static void main(String[] args) {
		if (args.length != 2) {
			LOG.error("Invalid number of arguments. Expected {mirroredDir} {driveLetter}.");
		}
		Path mirrored = Path.of(args[0]);
		Path mountPoint = Path.of(args[1]);
		var builder = Fuse.builder();
		try (var fuse = builder.build(new MirrorWinFileSystem(mirrored, builder.errno()))) {
			LOG.info("Mounting at {}...", mountPoint);
			int result = fuse.mount("jfuse", mountPoint, "-s", "-ouid=-1", "-ogid=11", "-ovolname=mirror");
			if (result == 0) {
				LOG.info("Mounted to {}. Unmount to terminate this process", mountPoint);
			} else {
				LOG.error("Failed to mount to {}. Exit code: {}", mountPoint, result);
			}
		} catch (CompletionException e) {
			LOG.error("Un/Mounting failed. ", e);
			System.exit(1);
		} catch (IOException e) {
			LOG.error("Failed to create mirror", e);
			System.exit(1);
		}
	}

	public MirrorWinFileSystem(Path root, Errno errno) throws IOException {
		super(root, errno, Files.getFileStore(root));
	}

	@Override
	protected DosFileAttributes readAttributes(Path node) throws IOException {
		return Files.readAttributes(node, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
	}

	@Override
	protected void createDir(Path node, FileAttribute<Set<PosixFilePermission>> permissions) throws IOException {
		Files.createDirectory(node);
	}

	@Override
	protected FileChannel openFileChannel(Path node, Set<? extends OpenOption> openOptions, FileAttribute<?>... attrs) throws IOException {
		return FileChannel.open(node, openOptions);
	}

}