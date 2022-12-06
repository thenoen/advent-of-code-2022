package sk.thenoen.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test {

	@Test
	void testPart1Sample1() {
		Assertions.assertEquals(7, new Day6().solvePart1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
	}

	@Test
	void testPart1Sample2() {
		Assertions.assertEquals(5, new Day6().solvePart1("bvwbjplbgvbhsrlpgdmjqwftvncz"));
	}

	@Test
	void testPart1Sample3() {
		Assertions.assertEquals(6, new Day6().solvePart1("nppdvjthqldpwncqszvftbrmjlhg"));
	}

	@Test
	void testPart1Sample4() {
		Assertions.assertEquals(10, new Day6().solvePart1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
	}

	@Test
	void testPart1Sample5() {
		Assertions.assertEquals(11, new Day6().solvePart1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
	}

//	@Disabled
	@Test
	void solvePart1() throws IOException {

		String input;

		try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("day6.input.txt")) {
			Scanner s = new Scanner(inputStream);
			List<String> inputLines = new ArrayList<>();
			while (s.hasNextLine()) {
				inputLines.add(s.nextLine());
			}
			input = String.join("\n", inputLines);
		}

		final long solution = new Day6().solvePart1(input);

		System.out.println("Solution 1:");
		System.out.println(solution);
	}
}