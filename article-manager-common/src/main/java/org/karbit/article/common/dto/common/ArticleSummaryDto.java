package org.karbit.article.common.dto.common;

import java.util.List;

import lombok.Data;

@Data
public class ArticleSummaryDto {
	private String articleId;

	private String title;

	private String summary;

	private List<TagDto> tags;

	private AuthorDto author;

	private String lastModifyDate;
}
