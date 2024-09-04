package org.karbit.article.common.exception;

import org.karbit.article.common.ResultStatus;
import org.karbit.skeleton.base.result.exception.BaseException;

public class PostNotFoundException extends BaseException {
	public PostNotFoundException(String message) {
		super(message);
	}

	public PostNotFoundException(Throwable cause) {
		super(cause);
	}

	public PostNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_NOT_FOUND_POST;
	}
}
