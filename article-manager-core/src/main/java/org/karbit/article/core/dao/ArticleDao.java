package org.karbit.article.core.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.karbit.article.core.model.Article;
import org.karbit.article.core.model.ArticleStatus;
import org.karbit.post.model.PostableStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDao extends MongoRepository<Article, String> {

	Optional<Article> findByUniqueId(String articleId);

	Optional<Article> findByUniqueIdAndStatus(String articleId,ArticleStatus status);

	List<Article> findByStatusInOrderByCreationDateDesc(Set<ArticleStatus> statuses);

	List<Article> findByStatusInOrderByCreationDateDesc(Set<ArticleStatus> statuses, Pageable pageable);

	List<Article> findByAuthorUserIdAndStatusInOrderByCreationDateDesc(String authorUserId, Set<ArticleStatus> statuses, Pageable pageable);

	List<Article> findByTitleLikeAndStatusInOrderByCreationDateDesc(String title, Set<ArticleStatus> statuses, Pageable pageable);
}
