package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Day9 {

	public int solvePart1(String inputPath) throws IOException {
		final List<String> movements = loadInput(inputPath);

		Point head = new Point();
		Point tail = new Point();
		Set<Point> tailHistory = new HashSet<>();
		//		tailHistory.add(tail);

		for (String movement : movements) {
			final String[] parts = movement.split(" ");
			final int count = Integer.parseInt(parts[1]);

			for (int i = 0; i < count; i++) {
				final Point prevHead = head.clone();
				moveHead(head, parts[0]);
				final int distance = tail.distance(head);
				if (distance > 1) {
					tailHistory.add(tail.clone());
					tail = prevHead;
				}
			}
		}
		tailHistory.add(tail);

		return tailHistory.size();
	}

	public int solvePart2(String inputPath, int length) throws IOException {
		final List<String> movements = loadInput(inputPath);

		Point[] points = new Point[length];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point();
		}
		Set<Point> tailHistory = new HashSet<>();
		tailHistory.add(points[points.length - 1]);

		for (String movement : movements) {
			final String[] parts = movement.split(" ");
			final String directionCommand = parts[0];
			final int count = Integer.parseInt(parts[1]);

			for (int i = 0; i < count; i++) {

				Point backupPoint = points[0].clone();
				moveHead(points[0], directionCommand);

				System.out.println("head: ");
				printPoints(points);

				for (int p = 0; p < points.length - 1; p++) {

					final int distance = points[p].distance(points[p + 1]);
					final String direction = points[p].direction(points[p + 1]);

					if (distance > 1 && direction.equals("para")) {
						final int cross = points[p].crossDirection(points[p + 1]);
						final Point clone = points[p + 1].clone();
						switch (cross) {
							case 1 -> clone.moveUp();
							case 2 -> clone.moveRight();
							case 3 -> clone.moveDown();
							case 4 -> clone.moveLeft();
						}
						points[p + 1] = clone;
					} else if (distance > 1 && direction.equals("diag")) {
						final int quadrant = points[p].quadrant(points[p + 1]);
						final Point clone = points[p + 1].clone();
						backupPoint = points[p + 1];

						switch (quadrant) {
							case 1 -> {
								clone.moveRight();
								clone.moveUp();
							}
							case 2 -> {
								clone.moveRight();
								clone.moveDown();
							}
							case 4 -> {
								clone.moveLeft();
								clone.moveUp();
							}
							case 3 -> {
								clone.moveLeft();
								clone.moveDown();
							}
						}
						points[p + 1] = clone;

					} else {
						System.out.println("noop");
					}

					System.out.println("body: " + (p + 3) + " / " + points.length);
					printPoints(points);
				}
				tailHistory.add(points[points.length - 1]);

				System.out.println(movement + " / " + (i + 1));
				printPoints(points);
			}
		}
		//		tailHistory.add(tail);

		return tailHistory.size();
	}

	private void printPoints(Point[] points) {

		if (true) {
			return;
		}

		final int height = 26;
		final int width = 27;
		char[][] output = new char[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				output[x][y] = '.';
			}
		}

		int shiftX = 12;
		int shiftY = 8;
		for (int i = points.length - 1; i >= 0; i--) {
			final Point point = points[i];
			output[point.getX() + shiftX][point.getY() + shiftY] = String.valueOf(i).toCharArray()[0];
		}

		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				System.out.print(output[x][y]);
			}
			System.out.println();
		}

	}

	private static void moveHead(Point head, String direction) {
		switch (direction) {
			case "U" -> head.moveUp();
			case "R" -> head.moveRight();
			case "D" -> head.moveDown();
			case "L" -> head.moveLeft();
			default -> throw new RuntimeException("Input error");
		}
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

	private static class Point {

		private int x = 0;
		private int y = 0;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void moveRight() {
			//			prevX = x;
			x++;
		}

		public void moveLeft() {
			//			prevX = x;
			x--;
		}

		public void moveUp() {
			//			prevY = y;
			y++;
		}

		public void moveDown() {
			//			prevY = y;
			y--;
		}

		public Point clone() {
			final Point point = new Point();
			point.x = this.x;
			point.y = this.y;
			return point;
		}

		public int distance(Point p) {
			final int dx = Math.abs(x - p.getX());
			final int dy = Math.abs(y - p.getY());
			return Math.max(dx, dy);
		}

		public String direction(Point p) {
			final int dx = Math.abs(x - p.getX());
			final int dy = Math.abs(y - p.getY());
			if (dx != 0 && dy != 0) {
				return "diag"; //diagonal
			} else {
				return "para"; //parallel
			}
		}

		public int crossDirection(Point p) {
			if (x == p.getX()) {
				if (y > p.getY()) {
					return 1;
				} else {
					return 3;
				}
			} else if (y == p.getY()) {
				if (x < p.getX()) {
					return 4;
				} else {
					return 2;
				}
			}
			throw new RuntimeException("can determine cross direction");
		}

		public int quadrant(Point p_1) {
			final int dx = x - p_1.getX();
			final int dy = y - p_1.getY();

			if (dx > 0 && dy > 0) {
				return 1;
			} else if (dx > 0 && dy < 0) {
				return 2;
			} else if (dx < 0 && dy > 0) {
				return 4;
			} else if (dx < 0 && dy < 0) {
				return 3;
			}
			throw new RuntimeException("Quadrant problem");
		}

		@Override
		public String toString() {
			return "[" + x + ", " + y + "]";
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			Point point = (Point) o;
			return x == point.x && y == point.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
}
