package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

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
