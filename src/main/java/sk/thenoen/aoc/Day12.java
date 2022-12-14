package sk.thenoen.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
					startingTile = new Tile(0);
					field[x][y] = startingTile;
					continue;
				}
				if (chars[x] == 'E') {
					endingTile = new Tile(27);
					field[x][y] = endingTile;
					continue;
				}
				field[x][y] = new Tile(chars[x] - 96);
			}
		}


		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				//determine neighbours;
			}
		}
		//todo: load tiles

		List<Traveller> travellers = new ArrayList<>();
		Traveller traveller = new Traveller();
		travellers.add(traveller);
		traveller.visitTile(startingTile);

		while (true) {
			for (Traveller t : travellers) {
				final List<Tile> neighbours = t.getGetCurrentTile().getNeighbours();
				for (Tile neighbour : neighbours) {
					if (!neighbour.getVisited()) {
						traveller.visitTile(neighbour);
					}
				}
			}
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

		return travellers.get(0).getTravelledDistance();
	}

	public int solvePart2(String inputPath) throws IOException {
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

	private class Tile {

		private List<Tile> neighbours = new ArrayList<>();
		private List<Traveller> presentTravellers = new ArrayList<>();
		private boolean visited;
		private int height;

		public Tile(int height) {
			this.height = height;
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
	}

	private class Traveller {

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
