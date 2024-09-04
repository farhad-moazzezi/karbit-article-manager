package org.karbit.article.core.api;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.common.ResultStatus;
import org.karbit.article.common.dto.request.CreateArticleReq;
import org.karbit.article.common.dto.response.CreateArticleResp;
import org.karbit.article.core.TestUtils;
import org.karbit.article.core.client.tagmanager.TagClient;
import org.karbit.article.core.dao.ArticleDao;
import org.karbit.article.core.model.Article;
import org.karbit.skeleton.base.result.dto.BaseRequest;
import org.karbit.skeleton.base.result.dto.ResultSummary;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class CreateArticleITest extends ArticleManagerITest {
	public static final String BASE_URL = "/article/create";

	private CreateArticleReq SAMPLE_REQUEST;

	@Autowired
	private ArticleDao articleDao;

	@MockBean
	private TagClient tagClient;

	@Override
	protected String getServiceUrl() {
		return BASE_URL;
	}

	@Override
	@BeforeEach
	protected void beforeTest() {
		clearDb();
		SAMPLE_REQUEST = createArticleReq();
	}

	@Override
	public ResponseEntity<CreateArticleResp> sendRequest(HttpHeaders headers, BaseRequest request) {
		return restTemplate.exchange(getTestUrl(), HttpMethod.POST,
				new HttpEntity<>(request, headers), CreateArticleResp.class);
	}

	private void clearDb() {
		articleDao.deleteAll();
	}

	@Test
	void createArticle_success() {

		Mockito.when(tagClient.findOrInsertTag(any())).thenReturn(TestUtils.TagUtils.createTagResp());

		mockUserProfileSummary(createProfileSummaryResp());

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(TestUtils.AUTHOR_ID), createArticleReq());

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Article> articles = articleDao.findAll();
		assertThat(articles).isNotEmpty();
		assertThat(articles.size()).isEqualTo(1);
		assertThat(articles.get(0).getTitle()).isEqualTo(TestUtils.POST_TITLE);
		assertThat(articles.get(0).getContent()).isEqualTo(TestUtils.POST_CONTENT);
		assertThat(articles.get(0).getAuthor().getUserId()).isEqualTo(TestUtils.AUTHOR_ID);
		assertThat(articles.get(0).getUniqueId()).isNotEmpty();
		assertThat(articles.get(0).getStatus()).isEqualTo(ArticleStatus.PEND);
	}

	@Test
	void prevent_to_createArticle_with_short_content() {
		mockUserProfileSummary(createProfileSummaryResp());

		CreateArticleReq request = createArticleReq();
		request.setContent("SHORT_CONTENT");

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(TestUtils.AUTHOR_ID), request);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		Assertions.assertEquals(ResultSummary.of(ResultStatus.EX_TOO_SHORT_CONTENT), response.getBody().getResult());
	}

	@ParameterizedTest
	@MethodSource(TestUtils.EMPTY_STRING_VALUES)
	void prevent_to_createArticle_with_empty_authorId(String userId) {

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(userId), SAMPLE_REQUEST);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		Assertions.assertEquals(ResultSummary.of(ResultStatus.EX_EMPTY_AUTHOR_ID), response.getBody().getResult());
	}

	@Test
	void prevent_to_createArticle_when_UserNotFound_by_userId() {
		mockUserProfileSummary(null);
		Mockito.when(tagClient.findOrInsertTag(any())).thenReturn(TestUtils.TagUtils.createTagResp());

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(TestUtils.AUTHOR_ID), createArticleReq());

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		Assertions.assertEquals(ResultSummary.of(ResultStatus.EX_NOT_FOUND_AUTHOR), response.getBody().getResult());

		List<Article> articles = articleDao.findAll();
		assertThat(articles).isEmpty();
	}

	@ParameterizedTest
	@MethodSource(TestUtils.EMPTY_STRING_VALUES)
	void prevent_to_createArticle_with_empty_articleTitle(String title) {
		SAMPLE_REQUEST.setTitle(title);

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(TestUtils.AUTHOR_ID), SAMPLE_REQUEST);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		Assertions.assertEquals(ResultSummary.of(ResultStatus.EX_EMPTY_TITLE), response.getBody().getResult());
	}

	@ParameterizedTest
	@MethodSource(TestUtils.EMPTY_STRING_VALUES)
	void prevent_to_createArticle_with_empty_articleContent(String content) {

		SAMPLE_REQUEST.setContent(content);

		ResponseEntity<CreateArticleResp> response = sendRequest(testUtils.getDefaultHeader(TestUtils.AUTHOR_ID), SAMPLE_REQUEST);

		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		Assertions.assertEquals(ResultSummary.of(ResultStatus.EX_EMPTY_CONTENT), response.getBody().getResult());

	}

	private static CreateArticleReq createArticleReq() {
		CreateArticleReq request = new CreateArticleReq();
		request.setTitle(TestUtils.POST_TITLE);
		request.setContent(TestUtils.POST_CONTENT);
		request.setTagLabel(TestUtils.TAG_LABELS);

		return request;
	}
}
