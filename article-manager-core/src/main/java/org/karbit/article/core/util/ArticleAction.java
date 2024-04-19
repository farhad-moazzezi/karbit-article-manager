package org.karbit.article.core.util;

import lombok.Getter;

@Getter
public enum ArticleAction {
	CREATED("action.sign-in");

	ArticleAction(String id) {
		this.id = id;
	}

	private String id;
}
