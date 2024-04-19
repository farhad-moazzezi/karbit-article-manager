package org.karbit.article.core.biz.tag;

import java.util.Set;

import org.karbit.article.core.model.Tag;

public interface TagService {
	Set<Tag> findOrInsertTag(Set<String> tagCaptions);
}
