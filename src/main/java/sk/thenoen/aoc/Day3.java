package sk.thenoen.aoc;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day3 {

	public void solvePart1() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day3.input.txt");
		//						InputStream input = this.getClass().getClassLoader().getResourceAsStream("day3.test-input.txt");

		Scanner scanner = new Scanner(input);

		long sum = 0;

		while (scanner.hasNextLine()) {
			final String allItems = scanner.nextLine();
			final int cItemsCount = allItems.length() / 2;
			String c1ItemString = allItems.substring(0, cItemsCount);
			String c2ItemString = allItems.substring(cItemsCount);

			if (c1ItemString.length() != c2ItemString.length()) {
				System.out.println(allItems);
			}

			final List<Integer> c1Items = c1ItemString.chars()
													  .boxed()
													  .collect(Collectors.toList());

			final List<Integer> common = c2ItemString.chars()
													 .filter(i -> c1Items.contains(i))
													 .boxed()
													 .collect(Collectors.toList());

			//			System.out.println(c1ItemString + " " + c2ItemString);
			//			System.out.println(common);

			if (common.size() == 0) {
				continue;
			}

			//			if (common.size() == 1) {
			//				System.out.println(common);
			//			}

			// a -> 97
			final int item = common.get(0).intValue();
			//			System.out.print((char) common.get(0).intValue() + " -> ");

			int itemCategory = calculateItemCategory(item);

			//			System.out.println(itemCategory);
			sum += itemCategory;
		}

		System.out.println("Solution 1:");
		System.out.println(sum);
		// 7502 too little
		// 9951 too much
	}

	public void solvePart2() {

		InputStream input = this.getClass().getClassLoader().getResourceAsStream("day3.input.txt");
//								InputStream input = this.getClass().getClassLoader().getResourceAsStream("day3.test-input.txt");

		Scanner scanner = new Scanner(input);

		long sum = 0;

		while (scanner.hasNextLine()) {
			final String elve1ItemString = scanner.nextLine();
			final String elve2ItemString = scanner.nextLine();
			final String elve3ItemString = scanner.nextLine();

			final List<Integer> e1Items = elve1ItemString.chars()
														 .boxed()
														 .toList();

			final List<Integer> e2Items = elve2ItemString.chars()
														 .boxed()
														 .toList();

			final List<Integer> commonItem = elve3ItemString.chars()
														  .filter(e1Items::contains)
														  .filter(e2Items::contains)
														  .boxed()
														  .toList();


			final int category = calculateItemCategory(commonItem.get(0));
			sum += category;

		}

		System.out.println("Solution 2:");
		System.out.println(sum);
	}

	private static int calculateItemCategory(int item) {
		int itemCategory = 0;
		if (item > 96) {
			itemCategory = item - 96;
		}

		// A -> 65
		if (item < 96) {
			itemCategory = item - 38;
		}
		return itemCategory;
	}

}
