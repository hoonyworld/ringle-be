package org.ringle.domain.conversation.vo;

import java.util.UUID;

import org.ringle.domain.conversation.ConversationSession;
import org.ringle.globalutils.constants.SessionStatus;

public record ConversationSessionInfo(
	UUID sessionId,
	SessionStatus status
) {
	public static ConversationSessionInfo newInstance(
		ConversationSession savedConversationSession
	) {
		return new ConversationSessionInfo(savedConversationSession.getId(), savedConversationSession.getStatus());
	}
}
