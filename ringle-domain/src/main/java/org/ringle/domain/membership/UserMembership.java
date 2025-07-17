package org.ringle.domain.membership;

import java.time.LocalDate;
import java.util.Objects;

import org.ringle.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMembership extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Integer planId;

	@Column(nullable = false)
	private int remainingConversations;

	@Column(nullable = false)
	private int remainingAnalysis;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate expiryDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private MembershipStatus status;

	@Builder(access = AccessLevel.PRIVATE)
	private UserMembership(Long userId,
		Integer planId,
		int remainingConversations,
		int remainingAnalysis,
		LocalDate startDate,
		LocalDate expiryDate,
		MembershipStatus status
	) {
		this.userId = userId;
		this.planId = planId;
		this.remainingConversations = remainingConversations;
		this.remainingAnalysis = remainingAnalysis;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.status = status;
	}

	public static UserMembership create(Long userId,
		Integer planId,
		int remainingConversations,
		int remainingAnalysis,
		LocalDate startDate,
		LocalDate expiryDate,
		MembershipStatus status
	) {
		return UserMembership.builder()
			.userId(userId)
			.planId(planId)
			.remainingConversations(remainingConversations)
			.remainingAnalysis(remainingAnalysis)
			.startDate(startDate)
			.expiryDate(expiryDate)
			.status(status)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserMembership that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
