package org.ringle.domain.payment;

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
public class Payment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false, unique = true)
	private Long userMembershipId;

	@Column(precision = 10, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@Builder(access = AccessLevel.PRIVATE)
	private Payment(Long userId, Long userMembershipId, BigDecimal amount, PaymentStatus status) {
		this.userId = userId;
		this.userMembershipId = userMembershipId;
		this.amount = amount;
		this.status = status;
	}

	public static Payment create(Long userId, Long userMembershipId, BigDecimal amount, PaymentStatus status) {
		return Payment.builder()
			.userId(userId)
			.userMembershipId(userMembershipId)
			.amount(amount)
			.status(status)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Payment payment))
			return false;
		return id != null && id.equals(payment.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
