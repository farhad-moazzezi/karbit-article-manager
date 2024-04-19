package org.karbit.article.core.util;

public interface PaginationUtil {
	static int convert(int input) {
		if (input < 1) {
			return 0;
		} else {
			return input - 1;
		}
	}
}
