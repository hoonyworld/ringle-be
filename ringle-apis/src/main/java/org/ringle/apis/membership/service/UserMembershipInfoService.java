package org.ringle.apis.membership.service;

import org.ringle.apis.auth.exception.UserErrorCode;
import org.ringle.apis.auth.exception.UserNotFoundException;
import org.ringle.apis.membership.exception.MembershipErrorCode;
import org.ringle.apis.membership.exception.MembershipPlanNotFoundException;
import org.ringle.apis.membership.exception.UserMembershipNotFoundException;
import org.ringle.domain.membership.MembershipPlan;
import org.ringle.domain.membership.MembershipPlanRepository;
import org.ringle.domain.membership.UserMembership;
import org.ringle.domain.membership.UserMembershipRepository;
import org.ringle.domain.membership.vo.UserMembershipInfo;
import org.ringle.domain.user.User;
import org.ringle.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMembershipInfoService {
	private final UserRepository userRepository;
	private final UserMembershipRepository userMembershipRepository;
	private final MembershipPlanRepository membershipPlanRepository;

	public UserMembershipInfo getUserMemberShip(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

		UserMembership userMembership = userMembershipRepository.findByUserId(user.getId())
			.orElseThrow(() -> new UserMembershipNotFoundException(MembershipErrorCode.USER_MEMBERSHIP_NOT_FOUND));

		MembershipPlan membershipPlan = membershipPlanRepository.findById(userMembership.getPlanId())
			.orElseThrow(() -> new MembershipPlanNotFoundException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

		return UserMembershipInfo.newInstance(userMembership, membershipPlan);
	}
}
