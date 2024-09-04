package org.karbit.article.common.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.karbit.skeleton.base.result.dto.SearchableReq;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SearchByTitleReq extends SearchableReq {
}
