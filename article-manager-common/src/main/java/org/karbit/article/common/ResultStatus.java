package org.karbit.article.common;

import org.karbit.skeleton.base.result.DefaultResultStatus;

public class ResultStatus extends DefaultResultStatus {

	public static final ResultStatus EX_EMPTY_AUTHOR_ID = new ResultStatus(422003001, "ex.empty.author.id");

	public static final ResultStatus EX_EMPTY_TITLE = new ResultStatus(422003002, "ex.empty.title");

	public static final ResultStatus EX_EMPTY_CONTENT = new ResultStatus(422003003, "ex.empty.content");

	public static final ResultStatus EX_EMPTY_TAG = new ResultStatus(422003004, "ex.empty.tag");

	public static final ResultStatus EX_INVOCATION_USER_SERVICE = new ResultStatus(422003005, "ex.service.invocation.user");

	public static final ResultStatus EX_NOT_FOUND_AUTHOR = new ResultStatus(422003006, "ex.not.found.author");

	public static final ResultStatus EX_NOT_FOUND_ARTICLE = new ResultStatus(422003007, "ex.not.found.article");

	public static final ResultStatus EX_TOO_SHORT_CONTENT = new ResultStatus(422003008, "ex.too.short.content");

	public static final ResultStatus EX_NOT_FOUND_POST = new ResultStatus(422003009, "ex.not.found.post");

	protected ResultStatus(int statusCode, String bundleId) {
		super(statusCode, bundleId);
	}

	@Override
	protected String[] getMessageResourceName() {
		return new String[] { "default-message", "parameterized-message", "message" };
	}
}
