package org.karbit.postmng.common.dto.response;

import lombok.Data;
import org.karbit.postmng.common.ResultStatus;
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
