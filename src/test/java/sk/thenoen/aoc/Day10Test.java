package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

	@Test
	void testPart1() throws IOException {
		final long solution = new Day10().solvePart1("day10.test-input.txt");
		assertEquals(13140, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final long solution = new Day10().solvePart1("day10.input.txt");
		System.out.println("Solution 1: " + solution);
		assertEquals(15020, solution);
	}

	@Test
	void solvePart2() {
	}
}