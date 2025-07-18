package org.ringle.globalutils.util;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public final class ListSortUtils {

	public static <T> List<T> sortByIntKey(List<T> list, ToIntFunction<T> keyExtractor) {
		return list.stream()
			.sorted(Comparator.comparingInt(keyExtractor))
			.toList();
	}

	private ListSortUtils(){
	}
}
