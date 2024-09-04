package org.karbit.article.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDraftArticleResp extends BasePostServiceResponse {
	private String articleId;
}
