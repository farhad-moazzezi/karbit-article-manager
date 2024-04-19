package org.karbit.article.core.biz.article.dto.input;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleModel {

	private String title;

	private String content;

	private String authorId;

	private Set<String> tags;
}
