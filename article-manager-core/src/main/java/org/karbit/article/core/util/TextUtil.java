package org.karbit.article.core.util;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;

public class TextUtil {
	public static String getPlainText(String content) {
		return Jsoup.parse(content).text();
	}

	public static String getEscaped(String content) {
		return StringEscapeUtils.escapeHtml4(content);
	}
	public static String getUnescaped(String content) {
		return StringEscapeUtils.unescapeHtml4(content);
	}
}
