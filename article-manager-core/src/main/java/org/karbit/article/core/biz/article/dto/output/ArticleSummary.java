package org.karbit.article.core.biz.article.dto.output;

import java.util.Map;

import lombok.Data;

@Data
public class ArticleSummary {

	private String postId;

	private String title;

	private String summary;

	private AuthorProfile author;

	private Map<String, String> tags;

	private String modifyDate;

}
