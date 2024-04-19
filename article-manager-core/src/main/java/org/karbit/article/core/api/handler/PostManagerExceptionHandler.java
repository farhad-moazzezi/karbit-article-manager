package org.karbit.article.core.api.handler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.core.util.BundleUtil;
import org.karbit.postmng.common.dto.response.BasePostServiceResponse;
import org.karbit.postmng.common.exception.ArticleNotFoundException;
import org.karbit.postmng.common.exception.BasePostServiceException;
import org.karbit.skeleton.base.result.dto.BaseResponse;
import org.karbit.skeleton.base.result.dto.ResultSummary;
import org.karbit.skeleton.base.result.exception.BaseException;
import org.karbit.skeleton.base.result.exception.ServiceInvocationException;
import org.karbit.user.common.ResultStatus;
import org.karbit.user.common.dto.response.BaseUserServiceResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class PostManagerExceptionHandler extends ResponseEntityExceptionHandler {

	private final BundleUtil bundleUtil;

	@ExceptionHandler(BasePostServiceException.class)
	public final ResponseEntity<BaseResponse> handleBusinessException(BasePostServiceException exception) {
		logger.error("invalid param error", exception);
		return ResponseEntity.unprocessableEntity().body(
				new BasePostServiceResponse(
						ResultSummary.of(exception.getResult())
				)
		);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.error("invalid param error", exception);
		FieldError fieldError = exception.getFieldError();
		String message;
		if (Objects.nonNull(fieldError) && StringUtils.hasText(fieldError.getDefaultMessage())) {
			message = bundleUtil.getMessage(fieldError.getDefaultMessage());
		} else {

			message = MessageFormat.format(
					ResultStatus.INVALID_INPUT_PARAM.getMessage(),
					exception.getFieldError().getField()
			);
		}

		return ResponseEntity.badRequest().body(
				new BaseUserServiceResponse(
						ResultSummary.of(
								ResultStatus.INVALID_INPUT_PARAM.getStatusCode(),
								message
						)
				)
		);
	}
}
