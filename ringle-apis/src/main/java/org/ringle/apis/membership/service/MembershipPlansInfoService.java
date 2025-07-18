package org.ringle.apis.membership.service;

import java.util.List;

import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.globalutils.util.ListSortUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipPlansInfoService {
	private final MembershipPlanRepository membershipPlanRepository;

	public MembershipPlansInfo getMembershipPlans() {
		List<MembershipPlan> plans = membershipPlanRepository.findAll();
		List<MembershipPlan> sortedPlans = ListSortUtils.sortByIntKey(plans, MembershipPlan::getPriority);

		return MembershipPlansInfo.newInstance(sortedPlans);
	}
}
