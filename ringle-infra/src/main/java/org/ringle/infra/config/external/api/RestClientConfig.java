package org.ringle.infra.config.external.api;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.ringle.infra.InfraBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig implements InfraBaseConfig {

	@Bean
	@Primary
	public RestClient generalRestClient() {
		return createConfiguredRestClientBuilder().build();
	}

	@Bean("geminiApiRestClient")
	public RestClient geminiRestClient() {
		return createConfiguredRestClientBuilder()
			.baseUrl("https://generativelanguage.googleapis.com/v1beta")
			.build();
	}

	private RestClient.Builder createConfiguredRestClientBuilder() {
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(Timeout.ofMilliseconds(5000))
			.setResponseTimeout(Timeout.ofMilliseconds(5000))
			.build();

		CloseableHttpClient httpClient = HttpClients.custom()
			.setDefaultRequestConfig(requestConfig)
			.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return RestClient.builder()
			.requestFactory(factory);
	}
}
