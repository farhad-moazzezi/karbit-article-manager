package org.karbit.article.core.biz.tag.impl;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.karbit.article.core.biz.tag.TagService;
import org.karbit.article.core.client.tagmanager.TagClient;
import org.karbit.article.core.client.tagmanager.mapper.TagMapper;
import org.karbit.article.core.model.Tag;
import org.karbit.tagmng.common.dto.response.FoundTagResp;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	private final TagClient tagClient;

	private final TagMapper tagMapper;

	@Override
	public Set<Tag> findOrInsertTag(Set<String> tagCaptions) {
		FoundTagResp foundTags = tagClient.findOrInsertTag(tagMapper.toFindTagModel(tagCaptions));
		return tagMapper.toTags(foundTags.getTags());
	}
}
