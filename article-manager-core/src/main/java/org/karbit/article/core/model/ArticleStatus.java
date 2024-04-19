package org.karbit.article.core.model;

import java.util.Arrays;
import java.util.Iterator;

import org.springframework.util.CollectionUtils;

public enum ArticleStatus {
	REMOVED(0),
	SUSPENDED(1),
	CONFIRMED(2),
	PEND(3),
	DRAFTED(4),
	REJECTED(5),
	CHECKING(6);

	private final int id;

	ArticleStatus(int id) {
		this.id = id;
	}
	
	public void validateNextState(ArticleStatus targetState) {
		Iterator<ArticleStatus> validNextStates = Arrays.stream(getNextState(this)).iterator();
		if (Boolean.FALSE.equals(CollectionUtils.contains(validNextStates, targetState))) {
			throw new IllegalStateException("Can not change state of Post to -> " + targetState);
		}
	}
	
	public ArticleStatus[] getNextState(ArticleStatus status) {
		return switch (status) {
			case REMOVED -> null;
			case CONFIRMED -> new ArticleStatus[] { SUSPENDED, REMOVED };
			case PEND -> new ArticleStatus[] { DRAFTED, REMOVED };
			case DRAFTED -> new ArticleStatus[] { PEND, REMOVED };
			case REJECTED -> new ArticleStatus[] { DRAFTED };
			case CHECKING -> new ArticleStatus[] { CONFIRMED, REJECTED };
			default -> throw new IllegalStateException("Unexpected value: " + status);
		};
	}
	
	public int getId() {
		return id;
	}

	public static ArticleStatus of(Integer statusId) {
		return Arrays.stream(ArticleStatus.values()).filter(status -> status.id == statusId).findFirst().orElseThrow(() -> new IllegalArgumentException("ca not find post status with id: " + statusId));
	}
}
