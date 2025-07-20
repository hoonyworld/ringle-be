package org.ringle.apis.conversation.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ringle.apis.conversation.dto.request.StartConversationRequest;
import org.ringle.apis.conversation.dto.response.ConversationHistoryResponse;
import org.ringle.apis.conversation.dto.response.ConversationTopicsResponse;
import org.ringle.apis.conversation.dto.response.StartConversationResponse;
import org.ringle.apis.conversation.service.ConversationService;
import org.ringle.apis.conversation.service.ConversationSessionService;
import org.ringle.apis.conversation.service.ConversationTopicService;
import org.ringle.apis.membership.service.UserMembershipInfoService;
import org.ringle.domain.conversation.ConversationMessage;
import org.ringle.domain.conversation.ConversationSession;
import org.ringle.domain.conversation.vo.ConversationSessionInfo;
import org.ringle.domain.conversation.vo.ConversationTopicInfo;
import org.ringle.globalutils.annotation.UseCase;
import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversationUseCase {

	private final UserMembershipInfoService userMembershipInfoService;
	private final ConversationSessionService conversationSessionService;
	private final ConversationTopicService conversationTopicService;
	private final ConversationService conversationService;

	public ConversationTopicsResponse getConversationTopics() {
		List<ConversationTopicInfo> allTopics = conversationTopicService.findAllTopics();
		return ConversationTopicsResponse.from(allTopics);
	}

	public ConversationHistoryResponse getConversationHistory(Long userId, UUID sessionId) {
		conversationSessionService.validateSessionOwner(userId, sessionId);

		List<ConversationMessage> history = conversationService.findConversationHistory(sessionId);

		List<ConversationHistoryResponse.ConversationHistoryMessageResponse> messages = history.stream()
			.map(ConversationHistoryResponse.ConversationHistoryMessageResponse::from)
			.toList();

		return ConversationHistoryResponse.from(messages);
	}

	@Transactional
	public StartConversationResponse startConversation(Long userId, StartConversationRequest request) {
		if (!request.force()) {
			Optional<ConversationSession> existingSession = conversationSessionService.findActiveSession(userId,
				request.topicId());

			if (existingSession.isPresent()) {
				return StartConversationResponse.of(existingSession.get().getId(), SessionStatus.EXISTING);
			}
		} else {
			conversationSessionService.findActiveSession(userId, request.topicId())
				.ifPresent(session -> {
					session.close();
					conversationSessionService.saveSession(session);
				});
		}

		userMembershipInfoService.decrementConversationCredit(userId);

		ConversationSessionInfo conversationSessionInfo = conversationSessionService.createSession(userId,
			request.topicId());

		return StartConversationResponse.of(conversationSessionInfo.sessionId(), conversationSessionInfo.status());
	}
}
