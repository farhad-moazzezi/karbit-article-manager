package org.karbit.article.core.config;

import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.karbit.article.core.util.ArticleAction;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "notification.action")
public class NotificationProp {

	private Map<ArticleAction, Boolean> enabled;

	public boolean isEnabledNotification(ArticleAction articleAction) {
		if (Objects.isNull(enabled)) {
			return false;
		}
		Boolean result = enabled.get(articleAction);
		if (Objects.isNull(result)) {
			return false;
		}
		return result;
	}
}
