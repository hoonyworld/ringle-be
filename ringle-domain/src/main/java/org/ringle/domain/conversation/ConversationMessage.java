package org.ringle.domain.conversation;

import java.util.Objects;
import java.util.UUID;

import org.ringle.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationMessage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private UUID sessionId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private SenderType sender;

	@Lob
	@Column(nullable = false)
	private String message;

	@Column
	private String audioUrl;

	@Builder(access = AccessLevel.PRIVATE)
	private ConversationMessage(UUID sessionId, SenderType sender, String message, String audioUrl) {
		this.sessionId = sessionId;
		this.sender = sender;
		this.message = message;
		this.audioUrl = audioUrl;
	}

	public static ConversationMessage create(UUID sessionId, SenderType sender, String message, String audioUrl) {
		return ConversationMessage.builder()
			.sessionId(sessionId)
			.sender(sender)
			.message(message)
			.audioUrl(audioUrl)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ConversationMessage that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
