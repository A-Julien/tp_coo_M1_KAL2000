package Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Category {
	Action,
	XxX,
	SF,
	Fantasy,
	Comedy,
	Tragedy,
	Romance;

	private static final List<Category> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Category randomCategory()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}