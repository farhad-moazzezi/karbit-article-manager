package org.karbit.article.core.client.tagmanager.mapper;

import java.util.List;
import java.util.Set;

import org.karbit.article.core.model.Tag;
import org.karbit.tagmng.common.dto.common.TagInfo;
import org.karbit.tagmng.common.dto.request.FinsertReq;
import org.karbit.tagmng.common.dto.response.FoundTagResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TagMapper {

	Set<Tag> toTags(List<TagInfo> tagInfos);

	@Named("toTag")
	@Mapping(target = "tagId", source = "tagId")
	@Mapping(target = "caption", source = "caption")
	Tag toTags(TagInfo tagInfo);

	default FinsertReq toFindTagModel(Set<String> tags) {
		return new FinsertReq(tags);
	}

	Tag toTag(TagInfo tagInfo);
}
