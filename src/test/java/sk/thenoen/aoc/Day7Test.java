package sk.thenoen.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class Day7Test {

	@Test
	void testPart1() throws IOException {
		final long solution = new Day7().solvePart1("day7.test-input.txt");
		Assertions.assertEquals(95437, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final long solution = new Day7().solvePart1("day7.input.txt");
		System.out.println("Solution 1: " + solution);
	}

	@Test
	void testPart2() throws IOException {
		final long solution = new Day7().solvePart2("day7.test-input.txt");
		Assertions.assertEquals(24933642, solution);
	}

	@Test
	void solvePart2() throws IOException {
		final long solution = new Day7().solvePart2("day7.input.txt");
		System.out.println("Solution 2: " + solution);
	}
}