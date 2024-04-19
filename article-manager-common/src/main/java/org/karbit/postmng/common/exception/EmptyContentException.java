package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

public class EmptyContentException extends BasePostServiceException {
	public EmptyContentException(String message) {
		super(message);
	}

	public EmptyContentException(Throwable cause) {
		super(cause);
	}

	public EmptyContentException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_EMPTY_CONTENT;
	}
}
