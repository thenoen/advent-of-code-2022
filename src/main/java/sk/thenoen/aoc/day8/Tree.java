package sk.thenoen.aoc.day8;

public class Tree {

	private int maxTopHeight = -1;
	private int maxBottomHeight = -1;
	private int maxLeftHeight = -1;
	private int maxRightHeight = -1;

	private int height = 0;
	private int topViewScore = 0;
	private int bottomViewScore = 0;
	private int leftViewScore = 0;
	private int rightViewScore = 0;


	public void setTopViewScore(int scenicScore) {
		this.topViewScore = scenicScore;
	}

	public int getTopViewScore() {
		return topViewScore;
	}

	public int getBottomViewScore() {
		return bottomViewScore;
	}

	public void setBottomViewScore(int bottomViewScore) {
		this.bottomViewScore = bottomViewScore;
	}

	public int getLeftViewScore() {
		return leftViewScore;
	}

	public void setLeftViewScore(int leftViewScore) {
		this.leftViewScore = leftViewScore;
	}

	public int getRightViewScore() {
		return rightViewScore;
	}

	public void setRightViewScore(int rightViewScore) {
		this.rightViewScore = rightViewScore;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMaxTopHeight(int maxTopHeight) {
		this.maxTopHeight = maxTopHeight;
	}

	public void setMaxBottomHeight(int maxBottomHeight) {
		this.maxBottomHeight = maxBottomHeight;
	}

	public void setMaxLeftHeight(int maxLeftHeight) {
		this.maxLeftHeight = maxLeftHeight;
	}

	public void setMaxRightHeight(int maxRightHeight) {
		this.maxRightHeight = maxRightHeight;
	}

	public int getMaxTopHeight() {
		return maxTopHeight;
	}

	public int getMaxBottomHeight() {
		return maxBottomHeight;
	}

	public int getMaxLeftHeight() {
		return maxLeftHeight;
	}

	public int getMaxRightHeight() {
		return maxRightHeight;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return height > maxTopHeight ||
			   height > maxBottomHeight ||
			   height > maxLeftHeight ||
			   height > maxRightHeight;
	}

	public int getViewScore() {
		return topViewScore * bottomViewScore * rightViewScore * leftViewScore;
	}

	@Override
	public String toString() {
		return String.valueOf(height);
	}
}
