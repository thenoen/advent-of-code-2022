package sk.thenoen.aoc.day10;

public class Memory {

	private long registerX;

	public long getRegisterX() {
		return registerX;
	}

	public void setRegisterX(long registerX) {
		this.registerX = registerX;
	}

	@Override
	public String toString() {
		return "Memory{ " +
			   "registerX = " + registerX +
			   " }";
	}
}
