package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

public class EmptyTagException extends BasePostServiceException {
	public EmptyTagException(String message) {
		super(message);
	}

	public EmptyTagException(Throwable cause) {
		super(cause);
	}

	public EmptyTagException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_EMPTY_TAG;
	}
}
