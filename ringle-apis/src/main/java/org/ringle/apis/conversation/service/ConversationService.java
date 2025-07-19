package org.ringle.apis.conversation.service;

import java.util.List;
import java.util.UUID;

import org.ringle.domain.conversation.ConversationMessage;
import org.ringle.domain.conversation.ConversationMessageRepository;
import org.ringle.domain.conversation.SenderType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversationService {

	private final ConversationMessageRepository conversationMessageRepository;

	@Transactional
	public void saveUserMessage(UUID sessionId, String message) {
		ConversationMessage userMessage = ConversationMessage.create(sessionId, SenderType.USER, message);
		conversationMessageRepository.save(userMessage);
	}

	@Transactional
	public void saveAiMessage(UUID sessionId, String message) {
		ConversationMessage aiMessage = ConversationMessage.create(sessionId, SenderType.AI, message);
		conversationMessageRepository.save(aiMessage);
	}

	public List<ConversationMessage> findConversationHistory(UUID sessionId) {
		return conversationMessageRepository.findAllBySessionIdOrderByCreatedAtAsc(sessionId);
	}
}
