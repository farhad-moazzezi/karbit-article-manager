package org.karbit.article.core.model;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.karbit.skeleton.mongo.model.BaseMongoEntity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Document(collection = "article")
public class Article extends BaseMongoEntity {

	@Exclude
	@Indexed
	private String title;

	@Exclude
	private String content;

	private String encodeContent;

	private Author author;

	private Set<Tag> tags;

	private String bannerId;

	@Indexed
	private ArticleStatus status;

	@Indexed
	private Long lastModifyDate = new Date().getTime();

	public String getSummary() {
		return getContent();
	}

	public Date getLastModifyDate() {
		return Objects.nonNull(lastModifyDate) ? Date.from(Instant.ofEpochMilli(lastModifyDate)) : null;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate.getTime();
	}

}
