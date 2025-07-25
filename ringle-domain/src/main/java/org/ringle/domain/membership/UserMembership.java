package org.ringle.domain.membership;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
	private Long planId;

	@Column(nullable = false)
	private int remainingConversations;

	@Column(nullable = false)
	private int remainingRolePlaying;

	@Column(nullable = false)
	private int remainingDiscussion;

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
		Long planId,
		int remainingConversations,
		int remainingRolePlaying,
		int remainingDiscussion,
		int remainingAnalysis,
		LocalDate startDate,
		LocalDate expiryDate,
		MembershipStatus status
	) {
		this.userId = userId;
		this.planId = planId;
		this.remainingConversations = remainingConversations;
		this.remainingRolePlaying = remainingRolePlaying;
		this.remainingDiscussion = remainingDiscussion;
		this.remainingAnalysis = remainingAnalysis;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.status = status;
	}

	public static UserMembership create(
		Long userId,
		Long planId,
		int remainingConversations,
		int remainingRolePlaying,
		int remainingDiscussion,
		int remainingAnalysis,
		int durationDays
	) {
		LocalDate startDate = LocalDate.now();
		LocalDate expiryDate = startDate.plusDays(durationDays);

		return UserMembership.builder()
			.userId(userId)
			.planId(planId)
			.remainingConversations(remainingConversations)
			.remainingRolePlaying(remainingRolePlaying)
			.remainingDiscussion(remainingDiscussion)
			.remainingAnalysis(remainingAnalysis)
			.startDate(startDate)
			.expiryDate(expiryDate)
			.status(MembershipStatus.INACTIVE)
			.build();
	}

	public void activate() {
		if (this.status == MembershipStatus.ACTIVE) {
			throw new IllegalStateException("이미 활성화된 멤버십입니다.");
		}
		long durationInDays = ChronoUnit.DAYS.between(this.startDate, this.expiryDate);
		this.status = MembershipStatus.ACTIVE;
		this.startDate = LocalDate.now();
		this.expiryDate = this.startDate.plusDays(durationInDays);
	}

	public void decrementConversations() {
		if (this.remainingConversations <= 0) {
			throw new IllegalStateException("남은 대화 횟수가 없습니다.");
		}
		this.remainingConversations--;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserMembership that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
