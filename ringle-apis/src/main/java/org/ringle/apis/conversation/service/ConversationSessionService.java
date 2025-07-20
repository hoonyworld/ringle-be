package org.ringle.apis.conversation.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.ringle.domain.conversation.ConversationSession;
import org.ringle.domain.conversation.ConversationSessionRepository;
import org.ringle.domain.conversation.ConversationTopicRepository;
import org.ringle.domain.conversation.vo.ConversationSessionInfo;
import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
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

	public void validateSessionOwner(Long userId, UUID sessionId) {
		ConversationSession session = conversationSessionRepository.findById(sessionId)
			.orElseThrow(() -> new EntityNotFoundException("해당 대화 세션을 찾을 수 없습니다."));

		if (!session.getUserId().equals(userId)) {
			throw new AccessDeniedException("해당 대화 내역에 접근할 권한이 없습니다.");
		}
	}
}
