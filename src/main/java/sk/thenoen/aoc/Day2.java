package sk.thenoen.aoc;

import java.io.InputStream;
import java.util.Scanner;

public class Day2 {

	public void solvePart1() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.input.txt");
		//		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.test-input.txt");

		Scanner scanner = new Scanner(input);

		long totalScore = 0;

		while (scanner.hasNextLine()) {
			String[] in = scanner.nextLine().split(" ");
			String p1 = mapToStandardSymbols(in[0]);
			String p2 = mapToStandardSymbols(in[1]);
			String round = p1 + p2;

			int roundResult = (compare(round) + 1) * 3;
			int symbolPoints = symbolToPoints(p2);
			totalScore += (roundResult + symbolPoints);
		}

		System.out.println("Solution 1:");
		System.out.println(totalScore);
	}

	public void solvePart2() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.input.txt");
//				InputStream input = this.getClass().getClassLoader().getResourceAsStream("day2.test-input.txt");

		Scanner scanner = new Scanner(input);

		long totalScore = 0;

		while (scanner.hasNextLine()) {
			String[] in = scanner.nextLine().split(" ");
			String p1 = mapToStandardSymbols(in[0]);
			String target = in[1];

			final String roundSolution = findRoundSolution(p1, target);

			int roundResult = (compare(p1 + roundSolution) + 1) * 3;
			int symbolPoints = symbolToPoints(roundSolution);
			totalScore += (roundResult + symbolPoints);
		}

		System.out.println("Solution 2:");
		System.out.println(totalScore);
	}

	private String mapToStandardSymbols(String input) {
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

	private String findRoundSolution(String oponent, String target) {
		String input = oponent + target;

		/*
			X - loose
			Y - draw
			Z - win
		*/
		return switch (input) {
			case "RX" -> "S";
			case "RY" -> "R";
			case "RZ" -> "P";

			case "PY" -> "P";
			case "PZ" -> "S";
			case "PX" -> "R";

			case "SZ" -> "R";
			case "SX" -> "P";
			case "SY" -> "S";

			default -> throw new RuntimeException("Can't find solution");
		};
	}
}
