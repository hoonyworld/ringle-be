package org.ringle.apis.conversation.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.ringle.domain.conversation.ConversationSession;
import org.ringle.domain.conversation.ConversationSessionRepository;
import org.ringle.domain.conversation.ConversationTopicRepository;
import org.ringle.domain.conversation.vo.ConversationSessionInfo;
import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationSessionService {

	private final ConversationSessionRepository conversationSessionRepository;
	private final ConversationTopicRepository conversationTopicRepository;

	public ConversationSessionInfo createSession(Long userId, Long topicId) {
		conversationTopicRepository.findById(topicId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대화 주제입니다."));

		ConversationSession session = ConversationSession.create(
			userId,
			topicId,
			LocalDateTime.now()
		);
		ConversationSession savedConversationSession = conversationSessionRepository.save(session);
		return ConversationSessionInfo.newInstance(savedConversationSession);
	}

	public Optional<ConversationSession> findActiveSession(Long userId, Long topicId) {
		return conversationSessionRepository.findByUserIdAndTopicIdAndStatus(
			userId,
			topicId,
			SessionStatus.ACTIVE
		);
	}

	public void saveSession(ConversationSession session) {
		conversationSessionRepository.save(session);
	}
}
