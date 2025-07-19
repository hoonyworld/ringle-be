package org.ringle.domain.conversation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, Long> {
	List<ConversationMessage> findAllBySessionIdOrderByCreatedAtAsc(UUID sessionId);
}
