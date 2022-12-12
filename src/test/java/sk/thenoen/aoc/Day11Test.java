package sk.thenoen.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

	@Test
	void testPart1() throws IOException {
		final long solution = new Day11().solvePart1("day11.test-input.txt");
		assertEquals(10605, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final long solution = new Day11().solvePart1("day11.input.txt");
		System.out.println("Solution 1: " + solution);
		assertEquals(58794, solution);
	}

	@Test
	void testPart2() throws IOException {
		final long solution = new Day11().solvePart2("day11.test-input.txt");
		assertEquals(2713310158L, solution);
	}

	@Test
	void solvePart2() throws IOException {
		final long solution = new Day11().solvePart2("day11.input.txt");
		System.out.println("Solution 1: " + solution);
//		assertEquals(???, solution);
	}
}