package org.karbit.postmng.common.exception;

import org.karbit.postmng.common.ResultStatus;
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
