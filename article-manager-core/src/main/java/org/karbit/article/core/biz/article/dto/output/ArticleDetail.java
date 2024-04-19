package org.karbit.article.core.biz.article.dto.output;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ArticleDetail extends PostDetail {

	@Exclude
	private String title;

	@Exclude
	private String content;

	private Map<String, String> tags;
}
