package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

	@Test
	void testPart1() throws IOException {
		final int solution = new Day12().solvePart1("day12.test-input.txt");
		assertEquals(31, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final int solution = new Day12().solvePart1("day12.input.txt");
		//		assertEquals(?, solution);
	}

	@Test
	void testPart2() throws IOException {
		final int solution = new Day12().solvePart1("day12.test-input.txt");
	}

	@Test
	void solvePart2() throws IOException {
		final int solution = new Day12().solvePart1("day12.input.txt");
	}
}