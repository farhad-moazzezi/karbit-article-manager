package org.karbit.postmng.common.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.skeleton.base.result.dto.BaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailReq extends BaseRequest {
	private String postId;
}
