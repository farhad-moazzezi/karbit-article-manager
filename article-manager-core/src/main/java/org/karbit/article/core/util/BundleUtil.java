package org.karbit.article.core.util;

import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BundleUtil {
	private final MessageSource messageSource;

	private Locale locale;

	public BundleUtil(@Qualifier("bundleHolder") MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String messageId, Object... messageInput) {
		return messageSource.getMessage(messageId, messageInput, getLocale());
	}

	private Locale getLocale() {
		if (Boolean.FALSE.equals(Objects.isNull(locale))) {
			return locale;
		}
		locale = LocaleContextHolder.getLocale();
		return locale;
	}
}
