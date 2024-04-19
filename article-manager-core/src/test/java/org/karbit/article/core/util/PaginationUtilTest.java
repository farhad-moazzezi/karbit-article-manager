package org.karbit.article.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PaginationUtilTest {

	@Test
	void convert() {
		assertThat(PaginationUtil.convert(0)).isZero();
		assertThat(PaginationUtil.convert(1)).isZero();
		assertThat(PaginationUtil.convert(2)).isEqualTo(1);
		assertThat(PaginationUtil.convert(-1)).isZero();
	}
}
