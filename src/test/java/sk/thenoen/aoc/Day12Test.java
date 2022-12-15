package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day12Test {

	@Test
	void testPart1() throws IOException {
		final int solution = new Day12().solvePart1("day12.test-input.txt");
		assertEquals(31, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final int solution = new Day12().solvePart1("day12.input.txt");
		System.out.println("Solution 1: " + solution);
		//		assertEquals(?, solution);
	}

	@Test
	void testPart2() throws IOException {
		final int solution = new Day12().solvePart2("day12.test-input.txt");
		assertEquals(29, solution);
	}

	@Test
	void solvePart2() throws IOException {
		final int solution = new Day12().solvePart2("day12.input.txt");
	}
}