package org.karbit.article.core.biz.article.dto.output;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class PostDetail {

	private String postId;

	private AuthorProfile author;

	private Date modifyDate;

	private Date createDate;

}
