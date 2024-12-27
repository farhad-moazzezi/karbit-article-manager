package org.karbit.article.core.biz.user.mapper;

import org.karbit.article.core.model.Author;
import org.karbit.user.common.dto.response.ProfileSummaryResp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	Author toAuthorProfile(ProfileSummaryResp profileSummary);

}
