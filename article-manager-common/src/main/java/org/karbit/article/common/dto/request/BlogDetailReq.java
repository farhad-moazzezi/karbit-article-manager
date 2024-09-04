package org.karbit.article.common.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.skeleton.base.result.dto.BaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlogDetailReq extends BaseRequest {
	private String postId;
}
