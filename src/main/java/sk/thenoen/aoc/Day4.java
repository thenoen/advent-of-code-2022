package sk.thenoen.aoc;

import java.io.InputStream;
import java.util.Scanner;

public class Day4 {

	public void solvePart1() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day4.input.txt");
		//								InputStream input = this.getClass().getClassLoader().getResourceAsStream("day4.test-input.txt");

		Scanner scanner = new Scanner(input);

		long count = 0;

		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			final String[] ranges = line.split(",");
			final String[] range1String = ranges[0].split("-");
			final String[] range2String = ranges[1].split("-");

			final int[] range1 = new int[2];
			range1[0] = Integer.parseInt(range1String[0]);
			range1[1] = Integer.parseInt(range1String[1]);

			final int[] range2 = new int[2];
			range2[0] = Integer.parseInt(range2String[0]);
			range2[1] = Integer.parseInt(range2String[1]);

			if (range1[0] <= range2[0]) {
				if (range2[1] <= range1[1]) {
					count++;
					continue;
				}
			}

			if (range2[0] <= range1[0]) {
				if (range1[1] <= range2[1]) {
					count++;
				}
			}

			//			System.out.println(line);
		}

		System.out.println("Solution 1:");
		System.out.println(count);
		// 505 too low
		// 625 too high
	}

	public void solvePart2() {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day4.input.txt");
		//								InputStream input = this.getClass().getClassLoader().getResourceAsStream("day4.test-input.txt");

		Scanner scanner = new Scanner(input);

		long count = 0;

		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			final String[] ranges = line.split(",");
			final String[] range1String = ranges[0].split("-");
			final String[] range2String = ranges[1].split("-");

			final int[] rangeA = new int[2];
			rangeA[0] = Integer.parseInt(range1String[0]);
			rangeA[1] = Integer.parseInt(range1String[1]);

			final int[] rangeB = new int[2];
			rangeB[0] = Integer.parseInt(range2String[0]);
			rangeB[1] = Integer.parseInt(range2String[1]);

			if (rangeA[0] >= rangeB[0] && rangeA[0] <= rangeB[1]) {
				count++;
				continue;
			}

			if (rangeA[1] >= rangeB[0] && rangeA[1] <= rangeB[1]) {
				count++;
				continue;
			}

			if (rangeB[0] >= rangeA[0] && rangeB[0] <= rangeA[1]) {
				count++;
				continue;
			}

			if (rangeB[1] >= rangeA[0] && rangeB[1] <= rangeA[1]) {
				count++;
				continue;
			}
		}

		System.out.println("Solution 2:");
		System.out.println(count);
		// 757 too low
	}

}
