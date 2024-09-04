package org.karbit.article.core;

import org.junit.jupiter.api.BeforeEach;
import org.karbit.skeleton.base.result.dto.BaseRequest;
import org.karbit.skeleton.base.result.dto.BaseResponse;
import org.karbit.skeleton.base.result.exception.NotImplementedException;
import org.karbit.test.Container;
import org.karbit.test.EnableTestContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = ArticleManagerBootstrap.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({ "classpath:application-test.properties" })
@EnableTestContainer(containers = { Container.MONGO_DB })
public abstract class AbstractIntegrationTest {

	@LocalServerPort
	protected int PORT;

	private static final String URL_PREFIX = "/doc";

	@Autowired
	protected TestUtils testUtils;

	@Autowired
	protected TestRestTemplate restTemplate;

	protected abstract String getServiceUrl();

	@BeforeEach
	protected abstract void beforeTest();

	public String getTestUrl() {
		return String.format("http://localhost:%s%s%s", PORT, URL_PREFIX, getServiceUrl());
	}

	public String getTestUrl(String serviceUrl) {
		return String.format("http://localhost:%s%s%s", PORT, URL_PREFIX, serviceUrl);
	}

	public String getTestUrlWithParam(String param) {
		return String.format("http://localhost:%s%s%s/%s", PORT, URL_PREFIX, getServiceUrl(), param);
	}

	public String getTestUrlWithParam(String serviceUrl, String param) {
		return String.format("http://localhost:%s%s%s/%s", PORT, URL_PREFIX, serviceUrl, param);
	}

	public ResponseEntity<? extends BaseResponse> sendRequest(HttpHeaders headers, BaseRequest request) {
		throw new NotImplementedException("not implement method!");
	}

	public ResponseEntity<? extends BaseResponse> sendRequest(HttpHeaders headers, String value) {
		throw new NotImplementedException("not implement method!");
	}

	public ResponseEntity<? extends BaseResponse> sendRequest(HttpHeaders headers, int value) {
		throw new NotImplementedException("not implement method!");
	}
}
