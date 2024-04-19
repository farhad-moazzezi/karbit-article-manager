package org.karbit.postmng.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleResp extends BasePostServiceResponse {
	private String articleId;
}
