package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;

public class EmptyTitleException extends BasePostServiceException {
	public EmptyTitleException(String message) {
		super(message);
	}

	public EmptyTitleException(Throwable cause) {
		super(cause);
	}

	public EmptyTitleException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_EMPTY_TITLE;
	}
}
