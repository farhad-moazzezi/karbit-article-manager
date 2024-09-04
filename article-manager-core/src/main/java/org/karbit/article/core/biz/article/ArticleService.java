package org.karbit.article.core.biz.article;

import java.util.List;
import java.util.Set;

import org.karbit.article.core.biz.article.dto.input.ArticleModel;
import org.karbit.article.core.biz.article.dto.input.DraftedArticleModel;
import org.karbit.article.core.model.ArticleStatus;
import org.karbit.article.core.biz.article.dto.output.ArticleDetail;
import org.karbit.article.core.biz.article.dto.output.ArticleSummary;
import org.karbit.article.core.model.Article;

public interface ArticleService {

	ArticleDetail getDetail(String articleId);

	List<ArticleSummary> getSummary(Set<ArticleStatus> status, Integer pageNumber, Integer pageSize);

	List<ArticleSummary> getSummary(String userId, Set<ArticleStatus> statuses, Integer pageNumber, Integer pageSize);

	List<ArticleSummary> findByTitle(String title, Set<ArticleStatus> statuses, int pageNumber, int pageSize);

	String createDraft(DraftedArticleModel draftArticle);

	String createNewArticle(ArticleModel articleModel);
}
