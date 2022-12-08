package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import sk.thenoen.aoc.day7.Directory;
import sk.thenoen.aoc.day7.File;
import sk.thenoen.aoc.day7.FileSystemNode;

public class Day7 {

	private static final long DISK_SPACE = 70_000_000;
	private static final long REQUIRED_FREE_SPACE = 30_000_000;

	public long solvePart1(String inputPath) throws IOException {
		List<String> inputLines = loadInput(inputPath);
		List<FileSystemNode> allFsNodes = loadFileSystem(inputLines);

		return allFsNodes.stream()
						 .filter(Directory.class::isInstance)
						 .mapToLong(FileSystemNode::getSize)
						 .filter(size -> size < 100_000)
						 .sum();
	}

	public long solvePart2(String inputPath) throws IOException {
		List<String> inputLines = loadInput(inputPath);
		List<FileSystemNode> allFsNodes = loadFileSystem(inputLines);

		final FileSystemNode fsRootNode = allFsNodes.stream()
													.filter(node -> node.getName().equals("/"))
													.findFirst()
													.orElseThrow();

		final long minSpaceToFree = REQUIRED_FREE_SPACE - (DISK_SPACE - fsRootNode.getSize());

		return allFsNodes.stream()
						 .filter(Directory.class::isInstance)
						 .map(Directory.class::cast)
						 .sorted(Comparator.comparing(Directory::getSize))
						 .mapToLong(Directory::getSize)
						 .filter(size -> size > minSpaceToFree)
						 .findFirst()
						 .orElseThrow();

	}

	private List<String> loadInput(String inputPath) throws IOException {
		List<String> inputLines = new ArrayList<>();
		try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(inputPath)) {
			final Scanner scanner = new Scanner(inputStream);
			scanner.nextLine(); // First line of input can be skipped
			while (scanner.hasNextLine()) {
				inputLines.add(scanner.nextLine());
			}
		}
		return inputLines;
	}

	private static List<FileSystemNode> loadFileSystem(List<String> inputLines) {
		List<FileSystemNode> allFsNodes = new ArrayList<>();
		final Directory fsRootDirectory = new Directory("/", null);
		allFsNodes.add(fsRootDirectory);
		Directory currentDirectory = fsRootDirectory;

		for (String inputLine : inputLines) {
			final String[] inputParts = inputLine.split(" ");

			if (inputParts[0].equals("$") && inputParts[1].equals("cd")) {
				if (inputParts[2].equals("..")) {
					currentDirectory = currentDirectory.getParentDirectory();
				} else {
					currentDirectory = currentDirectory.getChildDirectory(inputParts[2]);
				}
				continue;
			}

			if (inputParts[0].equals("$") && inputParts[1].equals("ls")) {
				continue;
			}

			if (inputParts[0].equals("dir")) {
				final Directory directory = new Directory(inputParts[1], currentDirectory);
				allFsNodes.add(directory);
				currentDirectory.addChildNode(directory);
			} else {
				final File file = new File(inputParts[1], Long.parseLong(inputParts[0]));
				allFsNodes.add(file);
				currentDirectory.addChildNode(file);
			}
		}
		return allFsNodes;
	}

}
