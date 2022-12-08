package sk.thenoen.aoc.day7;

public class File extends FileSystemNode{

	private long size;

	public File(String name, long size) {
		super(name);
		this.size = size;
	}

	@Override
	public long getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		return "file: " + getName() + " - size: " + this.size;
	}
}
