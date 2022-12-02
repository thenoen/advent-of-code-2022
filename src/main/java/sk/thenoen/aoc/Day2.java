package sk.thenoen.aoc;

import java.io.InputStream;
import java.util.Scanner;

public class Day2 {

	public void solve() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.input.txt");
//		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.test-input.txt");

		Scanner scanner = new Scanner(input);

		long totalScore = 0;

		while (scanner.hasNextLine()) {
			String[] in = scanner.nextLine().split(" ");
			String p1 = map(in[0]);
			String p2 = map(in[1]);
			String round = p1 + p2;

			int roundResult = (compare(round) + 1) * 3;
			int symbolPoints = symbolToPoints(p2);
			totalScore += (roundResult + symbolPoints);
		}


		System.out.println("Solution 1:");
		System.out.println(totalScore);
	}

	private String map(String input) {
		return switch (input) {
			case "A" -> "R";
			case "B" -> "P";
			case "C" -> "S";

			case "X" -> "R";
			case "Y" -> "P";
			case "Z" -> "S";

			default -> throw new RuntimeException();
		};
	}

	private int symbolToPoints(String input) {
		return switch (input) {
			case "R" -> 1;
			case "P" -> 2;
			case "S" -> 3;

			default -> throw new RuntimeException();
		};
	}

	private int compare(String round) {
		return switch (round) {
			case "RR" -> 0;
			case "PP" -> 0;
			case "SS" -> 0;

			case "RP" -> 1;
			case "PS" -> 1;
			case "SR" -> 1;

			case "RS" -> -1;
			case "PR" -> -1;
			case "SP" -> -1;

			default -> throw new RuntimeException("Can't compare");
		};
	}

}
