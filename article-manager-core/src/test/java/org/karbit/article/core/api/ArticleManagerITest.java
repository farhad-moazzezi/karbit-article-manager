package org.karbit.article.core.api;

import org.karbit.article.core.AbstractIntegrationTest;
import org.karbit.article.core.TestUtils;
import org.karbit.article.core.client.usermanager.UserClient;
import org.karbit.user.common.dto.response.ProfileSummaryResp;

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


abstract class ArticleManagerITest extends AbstractIntegrationTest {

	@MockBean
	private UserClient userClient;

	public void mockUserProfileSummary(ProfileSummaryResp profileSummaryResp) {
		when(userClient.getProfileSummary(any())).thenReturn(profileSummaryResp);
	}

	public ProfileSummaryResp createProfileSummaryResp() {
		return createProfileSummaryResp(TestUtils.AUTHOR_ID, TestUtils.AUTHOR_NICKNAME);
	}

	public ProfileSummaryResp createProfileSummaryResp(String userId, String nickname) {
		ProfileSummaryResp profileSummaryResp = new ProfileSummaryResp();
		profileSummaryResp.setUserId(userId);
		profileSummaryResp.setNickname(nickname);
		return profileSummaryResp;
	}
}
