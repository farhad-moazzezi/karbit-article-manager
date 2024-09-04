package org.karbit.article.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextUtilTest {

	public static String content = "html content";

	public static String htmlContent = "<div><br>" + content + "</div></br>";


	@Test
	public void getPlainText() {
		Assertions.assertEquals(content, TextUtil.getPlainText(htmlContent));
	}
}
