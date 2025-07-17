package org.ringle.domain.conversation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String title;

	@Lob
	@Column(nullable = false)
	private String initialPrompt;

	@Builder(access = AccessLevel.PRIVATE)
	private ConversationTopic(String title, String initialPrompt) {
		this.title = title;
		this.initialPrompt = initialPrompt;
	}

	public static ConversationTopic create(String title, String initialPrompt) {
		return ConversationTopic.builder()
			.title(title)
			.initialPrompt(initialPrompt)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ConversationTopic that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
