package org.karbit.article.common.dto.request;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.skeleton.base.result.dto.BaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class DraftArticleReq extends BaseRequest {

	private String articleId;

	private String title;

	private String content;

	private Set<String> tagLabels;
}
