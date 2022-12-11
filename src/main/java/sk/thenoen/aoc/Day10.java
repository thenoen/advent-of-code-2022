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

	public long solvePart1(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);

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

	public long solvePart2(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);
		return -1;
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
