package org.ringle.apis.conversation.usecase;

import java.util.UUID;

import org.ringle.apis.conversation.dto.response.SttResponse;
import org.ringle.apis.conversation.service.ConversationService;
import org.ringle.apis.conversation.service.SttService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SttUseCase {
	private final SttService sttService;
	private final ConversationService conversationService;
	private final AiGenerationUseCase aiGenerationUseCase;

	@Transactional
	public SttResponse processSttAndTriggerAi(Long userId, UUID sessionId, MultipartFile audioFile) {
		String transcript = sttService.convertSpeechToText(audioFile);
		conversationService.saveUserMessage(sessionId, transcript);
		aiGenerationUseCase.generateAiResponse(userId, sessionId, transcript);
		return SttResponse.from(transcript);
	}
}
