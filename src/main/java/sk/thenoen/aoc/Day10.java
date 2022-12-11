package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import sk.thenoen.aoc.day10.Instruction;
import sk.thenoen.aoc.day10.Memory;

public class Day10 {

	public static final int DISPLAY_WIDTH = 40;
	public static final int DISPLAY_HEIGHT = 6;

	public long solvePart1(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);
		Queue<Instruction> instructions = parseInstructions(inputLines);

		final Memory memory = new Memory();
		memory.setRegisterX(1);

		long cycleNumber = 0;
		List<Long> signalValues = new ArrayList<>();

		Instruction instruction = instructions.poll();
		do {
			cycleNumber++;
			if (instruction.isDone(memory)) {
				instruction = instructions.poll();
			}
			instruction.nextCycle();
			if ((cycleNumber - 20) % 40 == 0) {
				System.out.println("Cycle " + cycleNumber + ": " + memory.getRegisterX());
				signalValues.add(cycleNumber * memory.getRegisterX());
			}

		} while (!instructions.isEmpty());

		return signalValues.stream()
						   .mapToLong(Long::longValue)
						   .sum();
	}

	public String solvePart2(String inputPath) throws IOException {

		final List<String> inputLines = loadInput(inputPath);
		Queue<Instruction> instructions = parseInstructions(inputLines);

		final Memory memory = new Memory();
		memory.setRegisterX(1);

		int cycleNumber = 0;

		boolean[][] display = new boolean[DISPLAY_WIDTH][DISPLAY_HEIGHT];

		Instruction instruction = instructions.poll();
		do {
//			System.out.println("Cycle " + (cycleNumber % 40) + " (" + cycleNumber + ") : " + memory.getRegisterX());

			final long matchIndex = memory.getRegisterX() - (cycleNumber % 40);
			if (Math.abs(matchIndex) < 2) {
				int x = cycleNumber % DISPLAY_WIDTH;
				int y = (cycleNumber - x) / DISPLAY_WIDTH;
				display[x][y] = true;
			}

			cycleNumber++;
			instruction.nextCycle();
			if (instruction.isDone(memory)) {
				instruction = instructions.poll();
			}


		} while (!instructions.isEmpty());

		String result = "";
		for (int y = 0; y < DISPLAY_HEIGHT; y++) {
			for (int x = 0; x < DISPLAY_WIDTH; x++) {
				if (display[x][y]) {
					result += "#";
				} else {
					result += ".";
				}
			}
			result += "\n";
		}

		System.out.println(result);
		return result;
	}

	private static Queue<Instruction> parseInstructions(List<String> inputLines) {
		Queue<Instruction> instructions = new LinkedList<>();
		for (int i = 0; i < inputLines.size(); i++) {
			final String inputLine = inputLines.get(i);
			final String[] parts = inputLine.split(" ");
			final String label = i + " " + inputLine;
			if (parts[0].startsWith("noop")) {
				instructions.add(new Instruction(label, 1, m -> {
				}));
			} else {
				instructions.add(new Instruction(label, 2,
												 m -> m.setRegisterX(m.getRegisterX() + Long.parseLong(parts[1]))));
			}
		}
		return instructions;
	}

	private List<String> loadInput(String inputPath) throws IOException {
		List<String> inputLines = new ArrayList<>();
		try (final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(inputPath)) {
			final Scanner scanner = new Scanner(inputStream);
			while (scanner.hasNextLine()) {
				inputLines.add(scanner.nextLine());
			}
		}
		return inputLines;
	}

}
