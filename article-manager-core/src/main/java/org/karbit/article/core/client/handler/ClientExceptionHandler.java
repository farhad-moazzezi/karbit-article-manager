package org.karbit.article.core.client.handler;

import java.util.Arrays;
import java.util.List;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.core.client.exception.UserServiceInvocationException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientExceptionHandler extends ErrorDecoder.Default {
	private static final List<HttpStatus> BAD_RESULT =
			Arrays.asList(HttpStatus.BAD_GATEWAY,
					HttpStatus.GATEWAY_TIMEOUT,
					HttpStatus.SERVICE_UNAVAILABLE,
					HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.UNPROCESSABLE_ENTITY);

	@Override
	public Exception decode(String methodKey, Response response) {
		if (BAD_RESULT.contains(HttpStatus.valueOf(response.status()))) {
			log.error("error in invoke -> service name : {}", methodKey);
			return new UserServiceInvocationException(response.body().toString());
		}
		return super.decode(methodKey, response);
	}
}
