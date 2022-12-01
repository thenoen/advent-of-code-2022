package sk.thenoen.aoc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.OptionalLong;
import java.util.Scanner;

public class Main {

	public void solve() {
		final ArrayList<Long> elfs = loadInput();

		System.out.println("Solution 1:");
		final OptionalLong max = elfs.stream()
									 .mapToLong(Long::longValue)
									 .max();
		System.out.println(max.getAsLong());

		System.out.println("-----------------------------------------");
		System.out.println("Solution 2:");
		final long sum = elfs.stream()
							 .sorted(Long::compareTo)
							 .sorted(Comparator.reverseOrder())
							 .mapToLong(Long::longValue)
							 .limit(3)
							 .sum();
		System.out.println(sum);

	}

	private ArrayList<Long> loadInput() {
		final InputStream input = this.getClass().getClassLoader().getResourceAsStream("day1.input.txt");

		Scanner scanner = new Scanner(input);

		final ArrayList<Long> elfs = new ArrayList<>();

		long calories = 0;

		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			if (line.length() > 1) {
				calories += Long.parseLong(line);
			} else {
				elfs.add(calories);
				calories = 0;
			}
		}
		return elfs;
	}
}
