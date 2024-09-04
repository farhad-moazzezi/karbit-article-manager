package org.karbit.article.core.biz.user;

import java.util.Optional;

import org.karbit.article.core.model.Author;

public interface UserService {
	Optional<Author> getAuthorProfile(String userId);
}
