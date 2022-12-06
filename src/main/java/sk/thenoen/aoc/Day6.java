package sk.thenoen.aoc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Day6 {

	public long solvePart1(String input) {
		long solution = -1;

		final char[] chars = input.toCharArray();
		LinkedList<Character> buffer = new LinkedList<>();

		for (int i = 0; i < chars.length; i++) {
			final int foundIndex = buffer.indexOf(chars[i]);
			if (foundIndex ==  -1) {
				buffer.addLast(chars[i]);
			} else {

			}

			if (buffer.size() == 4) {
				solution = i;
				return i;
			}

			if (buffer.size() == 5) {
				buffer.removeFirst();
			}

		}

		return solution;
	}

}
