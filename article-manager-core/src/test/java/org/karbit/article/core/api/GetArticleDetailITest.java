package org.karbit.article.core.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karbit.article.core.TestUtils;
import org.karbit.article.core.dao.ArticleDao;

import org.karbit.article.core.model.ArticleStatus;
import org.karbit.postmng.common.dto.response.ArticleDetailResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetArticleDetailITest extends ArticleManagerITest {
	public static final String BASE_URL = "/article/detail";

	private String ARTICLE_ID;

	@Autowired
	private ArticleDao articleDao;

	@Override
	protected String getServiceUrl() {
		return BASE_URL;
	}

	@Override
	@BeforeEach
	protected void beforeTest() {
		clearDb();
		ARTICLE_ID = testUtils.createArticle(ArticleStatus.CONFIRMED).getUniqueId();
	}

	@Override
	public ResponseEntity<ArticleDetailResp> sendRequest(HttpHeaders headers, String articleId) {
		return restTemplate.exchange(getTestUrlWithParam(articleId), HttpMethod.GET,
				new HttpEntity<>(headers), ArticleDetailResp.class);
	}

	private void clearDb() {
		articleDao.deleteAll();
	}

	@Test
	void getArticleDetail_success() {
		mockUserProfileSummary(createProfileSummaryResp());
		ResponseEntity<ArticleDetailResp> response = sendRequest(testUtils.getDefaultHeader(), ARTICLE_ID);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(TestUtils.AUTHOR_ID, response.getBody().getAuthor().getUserId());
		Assertions.assertEquals(TestUtils.AUTHOR_NICKNAME, response.getBody().getAuthor().getNickname());
		Assertions.assertEquals(TestUtils.POST_TITLE, response.getBody().getTitle());
		Assertions.assertEquals(TestUtils.POST_CONTENT, response.getBody().getContent());
		Assertions.assertEquals(TestUtils.ARTICLE_TAGS.size(), response.getBody().getTags().size());
	}

}
