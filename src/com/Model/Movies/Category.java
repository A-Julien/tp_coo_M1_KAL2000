package com.Model.Movies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Fim Category
 */
public enum Category {
	Action,
	SF,
	Fantasy,
	Comedy,
	Tragedy,
	Romance;

	private static final List<Category> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	/**
	 * Allow to get a randomCategory
	 *
	 * Useful for data generation
	 * @return the category
	 */
	public static Category randomCategory()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	/**
	 * Print all category as string
	 *
	 * Useful for display
	 * @return String
	 */
	public static String toStringAll(){
		StringBuilder stringBuilder = new StringBuilder();
		for (Category category : Category.values()) stringBuilder.append(category).append(",");
		return stringBuilder.toString();
	}
}