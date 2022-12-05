package sk.thenoen.aoc;

import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day5 {

	public void solvePart1() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day5.input.txt");
		//		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day5.test-input.txt");

		Scanner scanner = new Scanner(input);

		List<String> stacksInputs = new ArrayList<>();
		int stacksCount = 0;

		Deque<String>[] stacks = loadCrateStacks(scanner, stacksInputs, stacksCount);

		while (scanner.hasNextLine()) {
			final String move = scanner.nextLine();
			final String[] moveParts = move.split("\s");
			final int count = Integer.parseInt(moveParts[1]);
			final int from = Integer.parseInt(moveParts[3]) - 1;
			final int to = Integer.parseInt(moveParts[5]) - 1;

			for (int i = 0; i < count; i++) {
				final String crate = stacks[from].removeLast();
				stacks[to].addLast(crate);
			}
		}

		printSolution(1, stacks);

	}

	public void solvePart2() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day5.input.txt"); // it is not closed - so what?
//				InputStream input = this.getClass().getClassLoader().getResourceAsStream("day5.test-input.txt");

		Scanner scanner = new Scanner(input);

		List<String> stacksInputs = new ArrayList<>();
		int stacksCount = 0;

		Deque<String>[] stacks = loadCrateStacks(scanner, stacksInputs, stacksCount);

		while (scanner.hasNextLine()) {
			final String move = scanner.nextLine();
			final String[] moveParts = move.split("\s");
			final int count = Integer.parseInt(moveParts[1]);
			final int from = Integer.parseInt(moveParts[3]) - 1;
			final int to = Integer.parseInt(moveParts[5]) - 1;

			LinkedList<String> crateBuffer = new LinkedList<>(); // I am lazy!

			for (int i = 0; i < count; i++) {
				final String crate = stacks[from].removeLast();
				crateBuffer.addLast(crate);
			}

			final int bufferSize = crateBuffer.size();
			for (int i = 0; i < bufferSize; i++) {
				final String crate = crateBuffer.removeLast();
				stacks[to].addLast(crate);
			}
		}

		printSolution(2, stacks);

	}

	private static void printSolution(int solution, Deque<String>[] stacks) {
		StringBuilder resultBuilder = new StringBuilder();
		for (Deque<String> stack : stacks) {
			resultBuilder.append(stack.getLast());
		}

		System.out.println("Solution " + solution + ":");
		System.out.println(resultBuilder);
	}

	private static Deque<String>[] loadCrateStacks(Scanner scanner, List<String> stacksInputs, int stacksCount) {
		while (true) {
			final String line = scanner.nextLine();
			if (line.trim().startsWith("[")) {
				stacksInputs.add(line);
				continue;
			}

			if (line.isEmpty()) {
				break;
			}

			final String[] split = line.trim().split("\s");
			stacksCount = Integer.parseInt(split[split.length - 1]);
		}

		Deque<String>[] stacks = new Deque[stacksCount];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = new LinkedList<>();
		}

		for (String stacksInput : stacksInputs) {

			for (int i = 0; i < stacksCount; i++) {
				int inputStackIndex = (i + 1) * 4 - 2;
				if (inputStackIndex < stacksInput.length()) {
					final String crateLabel = stacksInput.substring(inputStackIndex - 1, inputStackIndex);
					if (!crateLabel.isBlank()) {
						stacks[i].addFirst(crateLabel);
					}
				}
			}
		}
		return stacks;
	}
}
