package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sk.thenoen.aoc.day8.Tree;

public class Day8 {

	public long solvePart1(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);
		int width = inputLines.get(0).length();
		int height = inputLines.size();

		Tree[][] forest = new Tree[inputLines.size()][];

		for (int y = 0; y < height; y++) {
			final char[] treeHeights = inputLines.get(y).toCharArray();
			forest[y] = new Tree[treeHeights.length];

			for (int x = 0; x < width; x++) {
				final Tree tree = new Tree();
				tree.setHeight(Integer.parseInt(String.valueOf(treeHeights[x])));
				forest[y][x] = tree;
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(forest[y][x]);
			}
			System.out.println();
		}

		// ->
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				final Tree neighbourTree = forest[y][x - 1];
				forest[y][x].setMaxLeftHeight(Math.max(neighbourTree.getMaxLeftHeight(), neighbourTree.getHeight()));
			}
		}

		// <-
		for (int y = 1; y < height - 1; y++) {
			for (int x = width - 2; x > 0; x--) {
				final Tree neighbourTree = forest[y][x + 1];
				forest[y][x].setMaxRightHeight(Math.max(neighbourTree.getMaxRightHeight(), neighbourTree.getHeight()));
			}
		}

		// V
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				final Tree neighbourTree = forest[y - 1][x];
				forest[y][x].setMaxTopHeight(Math.max(neighbourTree.getMaxTopHeight(), neighbourTree.getHeight()));
			}
		}

		// ^
		for (int x = 1; x < width - 1; x++) {
			for (int y = height - 2; y > 0; y--) {
				final Tree neighbourTree = forest[y + 1][x];
				forest[y][x].setMaxBottomHeight(Math.max(neighbourTree.getMaxBottomHeight(), neighbourTree.getHeight()));
			}
		}

		long visibleCount = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (forest[y][x].isVisible()) {
					visibleCount++;
					System.out.print(1);
				} else {
					System.out.print(0);
				}
			}
			System.out.println();
		}

		return visibleCount;
	}

	public int solvePart2(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);
		int width = inputLines.get(0).length();
		int height = inputLines.size();

		Tree[][] forest = new Tree[inputLines.size()][];

		for (int y = 0; y < height; y++) {
			final char[] treeHeights = inputLines.get(y).toCharArray();
			forest[y] = new Tree[treeHeights.length];

			for (int x = 0; x < width; x++) {
				final Tree tree = new Tree();
				tree.setHeight(Integer.parseInt(String.valueOf(treeHeights[x])));
				forest[y][x] = tree;
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				calculateTopView(x, y, forest);
				calculateBottomView(y, x, forest);
				calculateRightView(y, x, forest);
				calculateLeftView(y, x, forest);
			}
		}

		int bestViewScore = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final Tree tree = forest[y][x];
				final int treeViewScore = tree.getViewScore();
				bestViewScore = Math.max(bestViewScore, treeViewScore);
			}
		}

		return bestViewScore;
	}

	public void calculateTopView(int h, int w, Tree[][] forest) {
		final Tree tree = forest[h][w];
		for (int y = h - 1; y >= 0; y--) {
			if (tree.getHeight() <= forest[y][w].getHeight()) {
				tree.setTopViewScore(h - y);
				return;
			}
		}
		tree.setTopViewScore(h);
	}

	public void calculateBottomView(int h, int w, Tree[][] forest) {
		final Tree tree = forest[h][w];
		for (int y = h + 1; y < forest.length; y++) {
			if (tree.getHeight() <= forest[y][w].getHeight()) {
				tree.setBottomViewScore(y - h);
				return;
			}
		}
		tree.setBottomViewScore(forest.length - h - 1);
	}

	public void calculateRightView(int h, int w, Tree[][] forest) {
		final Tree tree = forest[h][w];
		for (int x = w + 1; x < forest[h].length; x++) {
			if (tree.getHeight() <= forest[h][x].getHeight()) {
				tree.setRightViewScore(x - w);
				return;
			}
		}
		tree.setRightViewScore(forest[h].length - w - 1);
	}

	public void calculateLeftView(int h, int w, Tree[][] forest) {
		final Tree tree = forest[h][w];
		for (int x = w - 1; x >= 0; x--) {
			if (tree.getHeight() <= forest[h][x].getHeight()) {
				tree.setLeftViewScore(w - x);
				return;
			}
		}
		tree.setLeftViewScore(w);
	}

	private List<String> loadInput(String inputPath) throws IOException {
		List<String> inputLines = new ArrayList<>();
		try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(inputPath)) {
			final Scanner scanner = new Scanner(inputStream);
			while (scanner.hasNextLine()) {
				inputLines.add(scanner.nextLine());
			}
		}
		return inputLines;
	}

}
