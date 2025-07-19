package org.ringle.apis.conversation.usecase;

import java.util.List;
import java.util.Optional;

import org.ringle.apis.conversation.dto.request.StartConversationRequest;
import org.ringle.apis.conversation.dto.response.ConversationTopicsResponse;
import org.ringle.apis.conversation.dto.response.StartConversationResponse;
import org.ringle.apis.conversation.service.ConversationSessionService;
import org.ringle.apis.conversation.service.ConversationTopicService;
import org.ringle.apis.membership.service.UserMembershipInfoService;
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

	public ConversationTopicsResponse getConversationTopics() {
		List<ConversationTopicInfo> allTopics = conversationTopicService.findAllTopics();
		return ConversationTopicsResponse.from(allTopics);
	}

	@Transactional
	public StartConversationResponse startConversation(Long userId, StartConversationRequest request) {
		if (!request.force()) {
			Optional<ConversationSession> existingSession = conversationSessionService.findActiveSession(userId,
				request.topicId());

			if (existingSession.isPresent()) {
				return StartConversationResponse.of(existingSession.get().getId(), SessionStatus.EXISTING);
			}
		}

		userMembershipInfoService.decrementConversationCredit(userId);

		ConversationSessionInfo conversationSessionInfo = conversationSessionService.createSession(userId,
			request.topicId());

		return StartConversationResponse.of(conversationSessionInfo.sessionId(), conversationSessionInfo.status());
	}
}
