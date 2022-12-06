package sk.thenoen.aoc;

import java.util.LinkedList;

public class Day6 {

	public long solvePart1(String input) {
		long solution = -1;

		final char[] chars = input.toCharArray();
		LinkedList<Character> buffer = new LinkedList<>();

		for (int i = 0; i < chars.length; i++) {
			final int foundIndex = buffer.indexOf(chars[i]);
			buffer.addLast(chars[i]);
			if (foundIndex == -1 && buffer.size() == 4) {
				solution = i;
				return i + 1;
			}

			if (buffer.size() == 4 || foundIndex != -1) {
				buffer.removeFirst();
			}

			if (foundIndex != -1) {
				for (int j = 0; j < foundIndex; j++) {
					buffer.removeFirst();
				}
			}
		}
		return solution;
	}

	public long solvePart2(String input) {
		long solution = -1;

		final char[] chars = input.toCharArray();
		LinkedList<Character> buffer = new LinkedList<>();

		for (int i = 0; i < chars.length; i++) {
			final int foundIndex = buffer.indexOf(chars[i]);
			buffer.addLast(chars[i]);
			if (foundIndex == -1 && buffer.size() == 14) {
				solution = i;
				return i + 1;
			}

			if (buffer.size() == 14 || foundIndex != -1) {
				buffer.removeFirst();
			}

			if (foundIndex != -1) {
				for (int j = 0; j < foundIndex; j++) {
					buffer.removeFirst();
				}
			}
		}
		return solution;
	}

}
