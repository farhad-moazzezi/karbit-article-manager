package org.karbit.article.common.dto.response;

import lombok.Data;
import org.karbit.article.common.ResultStatus;
import org.karbit.skeleton.base.result.dto.BaseResponse;
import org.karbit.skeleton.base.result.dto.ResultSummary;

@Data
public class BasePostServiceResponse extends BaseResponse {

	public BasePostServiceResponse() {
		this(
				ResultSummary.of(ResultStatus.SUCCESS)
		);
	}

	public BasePostServiceResponse(ResultSummary result) {
		setResult(result);
	}
}
