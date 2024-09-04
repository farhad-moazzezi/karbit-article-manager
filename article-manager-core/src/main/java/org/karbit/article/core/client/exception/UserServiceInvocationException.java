package org.karbit.article.core.client.exception;

import org.karbit.article.common.ResultStatus;
import org.karbit.skeleton.base.result.exception.ServiceInvocationException;

public class UserServiceInvocationException extends ServiceInvocationException {
	public UserServiceInvocationException(String message) {
		super(message);
	}

	public UserServiceInvocationException(Throwable cause) {
		super(cause);
	}

	public UserServiceInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public ResultStatus getResult() {
		return ResultStatus.EX_INVOCATION_USER_SERVICE;
	}
}
