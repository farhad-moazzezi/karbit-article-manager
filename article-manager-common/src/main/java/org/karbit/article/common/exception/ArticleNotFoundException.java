package org.karbit.article.common.exception;

import org.karbit.article.common.ResultStatus;

public class ArticleNotFoundException extends BasePostServiceException {
	public ArticleNotFoundException(String message) {
		super(message);
	}

	public ArticleNotFoundException(Throwable cause) {
		super(cause);
	}

	public ArticleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_NOT_FOUND_ARTICLE;
	}

}
