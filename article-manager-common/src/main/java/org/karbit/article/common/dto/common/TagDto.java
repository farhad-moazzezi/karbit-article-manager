package org.karbit.article.common.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
	private String tagId;

	private String caption;

	public static TagDto of(String tagId, String caption) {
		return new TagDto(tagId, caption);
	}
}
