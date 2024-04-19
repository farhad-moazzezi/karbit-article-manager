package org.karbit.article.core.biz.user;

import org.karbit.article.core.model.Author;

public interface UserService {
	Author getAuthorProfile(String userId);
}
