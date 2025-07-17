package org.ringle.domain.analysis;

import java.util.Objects;
import java.util.UUID;

import org.ringle.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class AnalysisResult extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private UUID sessionId;

	@Column(nullable = false)
	private Long userId;

	@Column
	private String level;

	@Lob
	@Column(nullable = false)
	private String feedback;

	@Builder(access = AccessLevel.PRIVATE)
	private AnalysisResult(UUID sessionId, Long userId, String level, String feedback) {
		this.sessionId = sessionId;
		this.userId = userId;
		this.level = level;
		this.feedback = feedback;
	}

	public static AnalysisResult create(UUID sessionId, Long userId, String level, String feedback) {
		return AnalysisResult.builder()
			.sessionId(sessionId)
			.userId(userId)
			.level(level)
			.feedback(feedback)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AnalysisResult that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
