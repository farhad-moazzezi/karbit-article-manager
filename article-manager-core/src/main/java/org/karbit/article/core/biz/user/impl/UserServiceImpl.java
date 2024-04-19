package org.karbit.article.core.biz.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.core.biz.user.UserService;
import org.karbit.article.core.model.Author;
import org.karbit.post.biz.dto.AuthorProfile;
import org.karbit.article.core.biz.user.mapper.UserMapper;
import org.karbit.article.core.client.usermanager.UserClient;
import org.karbit.postmng.common.exception.AuthorNotFoundException;
import org.karbit.user.common.dto.response.ProfileSummaryResp;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserClient userClient;

	private final UserMapper userMapper;

	@Override
	public Author getAuthorProfile(String userId) {
		log.info("gonna to find user profile summary -> userId : {}", userId);
		try {
			ProfileSummaryResp profileSummary = userClient.getProfileSummary(userId);
			log.debug("found profile -> {}", profileSummary);
			return userMapper.toAuthorProfile(profileSummary);
		} catch (Exception exception) {
			throw new AuthorNotFoundException("author not found -> user-id: " + userId);
		}
	}
}
