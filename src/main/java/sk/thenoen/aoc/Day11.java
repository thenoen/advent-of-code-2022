package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day11 {

	public BigInteger solvePart1(String inputPath) throws IOException {

		final List<String> inputLines = loadInput(inputPath).stream()
															.filter(Predicate.not(String::isEmpty))
															.toList();
		Monkey[] monkeys = loadMonkeys(inputLines);
		final List<Item> items = Arrays.stream(monkeys)
									   .map(Monkey::getItems)
									   .flatMap(List::stream)
									   .toList();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setId(i);
		}

		final int numberOfRounds = 20;
		for (int i = 0; i < numberOfRounds; i++) {
			for (int m = 0; m < monkeys.length; m++) {
				monkeys[m].takeTurn();
			}
		}

		final List<BigInteger> top2Monkeys = Arrays.stream(monkeys)
												   .map(Monkey::getInspectionCount)
												   .sorted(BigInteger::compareTo)
												   .sorted(Comparator.reverseOrder())
												   .limit(2)
												   .toList();

		// 246 * 239 - correct
		return top2Monkeys.get(0).multiply(top2Monkeys.get(1));
	}

	public BigInteger solvePart2(String inputPath, int numberOfRounds) throws IOException {

		final List<String> inputLines = loadInput(inputPath).stream()
															.filter(Predicate.not(String::isEmpty))
															.toList();
		Monkey[] monkeys = loadMonkeys(inputLines);
		final List<Item> items = Arrays.stream(monkeys)
									   .map(Monkey::getItems)
									   .flatMap(List::stream)
									   .toList();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setId(i);
		}

		for (int i = 0; i < numberOfRounds; i++) {
			if (i % 10 == 0) {
				System.out.println("Round: " + i);
			}
			for (int m = 0; m < monkeys.length; m++) {
				monkeys[m].takeTurn2();
			}
		}

		final List<BigInteger> top2Monkeys = Arrays.stream(monkeys)
												   .map(Monkey::getInspectionCount)
												   .sorted(BigInteger::compareTo)
												   .sorted(Comparator.reverseOrder())
												   .limit(2)
												   .toList();

		//		final String historyString = monkeys[0].getItemToProcess().printHistory();
		//		findPatterns(historyString);

		return top2Monkeys.get(0).multiply(top2Monkeys.get(1));
	}

	private static Monkey[] loadMonkeys(List<String> inputLines) {

		final int monkeyCount = inputLines.size() / 6;
		Monkey[] monkeys = new Monkey[monkeyCount];

		for (int i = 0; i < monkeyCount; i++) {
			final Item[] itemsInput = Arrays.stream(inputLines.get(1 + i * 6)
															  .replace(",", " ")
															  .split(" "))
											.filter(Predicate.not(String::isEmpty))
											.skip(2)
											.map(BigInteger::new)
											.map(Item::new)
											.toArray(Item[]::new); // it would be easier to skip first N characters in line

			final int testLine = 3 + i * 6;
			final String testFormula = inputLines.get(testLine);
			final int testDivider = Integer.parseInt(testFormula.substring(21));

			monkeys[i] = new Monkey(i, testDivider, itemsInput);
		}

		for (int i = 0; i < monkeyCount; i++) {

			final int positiveTestTarget = parseTargetMonkeyIndex(inputLines, i, 4);
			final int negativeTestTarget = parseTargetMonkeyIndex(inputLines, i, 5);

			final Monkey monkey = monkeys[i];
			monkey.setPositiveTestTarget(monkeys[positiveTestTarget]);
			monkey.setNegativeTestTarget(monkeys[negativeTestTarget]);

			final String operationLine = inputLines.get(2 + i * 6).substring(11);
			final String[] operands = operationLine.split(" ");

			Function<Monkey, BigInteger> firstOperand = parseOperand(operands[3]);
			Function<Monkey, BigInteger> second = parseOperand(operands[5]);
			BiFunction<BigInteger, BigInteger, BigInteger> operation;
			if (operands[4].equals("*")) {
				operation = (o1, o2) -> o1.multiply(o2);
			} else {
				operation = BigInteger::add;
			}
			monkey.setWorryOperation(firstOperand, second, operation);
		}
		return monkeys;
	}

	private static Function<Monkey, BigInteger> parseOperand(String operand) {
		Function<Monkey, BigInteger> firstOperand;
		if (operand.equals("old")) {
			firstOperand = m -> m.getItemToProcess().getWorryLevel();
		} else {
			firstOperand = m -> new BigInteger(operand);
		}
		return firstOperand;
	}

	private static int parseTargetMonkeyIndex(List<String> inputLines, int i, int lineNumber) {
		final String[] line = inputLines.get(lineNumber + i * 6).split(" ");
		final String substring = line[line.length - 1];
		return Integer.parseInt(substring);
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

	private void findPatterns(String string) {
		for (int i = 1; i < string.length() / 2; i++) {
			final String patternA = string.substring(0, i);
			final int nextOccurrence = string.indexOf(patternA, i);
			if (nextOccurrence == patternA.length() * 2) {
				System.out.println("pattern A: " + patternA);
				final String patternB = string.substring(i, nextOccurrence);
				System.out.println("pattern B: " + patternB);
				System.out.println("---");
			}
		}
	}

	private static class Monkey {

		private int index;
		private long testDivider;
		Deque<Item> items = new LinkedList<>();

		Monkey positiveTestTarget;
		Monkey negativeTestTarget;

		Function<Monkey, BigInteger> firstOperand;
		Function<Monkey, BigInteger> secondOperand;
		BiFunction<BigInteger, BigInteger, BigInteger> logic;

		BigInteger inspectionCount = BigInteger.ZERO;

		public Monkey(int index, int testDivider, Item... items) {
			this.index = index;
			this.testDivider = testDivider;
			for (Item item : items) {
				this.items.addLast(item);
			}
		}

		public void takeTurn() {
			while (!items.isEmpty()) {
				final BigInteger newValue = inspectItem().divide(BigInteger.valueOf(3L));
				final Item inspectedItem = items.removeFirst();
				inspectedItem.setWorryLevel(newValue);
				if (newValue.mod(BigInteger.valueOf(testDivider)).equals(BigInteger.ZERO)) {
					positiveTestTarget.receiveItem(inspectedItem);
				} else {
					negativeTestTarget.receiveItem(inspectedItem);
				}
				inspectionCount = inspectionCount.add(BigInteger.ONE);
			}
		}

		public void takeTurn2() {
			while (!items.isEmpty()) {
				final BigInteger newValue = inspectItem();
				if (newValue.compareTo(items.peekFirst().getWorryLevel()) < 0) {
					System.out.println("error: new value is lower");
				}
				final Item removedItem = items.removeFirst();
				removedItem.setWorryLevel(newValue);
				if (newValue.mod(BigInteger.valueOf(testDivider)).compareTo(BigInteger.ZERO) == 0) {
					positiveTestTarget.receiveItem(removedItem);
				} else {
					negativeTestTarget.receiveItem(removedItem);
				}
				inspectionCount = inspectionCount.add(BigInteger.ONE);
			}
		}

		public void setWorryOperation(Function<Monkey, BigInteger> firstOperand,
									  Function<Monkey, BigInteger> secondOperand,
									  BiFunction<BigInteger, BigInteger, BigInteger> logic) {
			this.firstOperand = firstOperand;
			this.secondOperand = secondOperand;
			this.logic = logic;
		}

		private Item getItemToProcess() {
			return items.peekFirst();
		}

		public List<Item> getItems() {
			return items.stream().toList();
		}

		private BigInteger inspectItem() {
			final BigInteger o1 = firstOperand.apply(this);
			final BigInteger o2 = secondOperand.apply(this);
			final BigInteger result = logic.apply(o1, o2);
			return result;
		}

		public void setPositiveTestTarget(Monkey positiveTestTarget) {
			this.positiveTestTarget = positiveTestTarget;
		}

		public void setNegativeTestTarget(Monkey negativeTestTarget) {
			this.negativeTestTarget = negativeTestTarget;
		}

		public BigInteger getInspectionCount() {
			return inspectionCount;
		}

		public int getIndex() {
			return index;
		}

		public void receiveItem(Item item) {
			items.addLast(item);
			item.addToHistory(this);
		}

		@Override
		public String toString() {
			return "Monkey " + index + " - inspections: " + inspectionCount + " (" + items.size() + ")";
		}
	}

	private static class Item {

		private int id;
		private BigInteger worryLevel;
		private List<Integer> monkeyIndexHistory = new ArrayList<>();

		private String patternA;
		private String patternB;
		private Map<Integer, BigInteger> worryLevelCache = new HashMap<>();
		private List<String> monkeyLevelCache = new ArrayList<>();

		public Item(BigInteger worryLevel) {
			this.worryLevel = worryLevel;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public BigInteger getWorryLevel() {
			return worryLevel;
		}

		public void setWorryLevel(BigInteger worryLevel) {
			this.worryLevel = worryLevel;
		}

		public void addToHistory(Monkey monkey) {
			this.monkeyIndexHistory.add(monkey.getIndex());
			if (patternA == null && patternB == null) {
				worryLevelCache.put(monkeyIndexHistory.size(), worryLevel);
				monkeyLevelCache.add(monkey.getIndex() + " -  " + this.worryLevel);
				findPatterns();
			} else {
				//decrease worryLevel
				final String printedHistory = printHistory();
				if (printedHistory.startsWith(patternA)) {
					worryLevel = worryLevelCache.get(patternA.length() + 1);
					monkeyIndexHistory = monkeyIndexHistory.subList(patternA.length(), monkeyIndexHistory.size());
				} else if (printedHistory.startsWith(patternB)) {
					worryLevel = worryLevelCache.get(patternB.length());
					monkeyIndexHistory = monkeyIndexHistory.subList(patternB.length(), monkeyIndexHistory.size());
				}

			}
		}

		private void findPatterns() {
			final String historyString = printHistory();
			for (int i = 1; i < historyString.length() / 2; i++) {
				final String patternA = historyString.substring(0, i);
				final int nextOccurrence = historyString.indexOf(patternA, i);
				if (nextOccurrence == patternA.length() * 2) {
					this.patternA = patternA;
					final String patternB = historyString.substring(i, nextOccurrence);
					this.patternB = patternB;
					System.out.println(this);
					System.out.println("pattern A: " + patternA);
					System.out.println("pattern B: " + patternB);
					System.out.println("---");
				}
			}
		}

		public String printHistory() {
			final String join = this.monkeyIndexHistory.stream()
													   .map(String::valueOf)
													   .collect(Collectors.joining(""));
			//			System.out.println("History:\n" + join);
			return join;
		}

		@Override
		public String toString() {
			String prefix = "Item " + this.id;
			if (patternA != null && patternB != null) {
				prefix += " - pattern length: " + patternA.length();
			}
			return prefix;
		}
	}
}
