package org.ringle.admin.service;

import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMembershipPlanInfoService {

	private final MembershipPlanRepository membershipPlanRepository;

	public MembershipPlan findById(Long planId) {
		return membershipPlanRepository.findById(planId)
			.orElseThrow(() -> new EntityNotFoundException("MembershipPlan not found: " + planId));
	}
}
