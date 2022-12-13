package sk.thenoen.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day11Test {

	@Test
	void testPart1() throws IOException {
		final BigInteger solution = new Day11().solvePart1("day11.test-input.txt");
		assertEquals(BigInteger.valueOf(10605L), solution);
	}

	@Test
	void solvePart1() throws IOException {
		final BigInteger solution = new Day11().solvePart1("day11.input.txt");
		System.out.println("Solution 1: " + solution);
		assertEquals(BigInteger.valueOf(58794L), solution);
	}

	@Test
	void testPart2a() throws IOException {
		final BigInteger solution = new Day11().solvePart2("day11.test-input.txt", 1000);
		assertEquals(BigInteger.valueOf(27019168L), solution); // correct result
	}

	@Test
	void testPart2b() throws IOException {
		final BigInteger solution = new Day11().solvePart2("day11.test-input.txt", 10_000);
		assertEquals(BigInteger.valueOf(2713310158L), solution);
	}

	@Test
	void testPart2c() throws IOException {
		final BigInteger solution = new Day11().solvePart2("day11.test-input.txt", 700);
//		assertEquals(BigInteger.valueOf(2713310158L), solution);
	}

	@Test
	void solvePart2() throws IOException {
		final BigInteger solution = new Day11().solvePart2("day11.input.txt", 10_000);
		System.out.println("Solution 2: " + solution);
		assertTrue(BigInteger.valueOf(1516178120L).compareTo(solution) < 0); // 1516178120 - too low
		//		assertEquals(???, solution);
	}
}