package org.karbit.article.common.dto.request;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.skeleton.base.result.dto.BaseRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateBlogReq extends BaseRequest {
	private String userId;

	private String title;

	private String content;

	private Set<String> tagLabel;
}
