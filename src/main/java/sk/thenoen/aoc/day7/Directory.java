package sk.thenoen.aoc.day7;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemNode {

	private final List<FileSystemNode> childNodes = new ArrayList<>();
	private Directory parentDirectory;

	public Directory(String name, Directory parentDirectory) {
		super(name);
		this.parentDirectory = parentDirectory;
	}

	public Directory getParentDirectory() {
		return parentDirectory;
	}

	public void addChildNode(FileSystemNode fileSystemNode) {
		childNodes.add(fileSystemNode);
	}

	public Directory getChildDirectory(String name) {
		return childNodes.stream()
						 .filter(Directory.class::isInstance)
						 .map(Directory.class::cast)
						 .filter(node -> node.getName().equals(name))
						 .findFirst()
						 .orElseThrow(() -> new RuntimeException("Can't find node with name: " + name));
	}

	@Override
	public long getSize() {
		return childNodes.stream()
						 .mapToLong(FileSystemNode::getSize)
						 .sum();
	}

	@Override
	public String toString() {
		return "Directory: " + getName();
	}
}
