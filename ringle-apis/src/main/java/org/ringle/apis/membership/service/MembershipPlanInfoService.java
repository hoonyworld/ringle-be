package org.ringle.apis.membership.service;

import java.util.ArrayList;
import java.util.List;

import org.ringle.apis.auth.exception.UserErrorCode;
import org.ringle.apis.auth.exception.UserNotFoundException;
import org.ringle.apis.membership.exception.MembershipErrorCode;
import org.ringle.apis.membership.exception.MembershipPlanNotFoundException;
import org.ringle.domain.membership.CompanyMembershipPlanRepository;
import org.ringle.domain.membership.CustomerType;
import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.domain.membership.vo.MembershipPlansInfo.MembershipPlanInfo;
import org.ringle.domain.user.User;
import org.ringle.domain.user.UserRepository;
import org.ringle.globalutils.auth.Role;
import org.ringle.globalutils.util.ListSortUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipPlanInfoService {
	private final MembershipPlanRepository membershipPlanRepository;
	private final CompanyMembershipPlanRepository companyMembershipPlanRepository;
	private final UserRepository userRepository;

	public MembershipPlansInfo getMembershipPlans(Long userId, Role role) {
		List<MembershipPlan> plans = switch (role) {
			case ADMIN -> membershipPlanRepository.findAll();
			case USER -> membershipPlanRepository.findByCustomerType(CustomerType.B2C);
			case COMPANY -> {
				User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
				List<MembershipPlan> b2cPlans = membershipPlanRepository.findByCustomerType(CustomerType.B2C);
				List<Long> companyPlanIds = companyMembershipPlanRepository.findPlanIdsByCompanyId(user.getCompanyId());
				List<MembershipPlan> companyPlans = membershipPlanRepository.findAllById(companyPlanIds);

				List<MembershipPlan> combined = new ArrayList<>();
				combined.addAll(b2cPlans);
				combined.addAll(companyPlans);
				yield combined;
			}
		};

		List<MembershipPlan> sortedPlans = ListSortUtils.sortByIntKey(plans, MembershipPlan::getPriority);
		return MembershipPlansInfo.newInstance(sortedPlans);
	}

	public MembershipPlanInfo getMembershipPlan(Long planId) {
		MembershipPlan plan = membershipPlanRepository.findById(planId)
			.orElseThrow(() -> new MembershipPlanNotFoundException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

		return MembershipPlanInfo.from(plan);
	}
}
