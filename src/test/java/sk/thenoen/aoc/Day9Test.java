package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test {

	@Test
	void testPart1() throws IOException {
		final int solution = new Day9().solvePart1("day9.test-input.txt");
		assertEquals(13, solution);
	}

	@Test
	void testPart1v2() throws IOException {
		final int solution2 = new Day9().solvePart2("day9.test-input.txt", 2);
		assertEquals(13, solution2);
	}

	@Test
	void solvePart1() throws IOException {
		final int solution = new Day9().solvePart1("day9.input.txt");
		System.out.println("Solution 1: " + solution); // 6269
		assertEquals(6269, solution);
	}

	@Test
	void testPart2a() throws IOException {
		final int solution = new Day9().solvePart2("day9.test-input.txt", 10);
		assertEquals(1, solution);
	}

	@Test
	void testPart2b() throws IOException {
		final int solution = new Day9().solvePart2("day9.test-input2.txt", 10);
		assertEquals(36, solution);
	}

	@Test
	void solvePart2() throws IOException {
		final int solution = new Day9().solvePart2("day9.input.txt", 10);
		System.out.println("Solution 2: " + solution);
		assertEquals(2557, solution);
	}
}