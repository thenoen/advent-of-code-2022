package sk.thenoen.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class Day8Test {

	@Test
	void testPart1() throws IOException {
		final long solution = new Day8().solvePart1("day8.test-input.txt");
		Assertions.assertEquals(21, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final long solution = new Day8().solvePart1("day8.input.txt");
		System.out.println("Solution 1: " + solution);
	}

	@Test
	void testPart2() throws IOException {
		final long solution = new Day8().solvePart2("day8.test-input.txt");
		Assertions.assertEquals(8, solution);
	}

	@Test
	void solvePart2() throws IOException {
		final long solution = new Day8().solvePart2("day8.input.txt");
		System.out.println("Solution 2: " + solution);
	}
}