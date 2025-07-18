package org.ringle.domain.membership;

import java.util.Objects;

import org.ringle.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyMembershipPlan extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long companyId;

	@Column(nullable = false)
	private Long membershipPlanId;

	@Builder(access = AccessLevel.PRIVATE)
	private CompanyMembershipPlan(Long companyId, Long membershipPlanId) {
		this.companyId = companyId;
		this.membershipPlanId = membershipPlanId;
	}

	public static CompanyMembershipPlan create(Long companyId, Long membershipPlanId) {
		return CompanyMembershipPlan.builder()
			.companyId(companyId)
			.membershipPlanId(membershipPlanId)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CompanyMembershipPlan that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
