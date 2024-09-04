package org.karbit.article.core.biz.article.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.core.biz.article.ArticleService;
import org.karbit.article.core.biz.article.dto.input.ArticleModel;
import org.karbit.article.core.biz.article.dto.input.DraftedArticleModel;
import org.karbit.article.core.biz.article.dto.output.ArticleDetail;
import org.karbit.article.core.biz.article.dto.output.ArticleSummary;
import org.karbit.article.core.biz.tag.TagService;
import org.karbit.article.core.dao.ArticleDao;
import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.core.model.Author;
import org.karbit.article.core.model.Tag;
import org.karbit.article.common.exception.ArticleNotFoundException;
import org.karbit.article.common.exception.AuthorNotFoundException;
import org.karbit.article.common.exception.EmptyAuthorIdException;
import org.karbit.article.common.exception.EmptyContentException;
import org.karbit.article.common.exception.EmptyTagException;
import org.karbit.article.common.exception.EmptyTitleException;
import org.karbit.article.common.exception.TooShortContentException;
import org.karbit.article.core.biz.article.mapper.article.ArticleServiceMapper;
import org.karbit.article.core.biz.user.UserService;
import org.karbit.article.core.config.ArticleProp;
import org.karbit.article.core.model.Article;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
class ArticleServiceImpl implements ArticleService {

	private final ArticleDao articleDao;

	private final ArticleServiceMapper articleServiceMapper;

	private final UserService userService;

	private final TagService tagService;

	private final ArticleProp articleProp;

	@Override
	public ArticleDetail getDetail(String articleId) {
		return articleServiceMapper.toArticleDetail(findArticle(articleId));
	}

	@Override
	public List<ArticleSummary> getSummary(Set<ArticleStatus> statuses, Integer pageNumber, Integer pageSize) {
		List<Article> articles = articleDao.findByStatusInOrderByCreationDateDesc(statuses, Pageable.ofSize(pageSize).withPage(pageNumber));
		return articleServiceMapper.toSummary(articles);
	}

	@Override
	public List<ArticleSummary> getSummary(String userId, Set<ArticleStatus> statuses, Integer pageNumber, Integer pageSize) {
		List<Article> articles = articleDao.findByAuthorUserIdAndStatusInOrderByCreationDateDesc(userId, statuses, Pageable.ofSize(pageSize).withPage(pageNumber));
		return articleServiceMapper.toSummary(articles);
	}

	@Override
	public List<ArticleSummary> findByTitle(String title, Set<ArticleStatus> statuses, int pageNumber, int pageSize) {
		List<Article> articles = articleDao.findByTitleLikeAndStatusInOrderByCreationDateDesc(title, statuses, PageRequest.of(pageNumber, pageSize));
		return articleServiceMapper.toSummary(articles);
	}

	@Override
	public String createDraft(DraftedArticleModel draftedArticleModel) {
		Article article;
		if (StringUtils.hasText(draftedArticleModel.getArticleId())) {
			article = updateExistDraftedArticle(draftedArticleModel);
		} else {
			Optional<Author> author = getAuthorProfile(draftedArticleModel.getAuthorId());
			validateAuthor(author);
			article = createNewDraftedArticle(draftedArticleModel, author.get());
		}
		return article.getUniqueId();
	}

	@Override
	public String createNewArticle(ArticleModel articleModel) {
		log.debug("going to create article -> {}", articleModel);
		checkBeforeCreate(articleModel);
		Optional<Author> author = getAuthorProfile(articleModel.getAuthorId());
		validateAuthor(author);
		Set<Tag> tags = tagService.findOrInsertTag(articleModel.getTags());
		checkAfterFoundTags(tags);
		Article article = new Article();
		article.setTitle(articleModel.getTitle());
		article.setContent(articleModel.getContent());
		article.setAuthor(author.get());
		article.setTags(tags);
		article.setStatus(ArticleStatus.PEND);
		doBeforeSave(article);
		article = save(article);
		doAfterSave(article);
		return article.getUniqueId();
	}

	private void checkAfterFoundTags(Set<Tag> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			throw new EmptyTagException("article`s Tags is empty!");
		}
	}

	private Article updateExistDraftedArticle(DraftedArticleModel draftArticle) {
		Article article = findArticle(draftArticle.getArticleId());
		Set<Tag> tags = tagService.findOrInsertTag(draftArticle.getTags());
		article.setTitle(draftArticle.getTitle());
		article.setContent(draftArticle.getContent());
		article.setTags(tags);
		article.setLastModifyDate(new Date());
		return articleDao.save(article);
	}

	private Article findArticle(String articleId) {
		return articleDao.findByUniqueId(articleId).orElseThrow(() ->
				new ArticleNotFoundException("article not found by id -> articleId : " + articleId)
		);
	}

	private Article findArticle(String articleId, ArticleStatus articleStatus) {
		return articleDao.findByUniqueId(articleId).orElseThrow(() ->
				new ArticleNotFoundException("article not found by id -> articleId : " + articleId)
		);
	}

	private Article createNewDraftedArticle(DraftedArticleModel draftArticle, Author author) {
		Set<Tag> tags = tagService.findOrInsertTag(draftArticle.getTags());
		Article article = articleServiceMapper.toNewDraftArticle(author, draftArticle, tags);
		return articleDao.save(article);
	}

	private Article save(Article article) {
		log.debug("going to save article -> {}", article);
		return articleDao.save(article);
	}

	private void doBeforeSave(Article article) {
	}

	private void checkBeforeCreate(ArticleModel articleModel) {
		log.debug("going to check input value -> {}", articleModel);
		validateAuthor(articleModel);
		checkTitle(articleModel);
		checkContent(articleModel);
	}

	private void checkContent(ArticleModel articleModel) {
		if (Boolean.FALSE.equals(StringUtils.hasText(articleModel.getContent()))) {
			throw new EmptyContentException("content is empty!");
		}
		if (articleModel.getContent().length() < articleProp.getContent().getMinLength()) {
			throw new TooShortContentException("article`s content is too short!");
		}
	}

	private void checkTitle(ArticleModel articleModel) {
		if (Boolean.FALSE.equals(StringUtils.hasText(articleModel.getTitle()))) {
			throw new EmptyTitleException("title is empty!");
		}
	}

	private Optional<Author> getAuthorProfile(String userId) {
		log.info("gonna to find author profile summary -> userId : {}", userId);
		Optional<Author> author = userService.getAuthorProfile(userId);
		log.debug("found author profile -> {}", author);
		return author;
	}

	private void validateAuthor(ArticleModel model) {
		if (Objects.isNull(model.getAuthorId())) {
			throw new EmptyAuthorIdException("author is empty!");
		}
		if (Boolean.FALSE.equals(StringUtils.hasText(model.getAuthorId()))) {
			throw new EmptyAuthorIdException("author userId is empty!");
		}
	}

	private void validateAuthor(Optional<Author> author) {
		if (Boolean.FALSE.equals(author.isPresent())) {
			throw new AuthorNotFoundException("author not found!");
		}
	}

	private void doAfterSave(Article post) {
		publishCreationEvent(post);
	}

	private void publishCreationEvent(Article article) {
		log.debug("going to publish article creation event -> {}", article);
	}
}
