package org.karbit.article.core.client.usermanager;

import org.karbit.user.common.dto.response.ProfileSummaryResp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "user-service", url = "${service.user-manager.url}")
public interface UserClient {

	@GetMapping(value = "/profile/summary/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ProfileSummaryResp getProfileSummary(@PathVariable String userId);
}
