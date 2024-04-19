package org.karbit.postmng.common.dto.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.karbit.postmng.common.dto.common.AuthorDto;
import org.karbit.postmng.common.dto.common.TagDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlogDetailResp extends BasePostServiceResponse {
	private String blogId;

	private String title;

	private String content;

	private List<TagDto> tags;

	private AuthorDto author;

	private String lastModifyDate;

	private String createDate;
}
