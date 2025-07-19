package org.ringle.domain.conversation;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.ringle.globalutils.constants.SessionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationSession {

	@Id
	@UuidGenerator
	private UUID id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long topicId;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SessionStatus status;

	@Builder(access = AccessLevel.PRIVATE)
	private ConversationSession(Long userId, Long topicId, LocalDateTime startTime, SessionStatus status) {
		this.userId = userId;
		this.topicId = topicId;
		this.startTime = startTime;
		this.status = status;
	}

	public static ConversationSession create(Long userId, Long topicId, LocalDateTime startTime) {
		return ConversationSession.builder()
			.userId(userId)
			.topicId(topicId)
			.startTime(startTime)
			.status(SessionStatus.ACTIVE)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ConversationSession that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
