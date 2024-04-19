package org.karbit.postmng.common.exception;

import lombok.Setter;
import org.karbit.postmng.common.ResultStatus;
import org.karbit.skeleton.base.result.exception.BaseException;

public class BasePostServiceException extends BaseException {
	@Setter
	private ResultStatus resultStatus;

	public BasePostServiceException(String message) {
		super(message);
	}

	public BasePostServiceException(Throwable cause) {
		super(cause);
	}

	public BasePostServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return resultStatus;
	}
}
