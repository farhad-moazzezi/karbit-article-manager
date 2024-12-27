package org.karbit.article.core.biz.article.mapper.article;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.karbit.article.common.dto.common.ArticleSummaryDto;
import org.karbit.article.common.dto.common.AuthorDto;
import org.karbit.article.common.dto.common.TagDto;
import org.karbit.article.common.dto.request.CreateArticleReq;
import org.karbit.article.common.dto.request.DraftArticleReq;
import org.karbit.article.common.dto.response.ArticleDetailResp;
import org.karbit.article.core.biz.article.dto.input.ArticleModel;
import org.karbit.article.core.biz.article.dto.input.DraftedArticleModel;
import org.karbit.article.core.biz.article.dto.output.ArticleDetail;
import org.karbit.article.core.biz.article.dto.output.ArticleSummary;
import org.karbit.article.core.biz.article.dto.output.AuthorProfile;
import org.karbit.article.core.client.tagmanager.mapper.TagMapper;
import org.karbit.article.core.config.ArticleProp;
import org.karbit.article.core.model.Article;
import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.core.model.Author;
import org.karbit.article.core.model.Tag;
import org.karbit.convertor.DateConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring", imports = { ArticleStatus.class, Date.class, TagMapper.class })
public abstract class ArticleServiceMapper {

	@Autowired
	protected ArticleProp articleProp;

	public abstract List<ArticleSummary> toSummary(List<Article> articles);

	@Mapping(target = "postId", source = "article.uniqueId")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "modifyDate", source = "article.lastModifyDate", qualifiedByName = "toPersianDate")
	@Mapping(target = "title", source = "article.title")
	@Mapping(target = "summary", source = "article.content", qualifiedByName = "summarizeContent")
	@Mapping(target = "tags", source = "article.tags", qualifiedByName = "toTag")
	public abstract ArticleSummary toSummary(Article article);

	@Named("toPersianDate")
	public String toPersianDate(Date date) {
		return DateConvertor.toPersianDate(date);
	}


	@Named("toTag")
	public Map<String, String> toTag(Set<Tag> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return Collections.emptyMap();
		}
		var result = new HashMap<String, String>();
		tags.forEach(tag -> result.put(tag.getTagId(), tag.getCaption()));
		return result;
	}

	@Named("summarizeContent")
	public String summarizeContent(String content) {
		return
				new StringBuilder(content)
						.replace(articleProp.getSummary().getLength() - 3, articleProp.getSummary().getLength(), "...")
						.substring(0, articleProp.getSummary().getLength());
	}

	@Mapping(target = "title", source = "createArticleReq.title")
	@Mapping(target = "content", source = "createArticleReq.content")
	@Mapping(target = "authorId", source = "authorUserId")
	@Mapping(target = "tags", source = "createArticleReq.tagLabel")
	public abstract ArticleModel toNewArticleModel(CreateArticleReq createArticleReq, String authorUserId);


	@Mapping(target = "title", source = "draftedArticle.title")
	@Mapping(target = "content", source = "draftedArticle.content")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "tags", source = "tags")
	@Mapping(target = "status", expression = "java(ArticleStatus.PEND)")
	@Mapping(target = "lastModifyDate", expression = "java(new Date())")
	public abstract Article toNewDraftArticle(Author author, DraftedArticleModel draftedArticle, Set<Tag> tags);

	public abstract ArticleDetailResp toArticleDetailResp(ArticleDetail detail);

	public abstract List<ArticleSummaryDto> toArticleSummaryDto(List<ArticleSummary> articles);

	@Mapping(target = "articleId", source = "postId")
	@Mapping(target = "title", source = "title")
	@Mapping(target = "summary", source = "summary")
	@Mapping(target = "lastModifyDate", source = "modifyDate")
	public abstract ArticleSummaryDto toArticleSummaryDto(ArticleSummary article);

	@Mapping(target = "userId", source = "userId")
	@Mapping(target = "nickname", source = "nickname")
	public abstract AuthorDto toAuthorDto(AuthorProfile authorProfile);

	public List<TagDto> toTagDto(Map<String, String> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return null;
		}
		return tags.entrySet().stream().map(tag -> new TagDto(tag.getKey(), tag.getValue())).toList();
	}

	@Mapping(target = "title", source = "title")
	@Mapping(target = "content", source = "content")
	@Mapping(target = "postId", source = "uniqueId")
	@Mapping(target = "author", source = "author")
	@Mapping(target = "modifyDate", source = "lastModifyDate")
	@Mapping(target = "createDate", source = "creationDate")
	public abstract ArticleDetail toArticleDetail(Article article);

	public Map<String, String> toTagMap(Set<Tag> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return null;
		}
		Map<String, String> result = new HashMap<>();
		tags.forEach(tag -> result.put(tag.getTagId(), tag.getCaption()));
		return result;
	}

	@Mapping(target = "articleId", source = "draftArticleReq.articleId")
	@Mapping(target = "title", source = "draftArticleReq.title")
	@Mapping(target = "content", source = "draftArticleReq.content")
	@Mapping(target = "tags", source = "draftArticleReq.tagLabels")
	@Mapping(target = "authorId", source = "userId")
	public abstract DraftedArticleModel toDraftArticleModel(DraftArticleReq draftArticleReq, String userId);
}
