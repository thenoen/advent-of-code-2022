package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day12 {

	public int solvePart1(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);

		final int X = inputLines.get(0).length();
		final int Y = inputLines.size();
		Tile[][] field = new Tile[X][Y];

		final List<Tile> tiles = new ArrayList<>();
		Tile startingTile = null;
		Tile endingTile = null;

		for (int y = 0; y < inputLines.size(); y++) {
			final String inputLine = inputLines.get(y);
			final char[] chars = inputLine.toCharArray();
			for (int x = 0; x < chars.length; x++) {
				if (chars[x] == 'S') {
					startingTile = new Tile(0, 'S', x, y);
					field[x][y] = startingTile;
					continue;
				}
				if (chars[x] == 'E') {
					endingTile = new Tile(27, 'E', x, y);
					field[x][y] = endingTile;
					continue;
				}
				field[x][y] = new Tile(chars[x] - 96, chars[x], x, y);
			}
		}

		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				//determine neighbours;
				addTileNeighbour(field[x][y], field, x, y - 1, X, Y).ifPresent(tiles::add);
				addTileNeighbour(field[x][y], field, x + 1, y, X, Y).ifPresent(tiles::add);
				addTileNeighbour(field[x][y], field, x, y + 1, X, Y).ifPresent(tiles::add);
				addTileNeighbour(field[x][y], field, x - 1, y, X, Y).ifPresent(tiles::add);
			}
		}

		printField(X, Y, field);

		List<Traveller> travellers = new ArrayList<>();
		Traveller traveller = new Traveller();
		travellers.add(traveller);
		traveller.visitTile(startingTile);

		findPath(X, Y, field, tiles, endingTile, travellers);

		return endingTile.getPresentTravellers().get(0).getTravelledDistance() - 1;
	}

	private static void findPath(int X, int Y, Tile[][] field, List<Tile> tiles, Tile endingTile, List<Traveller> travellers) {
		while (true) {
			// Visit all neighbour Tiles
			List<Traveller> doneTravellers = new ArrayList<>();
			List<Traveller> newTravellers = new ArrayList<>();

			for (Traveller t : travellers) {
				final List<Tile> neighbours = t.getGetCurrentTile().getNeighbours();
				for (Tile neighbour : neighbours) {
					if (!neighbour.getVisited()) {
						final Traveller clonedTraveller = t.clone();
						clonedTraveller.visitTile(neighbour);
						newTravellers.add(clonedTraveller);
						// remove _traveller_ from _travellers_
						doneTravellers.add(t);
					}
				}
			}
			travellers.removeAll(doneTravellers);
			travellers.addAll(newTravellers);

			//			System.out.println("---------------------------------------");
			//			printField(X, Y, field);
			//			try {
			//				Thread.sleep(400);
			//			} catch (InterruptedException e) {
			//				throw new RuntimeException(e);
			//			}

			// Evaluate travelled paths and keep only the shortest
			for (Tile tile : tiles) {
				final List<Traveller> presentTravellers = tile.getPresentTravellers();
				if (presentTravellers.size() < 2) {
					continue;
				}
				final Traveller first = presentTravellers
						.stream()
						.sorted(Comparator.comparing(Traveller::getTravelledDistance))
						.findFirst()
						.orElseThrow();
				travellers.removeAll(presentTravellers);
				travellers.add(first);
			}
			if (!endingTile.getPresentTravellers().isEmpty()) {
				break;
			}
		}
	}

	public int solvePart2(String inputPath) throws IOException {
		final List<String> inputLines = loadInput(inputPath);

		final int X = inputLines.get(0).length();
		final int Y = inputLines.size();
		Tile[][] field = new Tile[X][Y];

		final List<Tile> tiles = new ArrayList<>();
		Tile startingTile = null;
		Tile endingTile = null;

		for (int y = 0; y < inputLines.size(); y++) {
			final String inputLine = inputLines.get(y);
			final char[] chars = inputLine.toCharArray();
			for (int x = 0; x < chars.length; x++) {
				if (chars[x] == 'S') {
					startingTile = new Tile(0, 'S', x, y);
					field[x][y] = startingTile;
					continue;
				}
				if (chars[x] == 'E') {
					endingTile = new Tile(27, 'E', x, y);
					field[x][y] = endingTile;
					continue;
				}
				field[x][y] = new Tile(chars[x] - 96, chars[x], x, y);
			}
		}

		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				//determine neighbours;
				Optional<Tile> tile;
				tile = addTileNeighbour2(field[x][y], field, x, y - 1, X, Y);
				if (tile.isPresent() && !tiles.contains(tile)) {
					tiles.add(tile.get());
				}
				tile = addTileNeighbour2(field[x][y], field, x + 1, y, X, Y);
				if (tile.isPresent() && !tiles.contains(tile)) {
					tiles.add(tile.get());
				}
				tile = addTileNeighbour2(field[x][y], field, x, y + 1, X, Y);
				if (tile.isPresent() && !tiles.contains(tile)) {
					tiles.add(tile.get());
				}
				tile = addTileNeighbour2(field[x][y], field, x - 1, y, X, Y);
				if (tile.isPresent() && !tiles.contains(tile)) {
					tiles.add(tile.get());
				}
			}
		}

		printField(X, Y, field);

		List<Traveller> travellers = new ArrayList<>();
		Traveller traveller = new Traveller();
		travellers.add(traveller);
		traveller.visitTile(endingTile);

		return findPath2(X, Y, field, tiles, endingTile, travellers);
	}

	private static int findPath2(int X, int Y, Tile[][] field, List<Tile> tiles, Tile endingTile, List<Traveller> travellers) {
		while (true) {
			// Visit all neighbour Tiles
			List<Traveller> doneTravellers = new ArrayList<>();
			List<Traveller> newTravellers = new ArrayList<>();

			for (Traveller t : travellers) {
				final List<Tile> neighbours = t.getGetCurrentTile().getNeighbours();
				for (Tile neighbour : neighbours) {
					if (!neighbour.getVisited()) {
						final Traveller clonedTraveller = t.clone();
						clonedTraveller.visitTile(neighbour);
						newTravellers.add(clonedTraveller);
						doneTravellers.add(t);
					}
				}
			}
			travellers.removeAll(doneTravellers);
			travellers.addAll(newTravellers);

			System.out.println("---------------------------------------");
			printField(X, Y, field);
//			try {
//				Thread.sleep(400);
//			} catch (InterruptedException e) {
//				throw new RuntimeException(e);
//			}

			// Evaluate travelled paths and keep only the shortest
			for (Tile tile : tiles) {

				final Optional<Tile> any = tile.getNeighbours()
											   .stream()
											   .filter(t -> !t.getVisited())
											   .findAny();
				if (any.isEmpty()) {
					travellers.removeAll(tile.getPresentTravellers());
				}

				final List<Traveller> presentTravellers = tile.getPresentTravellers();
				if (presentTravellers.size() < 2) {
					continue;
				}
				final Traveller first = presentTravellers
						.stream()
						.sorted(Comparator.comparing(Traveller::getTravelledDistance))
						.findFirst()
						.orElseThrow();
				travellers.removeAll(presentTravellers);
				travellers.add(first);

			}

			final Optional<Traveller> first = travellers.stream()
														.filter(t -> t.getGetCurrentTile().getLabel() == 'a' || t.getGetCurrentTile().getLabel() == 'S')
														.sorted(Comparator.comparing(Traveller::getTravelledDistance))
														.findFirst();
			if (first.isPresent()) {
				return first.get().getTravelledDistance() - 1;
			}
		}
	}

	private static void printField(int X, int Y, Tile[][] field) {
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {

				final Tile tile = field[x][y];
				if (tile.getVisited()) {
					System.out.print('.');
				} else {
					System.out.print(tile.getLabel());
				}
			}
			System.out.println();
		}
	}

	private Optional<Tile> addTileNeighbour(Tile sourceTile, Tile[][] tiles, int nX, int nY, int X, int Y) {
		if (nX >= 0 && nX < X && nY >= 0 && nY < Y) {
			final Tile potentialNeighbour = tiles[nX][nY];
			if (potentialNeighbour.getHeight() - 1 <= sourceTile.getHeight()) {
				sourceTile.addNeighbour(potentialNeighbour);
				return Optional.of(potentialNeighbour);
			}
		}
		return Optional.empty();
	}

	private Optional<Tile> addTileNeighbour2(Tile sourceTile, Tile[][] tiles, int nX, int nY, int X, int Y) {
		if (nX >= 0 && nX < X && nY >= 0 && nY < Y) {
			final Tile potentialNeighbour = tiles[nX][nY];
			if (potentialNeighbour.getHeight() + 1 >= sourceTile.getHeight()) {
				//			if (Math.abs(sourceTile.getHeight() - potentialNeighbour.getHeight()) < 2) {
				sourceTile.addNeighbour(potentialNeighbour);
				return Optional.of(potentialNeighbour);
			}
		}
		return Optional.empty();
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

	private class Tile {

		private List<Tile> neighbours = new ArrayList<>();
		private List<Traveller> presentTravellers = new ArrayList<>();
		private boolean visited;
		private int height;
		private char label;
		private final int x;
		private final int y;

		public Tile(int height, char label, int x, int y) {
			this.height = height;
			this.label = label;
			this.x = x;
			this.y = y;
		}

		public int getHeight() {
			return height;
		}

		public char getLabel() {
			return label;
		}

		public void addNeighbour(Tile neighbour) {
			neighbours.add(neighbour);
		}

		public List<Tile> getNeighbours() {
			return neighbours;
		}

		public List<Traveller> getPresentTravellers() {
			return presentTravellers;
		}

		public void addTraveller(Traveller traveller) {
			presentTravellers.add(traveller);
		}

		public void removeTraveller(Traveller traveller) {
			presentTravellers.remove(traveller);
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		public boolean getVisited() {
			return visited;
		}

		public void removeTravellers() {
			presentTravellers.clear();
			;
		}

		@Override
		public String toString() {
			return label + " (" + x + ", " + y + ")";
		}
	}

	private class Traveller {

		@Override
		public String toString() {
			return "T " + getGetCurrentTile().toString();
		}

		private LinkedList<Tile> travelledPath = new LinkedList<>();

		private void visitTile(Tile tile) {
			travelledPath.addLast(tile);
			tile.setVisited(true);
			tile.addTraveller(this);
		}

		public Tile getGetCurrentTile() {
			return travelledPath.getLast();
		}

		public int getTravelledDistance() {
			return travelledPath.size();
		}

		private void setTravelledPath(LinkedList<Tile> path) {
			this.travelledPath = path;
		}

		public Traveller clone() {
			LinkedList<Tile> path = new LinkedList<>();
			travelledPath.forEach(path::addLast);

			final Traveller traveller = new Traveller();
			traveller.setTravelledPath(path);
			return traveller;
		}

	}

}
