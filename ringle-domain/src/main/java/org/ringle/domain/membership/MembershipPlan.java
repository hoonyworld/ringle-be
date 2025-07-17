package org.ringle.domain.membership;

import java.math.BigDecimal;
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
public class MembershipPlan extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CustomerType customerType;

	@Column(nullable = false)
	private int conversationCount;

	@Column(nullable = false)
	private int rolePlayingCount;

	@Column(nullable = false)
	private int discussionCount;

	@Column(nullable = false)
	private int analysisCount;

	@Column(nullable = false)
	private int durationDays;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Builder(access = AccessLevel.PRIVATE)
	private MembershipPlan(
		String name,
		CustomerType customerType,
		int conversationCount,
		int rolePlayingCount,
		int discussionCount,
		int analysisCount,
		int durationDays,
		BigDecimal price
	) {
		this.name = name;
		this.customerType = customerType;
		this.conversationCount = conversationCount;
		this.rolePlayingCount = rolePlayingCount;
		this.discussionCount = discussionCount;
		this.analysisCount = analysisCount;
		this.durationDays = durationDays;
		this.price = price;
	}

	public static MembershipPlan create(
		String name,
		CustomerType customerType,
		int conversationCount,
		int rolePlayingCount,
		int discussionCount,
		int analysisCount,
		int durationDays,
		BigDecimal price
	) {
		return MembershipPlan.builder()
			.name(name)
			.customerType(customerType)
			.conversationCount(conversationCount)
			.rolePlayingCount(rolePlayingCount)
			.discussionCount(discussionCount)
			.analysisCount(analysisCount)
			.durationDays(durationDays)
			.price(price)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MembershipPlan that))
			return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
