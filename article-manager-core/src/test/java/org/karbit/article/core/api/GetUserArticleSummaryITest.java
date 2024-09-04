package org.karbit.article.core.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karbit.article.core.dao.ArticleDao;

import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.common.dto.common.ArticleSummaryDto;
import org.karbit.article.common.dto.response.ArticleSummaryResp;
import org.karbit.article.core.TestUtils;
import org.karbit.article.core.config.ArticleProp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetUserArticleSummaryITest extends ArticleManagerITest {
	public static final String BASE_URL = "/article/summary";

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ArticleProp articleProp;

	@Override
	protected String getServiceUrl() {
		return BASE_URL;
	}

	@Override
	@BeforeEach
	protected void beforeTest() {
		clearDb();
	}

	@AfterEach
	protected void afterTest() {
		clearDb();
	}

	private void createSomeArticle(int size) {
		for (int i = 0; i < size; i++) {
			testUtils.createArticle(ArticleStatus.CONFIRMED);
		}
	}

	private void clearDb() {
		articleDao.deleteAll();
	}

	@Override
	public ResponseEntity<ArticleSummaryResp> sendRequest(HttpHeaders headers, String value) {
		return restTemplate.exchange(getTestUrlWithParam(value), HttpMethod.GET,
				new HttpEntity<>(headers), ArticleSummaryResp.class);
	}

	@Test
	void getArticleSummary_success() {

		createSomeArticle(1);

		mockUserProfileSummary(createProfileSummaryResp());
		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), "/" + TestUtils.AUTHOR_ID + "/" + 1);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(1, response.getBody().getArticles().size());

		Assertions.assertNotNull(response.getBody().getArticles().get(0).getArticleId());
		Assertions.assertEquals(TestUtils.POST_TITLE, response.getBody().getArticles().get(0).getTitle());
		Assertions.assertEquals(TestUtils.AUTHOR_ID, response.getBody().getArticles().get(0).getAuthor().getUserId());
		Assertions.assertEquals(TestUtils.AUTHOR_NICKNAME, response.getBody().getArticles().get(0).getAuthor().getNickname());
		Assertions.assertEquals(TestUtils.ARTICLE_TAGS.size(), response.getBody().getArticles().get(0).getTags().size());
	}

	@Test
	void return_article_exactly_equal_to_summary_prop_if_saved_article_is_more_than_summary_prop() {

		createSomeArticle(articleProp.getSummary().getPageSize() + 1);

		mockUserProfileSummary(createProfileSummaryResp());
		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), "/" + TestUtils.AUTHOR_ID + "/" + 1);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(articleProp.getSummary().getPageSize(), response.getBody().getArticles().size());
	}

	@Test
	void return_all_article_if_count_of_saved_article_is_less_than_summary_prop() {

		createSomeArticle(articleProp.getSummary().getPageSize() - 1);

		mockUserProfileSummary(createProfileSummaryResp());
		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), "/" + TestUtils.AUTHOR_ID + "/" + 1);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(articleProp.getSummary().getPageSize() - 1, response.getBody().getArticles().size());
	}

	@Test
	void article_summary_should_end_with_3_point() {

		createSomeArticle(articleProp.getSummary().getPageSize() + 1);

		mockUserProfileSummary(createProfileSummaryResp());
		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), "/" + TestUtils.AUTHOR_ID + "/" + 1);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(articleProp.getSummary().getPageSize(), response.getBody().getArticles().size());

		for (ArticleSummaryDto article : response.getBody().getArticles()) {
			Assertions.assertEquals(articleProp.getSummary().getLength(), article.getSummary().length());
			Assertions.assertTrue(article.getSummary().endsWith("..."));
		}
	}

}
