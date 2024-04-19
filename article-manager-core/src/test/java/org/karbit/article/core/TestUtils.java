package org.karbit.article.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.karbit.article.core.dao.ArticleDao;
import org.karbit.article.core.model.Article;
import org.karbit.article.core.model.ArticleStatus;

import org.karbit.article.core.model.Author;
import org.karbit.article.core.model.Tag;
import org.karbit.tagmng.common.dto.common.TagInfo;
import org.karbit.tagmng.common.dto.response.FoundTagResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class TestUtils {

	public static final String EMPTY_STRING_VALUES = "org.karbit.article.core.TestUtils#emptyStringValue";

	public static final String AUTHOR_ID = "AUTHOR_ID";

	public static final String AUTHOR_USERNAME = "USERNAME";

	public static final String AUTHOR_NICKNAME = "NICKNAME";

	public static final String POST_TITLE = "POST_TITLE";

	public static final String POST_CONTENT = "LONG_POST_CONTENT";

	public static final String BANNER_ID = "BANNER_ID";

	public static class TagUtils {
		public static final String TAG_ID = "TAG_ID";

		public static final String TAG_CAPTION = "TAG_CAPTION";

		public static FoundTagResp createTagResp() {
			return new FoundTagResp(createTagInfo(TAG_ID, TAG_CAPTION));
		}

		public static List<TagInfo> createTagInfo() {
			return createTagInfo(TAG_ID, TAG_CAPTION);
		}

		public static List<TagInfo> createTagInfo(String tagId, String caption) {
			TagInfo tagInfo = new TagInfo();
			tagInfo.setTagId(tagId);
			tagInfo.setCaption(caption);
			return Collections.singletonList(tagInfo);
		}
	}

	public static final Set<Tag> ARTICLE_TAGS = Set.of(new Tag("TAG-ID-1", "TAG-1"), new Tag("TAG-ID-2", "TAG-2"));

	public static final Set<String> TAG_LABELS = Set.of("TAG-1", "TAG-2");

	@Autowired
	private ArticleDao articleDao;

	public static List<String> emptyStringValue() {
		return Arrays.asList("", " ", null);
	}

	public HttpHeaders getDefaultHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	public HttpHeaders getDefaultHeader(String userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Id", userId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	public Article createArticle(ArticleStatus status) {
		return createArticle(POST_TITLE, status);
	}

	public Article createArticle(String title, ArticleStatus status) {
		Article article = new Article();
		article.setBannerId(BANNER_ID);
		article.setTitle(title);
		article.setContent(POST_CONTENT);
		article.setAuthor(Author.builder().userId(AUTHOR_ID).nickname(AUTHOR_NICKNAME).build());
		article.setTags(ARTICLE_TAGS);
		article.setStatus(status);
		return articleDao.save(article);
	}
}
