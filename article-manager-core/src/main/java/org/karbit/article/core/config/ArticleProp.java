package org.karbit.article.core.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "article")
public class ArticleProp {

	private Title title;

	private Content content;

	private Summary summary;

	private Event event;

	@Getter
	@Setter
	public static class Title {
		private Integer minLength;

		private Integer maxLength;
	}

	@Getter
	@Setter
	public static class Summary {
		private int pageSize;

		private int length;
	}

	@Getter
	@Setter
	public static class Event {
		private String exchange;
	}

	@Getter
	@Setter
	public static class Content {
		private Integer minLength;

		private Integer maxLength;
	}
}
