package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

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
