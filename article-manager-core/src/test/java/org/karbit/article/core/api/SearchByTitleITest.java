package org.karbit.article.core.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.karbit.article.core.TestUtils;
import org.karbit.article.core.dao.ArticleDao;

import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.common.dto.request.SearchByTitleReq;
import org.karbit.article.common.dto.response.ArticleSummaryResp;
import org.karbit.skeleton.base.result.dto.BaseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SearchByTitleITest extends ArticleManagerITest {
	public static final String BASE_URL = "/article/search";

	private static final String ARTICLE_TITLE = "this article should show in search by title result";

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
		mockUserProfileSummary(createProfileSummaryResp());
		testUtils.createArticle(ARTICLE_TITLE, ArticleStatus.CONFIRMED);
	}

	@AfterEach
	protected void afterTest() {
		clearDb();
	}

	private void clearDb() {
		articleDao.deleteAll();
	}

	@Override
	public ResponseEntity<ArticleSummaryResp> sendRequest(HttpHeaders headers, BaseRequest request) {
		return restTemplate.exchange(getTestUrl(), HttpMethod.POST,
				new HttpEntity<>(request, headers), ArticleSummaryResp.class);
	}

	@Test
	void getArticleSummary_success() {

		testUtils.createArticle("this article should not show in search result...!", ArticleStatus.CONFIRMED);

		SearchByTitleReq searchByTitleReq = new SearchByTitleReq();
		searchByTitleReq.setValue("search by title");

		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), searchByTitleReq);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(1, response.getBody().getArticles().size());
		Assertions.assertEquals(2, articleDao.findAll().size());

		Assertions.assertEquals(ARTICLE_TITLE, response.getBody().getArticles().get(0).getTitle());
		Assertions.assertEquals(TestUtils.AUTHOR_ID, response.getBody().getArticles().get(0).getAuthor().getUserId());
		Assertions.assertEquals(TestUtils.AUTHOR_NICKNAME, response.getBody().getArticles().get(0).getAuthor().getNickname());
		Assertions.assertEquals(TestUtils.ARTICLE_TAGS.size(), response.getBody().getArticles().get(0).getTags().size());

	}

	@Test
	void do_not_show_if_any_title_contain_search_value() {

		SearchByTitleReq searchByTitleReq = new SearchByTitleReq();
		searchByTitleReq.setValue("not contain value");

		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), searchByTitleReq);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(0, response.getBody().getArticles().size());
		Assertions.assertEquals(1, articleDao.findAll().size());

	}

	@Test
	void search_by_persian_letter() {

		testUtils.createArticle("قراره این مقاله پیدا بشه و داریم سرچ فارسی رو تست میکنیم...!", ArticleStatus.CONFIRMED);

		SearchByTitleReq searchByTitleReq = new SearchByTitleReq();
		searchByTitleReq.setValue("فارسی");

		ResponseEntity<ArticleSummaryResp> response = sendRequest(testUtils.getDefaultHeader(), searchByTitleReq);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(response.getBody().getArticles());
		Assertions.assertEquals(1, response.getBody().getArticles().size());
		Assertions.assertEquals(2, articleDao.findAll().size());

	}

}
