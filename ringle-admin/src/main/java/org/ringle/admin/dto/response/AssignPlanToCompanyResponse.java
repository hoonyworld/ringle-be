package org.ringle.admin.dto.response;

import java.time.LocalDateTime;

import org.ringle.domain.company.Company;
import org.ringle.domain.company.CompanyName;
import org.ringle.domain.membership.CompanyMembershipPlan;
import org.ringle.domain.membership.MembershipPlan;

public record AssignPlanToCompanyResponse(
	Long assignmentId,
	Long companyId,
	CompanyName companyName,
	Long planId,
	String planName,
	LocalDateTime createdAt
) {
	public static AssignPlanToCompanyResponse of(
		CompanyMembershipPlan assignment,
		Company company,
		MembershipPlan plan
	) {
		return new AssignPlanToCompanyResponse(
			assignment.getId(),
			company.getId(),
			company.getCompanyName(),
			plan.getId(),
			plan.getName(),
			assignment.getCreatedAt()
		);
	}
}
