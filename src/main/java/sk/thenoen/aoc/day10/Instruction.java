package sk.thenoen.aoc.day10;

import java.util.function.Consumer;

public class Instruction {

	private String label;
	private int requiredCycles;
	private int remainingCycles;

	private Consumer<Memory> logic;

	public Instruction(String label, int requiredCycles, Consumer<Memory> logic) {
		this.label = label;
		this.requiredCycles = requiredCycles;
		this.logic = logic;
		this.remainingCycles = this.requiredCycles;
	}

	public void nextCycle() {
		remainingCycles--;
	}

	public boolean isDone(Memory memory) {
		final boolean isDone = remainingCycles == 0;
		if (isDone) {
			logic.accept(memory);
		}
		return isDone;
	}

	public int getRequiredCycles() {
		return requiredCycles;
	}

	@Override
	public String toString() {
		return "Instruction{" +
			   "index=" + label +
			   ", requiredCycles=" + requiredCycles +
			   ", remainingCycles=" + remainingCycles +
			   '}';
	}
}
