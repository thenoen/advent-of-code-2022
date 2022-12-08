package sk.thenoen.aoc.day7;

public abstract class FileSystemNode {

	private String name;

	public FileSystemNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract long getSize();

}
