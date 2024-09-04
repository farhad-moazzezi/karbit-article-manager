package org.karbit.article.common.exception;

import org.karbit.article.common.ResultStatus;

public class TooShortContentException extends BasePostServiceException {
	public TooShortContentException(String message) {
		super(message);
	}

	public TooShortContentException(Throwable cause) {
		super(cause);
	}

	public TooShortContentException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_TOO_SHORT_CONTENT;
	}
}
