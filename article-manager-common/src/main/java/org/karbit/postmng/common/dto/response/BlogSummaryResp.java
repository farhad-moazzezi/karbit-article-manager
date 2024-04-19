package org.karbit.postmng.common.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.karbit.postmng.common.dto.common.BlogSummaryInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogSummaryResp extends BasePostServiceResponse {
	private List<BlogSummaryInfo> articles;
}
