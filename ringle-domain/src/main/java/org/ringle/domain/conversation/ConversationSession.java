package org.ringle.domain.conversation;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Integer topicId;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Builder(access = AccessLevel.PRIVATE)
	private ConversationSession(Long userId, Integer topicId, LocalDateTime startTime) {
		this.userId = userId;
		this.topicId = topicId;
		this.startTime = startTime;
	}

	public static ConversationSession create(Long userId, Integer topicId, LocalDateTime startTime) {
		return ConversationSession.builder()
			.userId(userId)
			.topicId(topicId)
			.startTime(startTime)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ConversationSession that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
