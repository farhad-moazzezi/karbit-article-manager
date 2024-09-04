package org.karbit.article.core.biz.user.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karbit.article.core.biz.user.UserService;
import org.karbit.article.core.model.Author;
import org.karbit.article.core.biz.user.mapper.UserMapper;
import org.karbit.article.core.client.usermanager.UserClient;
import org.karbit.user.common.dto.response.ProfileSummaryResp;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserClient userClient;

	private final UserMapper userMapper;

	@Override
	public Optional<Author> getAuthorProfile(String userId) {
		log.info("gonna to find user profile summary -> userId : {}", userId);
		try {
			ProfileSummaryResp profileSummary = userClient.getProfileSummary(userId);
			log.debug("found profile -> {}", profileSummary);
			return Optional.of(userMapper.toAuthorProfile(profileSummary));
		} catch (Exception exception) {
			log.error("exception happen occur fetching user profile!", exception);
			return Optional.empty();
		}
	}
}
