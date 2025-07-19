package org.ringle.apis.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;

@Configuration
@Profile("!test")
public class GoogleCloudConfig {

	@Value("${spring.cloud.gcp.credentials.path}")
	private String credentialsPath;

	@Bean
	public SpeechClient speechClient() throws IOException {
		CredentialsProvider credentialsProvider = createCredentialsProvider();

		SpeechSettings speechSettings = SpeechSettings.newBuilder()
			.setCredentialsProvider(credentialsProvider)
			.build();

		return SpeechClient.create(speechSettings);
	}

	private CredentialsProvider createCredentialsProvider() throws IOException {
		String cleanPath = credentialsPath.replace("optional:file:", "");
		Resource credentialResource = new FileSystemResource(cleanPath);

		if (credentialResource.exists()) {
			GoogleCredentials credentials = GoogleCredentials.fromStream(credentialResource.getInputStream())
				.createScoped(SpeechSettings.getDefaultServiceScopes());
			return FixedCredentialsProvider.create(credentials);
		}

		return SpeechSettings.defaultCredentialsProviderBuilder().build();
	}
}
