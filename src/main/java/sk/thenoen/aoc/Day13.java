package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day13 {

	public int solvePart1(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath).stream()
															.filter(Predicate.not(l -> l.isEmpty()))
															.toList();

		int solution = 0;

		for (int pairIndex = 1; pairIndex <= inputLines.size() / 2; pairIndex++) {
			System.out.println("Pair: " + pairIndex);

			final Scanner scanner1 = new Scanner(inputLines.get(pairIndex * 2 - 2));
			scanner1.useDelimiter("");
			final Packet packet1 = parseItem(scanner1);

			final Scanner scanner2 = new Scanner(inputLines.get(pairIndex * 2 - 1));
			scanner2.useDelimiter("");
			final Packet packet2 = parseItem(scanner2);

			if (packet1.compareTo(packet2) < 1) {
				solution += pairIndex;
			}

		}

		return solution;
	}

	private Packet parseItem(Scanner scanner) {
		final Packet packet = new Packet();
		while (scanner.hasNext()) {
			if (scanner.hasNext("\\[")) {
				scanner.next();
				packet.addChild(parseItem(scanner));
			}
			if (scanner.hasNext("\\]")) {
				scanner.next();
				return packet;
			}
			if (scanner.hasNextInt()) {
				String intString = "";
				while (scanner.hasNextInt()) {
					intString += scanner.nextInt();
				}
				packet.addChild(new Nr(Integer.parseInt(intString)));
			}
			if (scanner.hasNext(",")) {
				scanner.next();
			}
		}
		return packet;
	}

	public int solvePart2(String inputPath) throws IOException {
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

	private abstract class Item implements Comparable {

	}

	private class Packet extends Item {

		private List<Item> children = new ArrayList<>();

		public void addChild(Item item) {
			this.children.add(item);
		}

		@Override
		public String toString() {
			return "[" + children.stream().map(c -> c.toString()).collect(Collectors.joining(",")) + "]";
		}

		@Override
		public int compareTo(Object o) {
			if (o instanceof Packet packet) {
				final List<Item> thatChildren = packet.children;
				for (int i = 0; i < this.children.size(); i++) {
					if (thatChildren.size() < i + 1) {
						return 1;
					}
					if (this.children.get(i).compareTo(thatChildren.get(i)) > 0) {
						return 1;
					}
				}
				return 0;
			}
			if (o instanceof Nr nr) {
				if (this.children.isEmpty()) {
					return -1;
				}
				final Packet packet2 = new Packet();
				packet2.addChild(nr);

//				final Packet packet1 = new Packet();
//				packet1.addChild(this.children.get(0));
//
//				if (this.children.get(0) instanceof Packet) {
//
//				} else {
					return this.children.get(0).compareTo(packet2);
//				}

			}
			return 0;
		}
	}

	private class Nr extends Item {

		int value;

		public Nr(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@Override
		public int compareTo(Object o) {
			if (o instanceof Nr nr) {
				return Integer.compare(this.value, nr.value);
			}
			if (o instanceof Packet p) {
				if (p.children.isEmpty()) {
					return 1;
				}
				final Packet packet = new Packet();
				packet.addChild(this);

				if (p.children.get(0) instanceof Packet) {
					return packet.compareTo(p);
				} else {
					return this.compareTo(p.children.get(0));
				}

			}
			return 0;
		}
	}

}
