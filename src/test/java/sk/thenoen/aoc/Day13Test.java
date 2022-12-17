package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {

	@Test
	void testPart1sub1() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input1.txt");
		assertEquals(1, solution);
	}

	@Test
	void testPart1sub2() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input2.txt");
		assertEquals(1, solution);
	}

	@Test
	void testPart1sub3() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input3.txt");
		assertEquals(0, solution);
	}

	@Test
	void testPart1sub4() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input4.txt");
		assertEquals(1, solution);
	}

	@Test
	void testPart1sub5() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input5.txt");
		assertEquals(0, solution);
	}

	@Test
	void testPart1sub6() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input6.txt");
		assertEquals(1, solution);
	}

	@Test
	void testPart1sub7() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input7.txt");
		assertEquals(0, solution);
	}

	@Test
	void testPart1sub8() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input8.txt");
		assertEquals(0, solution);
	}

	@Test
	void testPart1() throws IOException {
		final int solution = new Day13().solvePart1("day13.test-input.txt");
		assertEquals(13, solution);
	}

	@Test
	void solvePart1() throws IOException {
		final int solution = new Day13().solvePart1("day13.input.txt");
		System.out.println("Solution 1: " + solution);
		assertTrue(629 < solution, "too low");
	}

	@Test
	void testPart2() throws IOException {
		final int solution = new Day13().solvePart2("day13.test-input.txt");
		//		assertEquals(?, solution);

	}

	@Test
	void solvePart2() throws IOException {
		final int solution = new Day13().solvePart2("day13.input.txt");
		System.out.println("Solution 1: " + solution);
		//		assertEquals(?, solution);
	}
}