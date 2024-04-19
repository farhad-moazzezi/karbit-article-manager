package org.karbit.postmng.common.dto.common;

import java.util.List;

import lombok.Data;

@Data
public class BlogSummaryInfo {
	private String blogId;

	private String title;

	private String summary;

	private List<TagDto> tags;

	private AuthorDto author;

	private String lastModifyDate;
}
