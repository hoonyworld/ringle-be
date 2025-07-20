package org.ringle.admin.usecase;

import org.ringle.admin.dto.response.AssignPlanToCompanyResponse;
import org.ringle.admin.service.AdminService;
import org.ringle.admin.service.AdminCompanyInfoService;
import org.ringle.admin.service.AdminMembershipPlanInfoService;
import org.ringle.domain.company.Company;
import org.ringle.domain.membership.CompanyMembershipPlan;
import org.ringle.domain.membership.MembershipPlan;
import org.ringle.globalutils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AdminUseCase {

	private final AdminService adminService;
	private final AdminCompanyInfoService companyInfoService;
	private final AdminMembershipPlanInfoService membershipPlanInfoService;

	@Transactional
	public AssignPlanToCompanyResponse assignB2bPlan(Long companyId, Long planId) {
		CompanyMembershipPlan assignment = adminService.assignPlanToCompany(companyId, planId);
		Company company = companyInfoService.findById(assignment.getCompanyId());
		MembershipPlan plan = membershipPlanInfoService.findById(assignment.getMembershipPlanId());

		return AssignPlanToCompanyResponse.of(assignment, company, plan);
	}

	@Transactional
	public void removeB2bPlan(Long companyId, Long planId) {
		adminService.removePlanFromCompany(companyId, planId);
	}
}
