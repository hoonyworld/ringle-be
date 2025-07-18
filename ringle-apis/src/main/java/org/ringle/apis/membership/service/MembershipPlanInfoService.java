package org.ringle.apis.membership.service;

import java.util.List;

import org.ringle.apis.membership.exception.MembershipErrorCode;
import org.ringle.apis.membership.exception.MembershipPlanNotFoundException;
import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.domain.membership.vo.MembershipPlansInfo.MembershipPlanInfo;
import org.ringle.globalutils.util.ListSortUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipPlanInfoService {
	private final MembershipPlanRepository membershipPlanRepository;

	public MembershipPlansInfo getMembershipPlans() {
		List<MembershipPlan> plans = membershipPlanRepository.findAll();
		List<MembershipPlan> sortedPlans = ListSortUtils.sortByIntKey(plans, MembershipPlan::getPriority);

		return MembershipPlansInfo.newInstance(sortedPlans);
	}

	public MembershipPlanInfo getMembershipPlan(Long planId) {
		MembershipPlan plan = membershipPlanRepository.findById(planId)
			.orElseThrow(() -> new MembershipPlanNotFoundException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

		return MembershipPlanInfo.from(plan);
	}
}
