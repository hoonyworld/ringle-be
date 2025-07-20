package org.ringle.apis.conversation.usecase;

import java.util.UUID;

import org.ringle.apis.conversation.dto.response.AiCompleteEvent;
import org.ringle.apis.conversation.service.ConversationService;
import org.ringle.apis.conversation.service.LlmService;
import org.ringle.apis.conversation.service.SseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiGenerationUseCase {
	private final LlmService llmService;
	private final ConversationService conversationService;
	private final SseService sseService;

	@Async
	@Transactional
	public void generateAiResponse(Long userId, UUID sessionId, String userMessage) {
		String fullAiResponseText = llmService.streamLlMResponse(userId, sessionId, userMessage);

		conversationService.saveAiMessage(sessionId, fullAiResponseText);

		AiCompleteEvent finalEvent = AiCompleteEvent.of(fullAiResponseText);
		sseService.sendEvent(userId, "ai_complete", finalEvent);

		sseService.completeEmitter(userId);
	}
}
