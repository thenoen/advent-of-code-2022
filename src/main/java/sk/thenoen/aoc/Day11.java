package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day11 {

	public long solvePart1(String inputPath) throws IOException {

		final List<String> inputLines = loadInput(inputPath).stream()
															.filter(Predicate.not(String::isEmpty))
															.toList();
		final int monkeyCount = inputLines.size() / 6;

		Monkey[] monkeys = new Monkey[monkeyCount];

		for (int i = 0; i < monkeyCount; i++) {
			final Integer[] itemsInput = Arrays.stream(inputLines.get(1 + i * 6)
																 .replace(",", " ")
																 .split(" "))
											   .filter(Predicate.not(String::isEmpty))
											   .skip(2)
											   .map(Integer::valueOf)
											   .toArray(Integer[]::new); // it would be easier to skip first N characters in line

			final int testDivider = Integer.parseInt(inputLines.get(3 + i * 6).substring(21));

			monkeys[i] = new Monkey(i, testDivider, itemsInput);
		}

		for (int i = 0; i < monkeyCount; i++) {

			final int positiveTestTarget = parseTarget(inputLines, i, 4);
			final int negativeTestTarget = parseTarget(inputLines, i, 5);

			final Monkey monkey = monkeys[i];
			monkey.setPositiveTestTarget(monkeys[positiveTestTarget]);
			monkey.setNegativeTestTarget(monkeys[negativeTestTarget]);

			final String operationLine = inputLines.get(2 + i * 6).substring(11);
			final String[] operands = operationLine.split(" ");

			Function<Monkey, Integer> firstOperand = parseOperand(operands[3]);
			Function<Monkey, Integer> second = parseOperand(operands[5]);
			BiFunction<Integer, Integer, Integer> operation;
			if (operands[4].equals("*")) {
				operation = (o1, o2) -> o1 * o2;
			} else {
				operation = Integer::sum;
			}
			monkey.setWorryOperation(firstOperand, second, operation);
		}
		final int numberOfRounds = 20;
		for (int i = 0; i < numberOfRounds; i++) {
			for (int m = 0; m < monkeys.length; m++) {
				monkeys[m].takeTurn();
			}
		}

		final List<Integer> top2Monkeys = Arrays.stream(monkeys)
												.map(Monkey::getInspectionCount)
												.sorted(Integer::compareTo)
												.sorted(Comparator.reverseOrder())
												.limit(2)
												.toList();

		return top2Monkeys.get(0) * top2Monkeys.get(1);
	}

	private static Function<Monkey, Integer> parseOperand(String operand) {
		Function<Monkey, Integer> firstOperand;
		if (operand.equals("old")) {
			firstOperand = Monkey::getItemToProcess;
		} else {
			firstOperand = m -> Integer.parseInt(operand);
		}
		return firstOperand;
	}

	private static int parseTarget(List<String> inputLines, int i, int lineNumber) {
		final String[] line = inputLines.get(lineNumber + i * 6).split(" ");
		final String substring = line[line.length - 1];
		return Integer.parseInt(substring);
	}

	public long solvePart2(String inputPath) throws IOException {

		final List<String> inputLines = loadInput(inputPath);

		return -1;
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

	private static class Monkey {

		private int index;
		private int testDivider;
		Deque<Integer> items = new LinkedList<>();

		Monkey positiveTestTarget;
		Monkey negativeTestTarget;

		//		WorryOperation worryOperation;
		Function<Monkey, Integer> firstOperand;
		Function<Monkey, Integer> secondOperand;
		BiFunction<Integer, Integer, Integer> logic;

		Integer inspectionCount = 0;

		public Monkey(int index, int testDivider, Integer... items) {
			this.index = index;
			this.testDivider = testDivider;
			for (int item : items) {
				this.items.addLast(item);
			}
		}

		public void takeTurn() {
			while (!items.isEmpty()) {
				final Integer newValue = inspectItem() / 3;
				items.removeFirst();
				if (newValue % testDivider == 0) {
					positiveTestTarget.receiveItem(newValue);
				} else {
					negativeTestTarget.receiveItem(newValue);
				}
				inspectionCount++;
			}
		}

		public void setWorryOperation(Function<Monkey, Integer> firstOperand,
									  Function<Monkey, Integer> secondOperand,
									  BiFunction<Integer, Integer, Integer> logic) {
			this.firstOperand = firstOperand;
			this.secondOperand = secondOperand;
			this.logic = logic;
		}

		private Integer getItemToProcess() {
			return items.peekFirst();
		}

		private Integer inspectItem() {
			final Integer o1 = firstOperand.apply(this);
			final Integer o2 = secondOperand.apply(this);
			final Integer result = logic.apply(o1, o2);
			return result;
		}

		public void setPositiveTestTarget(Monkey positiveTestTarget) {
			this.positiveTestTarget = positiveTestTarget;
		}

		public void setNegativeTestTarget(Monkey negativeTestTarget) {
			this.negativeTestTarget = negativeTestTarget;
		}

		public Integer getInspectionCount() {
			return inspectionCount;
		}

		public void receiveItem(Integer item) {
			items.addLast(item);
		}

		@Override
		public String toString() {
			return "Monkey " + index + " - inspections: " + inspectionCount;
		}
	}
}
