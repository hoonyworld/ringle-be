package org.ringle.apis.conversation.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.ringle.apis.conversation.dto.request.GeminiRequest;
import org.ringle.apis.conversation.dto.response.GeminiResponse;
import org.ringle.domain.conversation.ConversationMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LlmService {

	private final SseService sseService;
	private final ConversationService conversationService;
	private final ObjectMapper objectMapper;
	private final RestClient restClient; // WebClient 대신 RestClient를 다시 사용합니다.
	private final String geminiApiKey;
	private final String geminiModelName;

	public LlmService(
		SseService sseService,
		ConversationService conversationService,
		ObjectMapper objectMapper,
		@Qualifier("geminiApiRestClient") RestClient restClient, // WebClient 관련 주입을 제거하고 RestClient를 사용합니다.
		@Value("${gemini.api-key}") String geminiApiKey,
		@Value("${gemini.model-name}") String geminiModelName
	) {
		this.sseService = sseService;
		this.conversationService = conversationService;
		this.objectMapper = objectMapper;
		this.restClient = restClient;
		this.geminiApiKey = geminiApiKey;
		this.geminiModelName = geminiModelName;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private record GeminiStreamChunk(List<GeminiResponse.Candidate> candidates) {
	}

	public String streamLlMResponse(Long userId, UUID sessionId, String userMessage) {
		log.info("Streaming Gemini API response for session: {}", sessionId);

		String finalPrompt = createPromptWithHistory(conversationService.findConversationHistory(sessionId))
			+ "\nUSER: " + userMessage;
		GeminiRequest geminiRequest = GeminiRequest.createGemini(finalPrompt);

		StringBuilder fullResponse = new StringBuilder();

		try {
			ResponseEntity<Resource> responseEntity = restClient.post()
				.uri("/models/{model}:streamGenerateContent", geminiModelName)
				.header("X-goog-api-key", geminiApiKey)
				.header("Content-Type", "application/json")
				.body(geminiRequest)
				.retrieve()
				.toEntity(Resource.class);

			try (InputStream responseStream = responseEntity.getBody().getInputStream()) {
				JsonParser parser = objectMapper.getFactory().createParser(responseStream);

				if (parser.nextToken() != JsonToken.START_ARRAY) {
					throw new IllegalStateException("Gemini API response did not start with a JSON array.");
				}

				while (parser.nextToken() != JsonToken.END_ARRAY) {
					GeminiStreamChunk chunk = objectMapper.readValue(parser, GeminiStreamChunk.class);
					String token = parseTextFromGeminiChunk(chunk);
					if (token != null && !token.isEmpty()) {
						fullResponse.append(token);

						ObjectNode tokenNode = JsonNodeFactory.instance.objectNode();
						tokenNode.put("token", token);
						String eventData = objectMapper.writeValueAsString(tokenNode);

						sseService.sendEvent(userId, "llm_token", eventData);
					}
				}
			}

		} catch (Exception e) {
			log.error("Error during Gemini API call or stream processing for session: {}", sessionId, e);
			sseService.sendEvent(userId, "error", "AI 응답 생성 중 오류가 발생했습니다.");
			throw new RuntimeException("Error during Gemini API call or stream processing", e);
		}

		log.info("Gemini stream completed for session: {}", sessionId);
		return fullResponse.toString();
	}

	private String parseTextFromGeminiChunk(GeminiStreamChunk chunk) {
		if (chunk != null && chunk.candidates() != null && !chunk.candidates().isEmpty()) {
			GeminiResponse.Candidate candidate = chunk.candidates().get(0);
			if (candidate != null && candidate.content() != null && candidate.content().parts() != null
				&& !candidate.content().parts().isEmpty()) {
				return candidate.content().parts().get(0).text();
			}
		}
		return "";
	}

	private String createPromptWithHistory(List<ConversationMessage> history) {
		return history.stream()
			.map(msg -> msg.getSender().name() + ": " + msg.getMessage())
			.collect(Collectors.joining("\n"));
	}
}
