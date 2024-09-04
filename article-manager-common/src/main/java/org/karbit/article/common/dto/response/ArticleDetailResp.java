package org.karbit.article.common.dto.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.article.common.dto.common.AuthorDto;
import org.karbit.article.common.dto.common.TagDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDetailResp extends BasePostServiceResponse {
	private String uid;

	private String title;

	private String content;

	private List<TagDto> tags;

	private AuthorDto author;

	private String lastModifyDate;

	private String createDate;
}
