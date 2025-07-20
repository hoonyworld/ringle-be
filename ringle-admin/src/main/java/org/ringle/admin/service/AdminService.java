package org.ringle.admin.service;

import org.ringle.domain.company.CompanyRepository;
import org.ringle.domain.membership.CompanyMembershipPlan;
import org.ringle.domain.membership.CompanyMembershipPlanRepository;
import org.ringle.domain.membership.CustomerType;
import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final MembershipPlanRepository membershipPlanRepository;
	private final CompanyRepository companyRepository;
	private final CompanyMembershipPlanRepository companyMembershipPlanRepository;

	public CompanyMembershipPlan assignPlanToCompany(Long companyId, Long planId) {
		MembershipPlan plan = membershipPlanRepository.findById(planId)
			.orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found with id: " + planId));

		if (plan.getCustomerType() != CustomerType.B2B) {
			throw new IllegalArgumentException("Only B2B plans can be assigned to companies.");
		}

		if (!companyRepository.existsById(companyId)) {
			throw new EntityNotFoundException("Company not found with id: " + companyId);
		}

		companyMembershipPlanRepository.findByCompanyIdAndMembershipPlanId(companyId, planId)
			.ifPresent(cmp -> {
				throw new IllegalStateException("This plan is already assigned to the company.");
			});

		CompanyMembershipPlan companyPlan = CompanyMembershipPlan.create(companyId, planId);
		return companyMembershipPlanRepository.save(companyPlan);
	}

	public void removePlanFromCompany(Long companyId, Long planId) {
		CompanyMembershipPlan companyPlan = companyMembershipPlanRepository.findByCompanyIdAndMembershipPlanId(
				companyId, planId)
			.orElseThrow(() -> new EntityNotFoundException("The specified plan is not assigned to this company."));

		companyMembershipPlanRepository.delete(companyPlan);
	}
}
