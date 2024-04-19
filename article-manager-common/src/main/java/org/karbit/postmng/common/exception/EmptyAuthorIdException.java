package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

public class EmptyAuthorIdException extends BasePostServiceException {
	public EmptyAuthorIdException(String message) {
		super(message);
	}

	public EmptyAuthorIdException(Throwable cause) {
		super(cause);
	}

	public EmptyAuthorIdException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_EMPTY_AUTHOR_ID;
	}
}
