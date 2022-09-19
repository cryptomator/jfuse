package org.cryptomator.jfuse.examples;

import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * A pseudo-randomized readonly file tree.
 */
public class RandomFileStructure {

	private static final SortedMap<String, Node> NO_CHILDREN = Collections.unmodifiableSortedMap(new TreeMap<>());

	private final Node root;

	private RandomFileStructure(Node root) {
		this.root = root;
	}

	public static RandomFileStructure init(long seed, int maxChildren) {
		RandomGenerator rng = RandomGeneratorFactory.getDefault().create(seed);
		var children = new TreeMap<String, Node>();
		for (int i = 0; i < maxChildren; i++) {
			var child = genNode(rng, maxChildren / 2);
			children.put(child.name, child);
		}
		var root = new Node("",true, Instant.EPOCH, 0, children);
		return new RandomFileStructure(root);
	}

	private static Node genNode(RandomGenerator rng, int nChildren) {
		var bytes = new byte[15];
		rng.nextBytes(bytes);
		var name = Base64.getUrlEncoder().encodeToString(bytes);
		var isDir = rng.nextInt(10) < 1; // 10% directories
		var lastModified = Instant.ofEpochSecond(rng.nextLong(0, 32503676399L));
		if (isDir) {
			var children = new TreeMap<String, Node>();
			for (int i = 0; i < nChildren; i++) {
				var child = genNode(rng, nChildren / 4);
				children.put(child.name, child);
			}
			return new Node(name, true, lastModified, 0, children);
		} else {
			var size = rng.nextInt(0, 500_000_000);
			return new Node(name, false, lastModified, size, NO_CHILDREN);
		}
	}

	public Node getNode(String absPath) {
		assert absPath.indexOf('/') == 0;
		var subPath = absPath.substring(1);
		return getNode(root, subPath);
	}

	private Node getNode(Node base, String relPath) {
		int idx = relPath.indexOf('/');
		var current = idx == -1 ? relPath : relPath.substring(0, idx);
		var remaining = idx == -1 ? "" : relPath.substring(idx + 1);
		if (current.isEmpty()) {
			return base;
		} else {
			var child = base.children.get(current);
			if (child == null) {
				return null;
			} else {
				return getNode(child, remaining);
			}
		}
	}

	public record Node (String name, boolean isDir, Instant lastModified, long size, SortedMap<String, Node> children) {
	}

}
