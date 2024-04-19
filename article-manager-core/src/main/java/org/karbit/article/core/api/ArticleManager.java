package org.karbit.article.core.api;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.karbit.article.core.biz.article.ArticleService;
import org.karbit.article.core.biz.article.dto.output.ArticleDetail;
import org.karbit.article.core.biz.article.dto.output.ArticleSummary;
import org.karbit.article.core.biz.article.mapper.article.ArticleServiceMapper;
import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.core.util.PaginationUtil;
import org.karbit.postmng.common.dto.request.CreateArticleReq;
import org.karbit.postmng.common.dto.request.DraftArticleReq;
import org.karbit.postmng.common.dto.request.SearchByTitleReq;
import org.karbit.postmng.common.dto.response.ArticleDetailResp;
import org.karbit.postmng.common.dto.response.ArticleSummaryResp;
import org.karbit.postmng.common.dto.response.CreateArticleResp;
import org.karbit.article.core.config.ArticleProp;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleManager {

	private final ArticleService articleService;

	private final ArticleServiceMapper mapper;

	private final ArticleProp articleProp;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateArticleResp> createArticle(@RequestBody CreateArticleReq createArticleReq, @RequestHeader("User-Id") String userId) {
		String articleId = articleService.createNewArticle(mapper.toNewArticleModel(createArticleReq, userId));
		return ResponseEntity.ok(new CreateArticleResp(articleId));
	}

	@PostMapping(value = "/draft", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateArticleResp> createDraftArticle(@RequestBody DraftArticleReq draftArticleReq, @RequestHeader("User-Id") String userId) {
		String articleId = articleService.createDraft(mapper.toDraftArticleModel(draftArticleReq, userId));
		return ResponseEntity.ok(new CreateArticleResp(articleId));
	}

	@GetMapping(value = "/detail/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleDetailResp> getArticleDetail(@PathVariable String articleId) {
		ArticleDetail detail = articleService.getDetail(articleId);
		return ResponseEntity.ok(mapper.toArticleDetailResp(detail));
	}

	@GetMapping(value = "/summary/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleSummaryResp> getArticleSummary(@PathVariable(required = false) int page) {
		List<ArticleSummary> articles =
				articleService.getSummary(
						Collections.singleton(ArticleStatus.CONFIRMED),
						PaginationUtil.convert(page),
						articleProp.getSummary().getPageSize()
				);
		return ResponseEntity.ok(new ArticleSummaryResp(mapper.toArticleSummaryDto(articles)));
	}

	@GetMapping(value = "/summary/{userId}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleSummaryResp> getUserArticleSummary(@PathVariable String userId, @PathVariable(required = false) int page) {
		List<ArticleSummary> articles =
				articleService.getSummary(
						userId,
						Collections.singleton(ArticleStatus.CONFIRMED),
						PaginationUtil.convert(page),
						articleProp.getSummary().getPageSize()
				);
		return ResponseEntity.ok(new ArticleSummaryResp(mapper.toArticleSummaryDto(articles)));
	}

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleSummaryResp> searchByTitle(@RequestBody SearchByTitleReq searchByTitleReq) {
		List<ArticleSummary> articles =
				articleService.findByTitle(
						searchByTitleReq.getValue(),
						Collections.singleton(ArticleStatus.CONFIRMED),
						PaginationUtil.convert(searchByTitleReq.getPage()),
						articleProp.getSummary().getPageSize()
				);
		return ResponseEntity.ok(new ArticleSummaryResp(mapper.toArticleSummaryDto(articles)));
	}
}
