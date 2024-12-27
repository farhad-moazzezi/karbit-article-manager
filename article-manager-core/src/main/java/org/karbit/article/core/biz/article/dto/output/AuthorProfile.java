package org.karbit.article.core.biz.article.dto.output;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthorProfile {
	private String userId;

	private String nickname;
}
