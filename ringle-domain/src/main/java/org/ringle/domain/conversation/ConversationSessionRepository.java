package org.ringle.domain.conversation;

import java.util.Optional;
import java.util.UUID;

import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationSessionRepository extends JpaRepository<ConversationSession, UUID> {
	Optional<ConversationSession> findByUserIdAndTopicIdAndStatus(Long userId, Long topicId, SessionStatus status);
}
