package org.karbit.article.common.exception;

import org.karbit.article.common.ResultStatus;

public class AuthorNotFoundException extends BasePostServiceException {
	public AuthorNotFoundException(String message) {
		super(message);
	}

	public AuthorNotFoundException(Throwable cause) {
		super(cause);
	}

	public AuthorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_NOT_FOUND_AUTHOR;
	}
}
