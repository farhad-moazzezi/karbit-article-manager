package org.karbit.article.core.client.tagmanager;

import org.karbit.tagmng.common.dto.request.FinsertReq;
import org.karbit.tagmng.common.dto.request.SearchTagReq;
import org.karbit.tagmng.common.dto.response.FoundTagResp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "tag-service", url = "${service.tag-manager.url}")
public interface TagClient {

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	FoundTagResp searchTag(@RequestBody SearchTagReq searchTagReq);

	@PostMapping(value = "/finsert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	FoundTagResp findOrInsertTag(@RequestBody FinsertReq finsertReq);
}
