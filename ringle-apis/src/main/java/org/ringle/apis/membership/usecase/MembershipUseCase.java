package org.ringle.apis.membership.usecase;

import java.util.List;

import org.ringle.apis.membership.dto.request.PlanPurchaseRequest;
import org.ringle.apis.membership.dto.response.MembershipPlansInfoResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoAllResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.service.MembershipPlanInfoService;
import org.ringle.apis.membership.service.UserMembershipInfoService;
import org.ringle.apis.payment.service.PaymentService;
import org.ringle.apis.user.service.UserService;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.domain.membership.vo.MembershipPlansInfo.MembershipPlanInfo;
import org.ringle.domain.membership.vo.UserMembershipInfo;
import org.ringle.domain.user.vo.UserIdentity;
import org.ringle.globalutils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipUseCase {
	private final UserMembershipInfoService userMembershipInfoService;
	private final MembershipPlanInfoService membershipPlanInfoService;
	private final PaymentService paymentService;
	private final UserService userService;

	public UserMembershipInfoAllResponse getUserMembershipInfo(Long userId) {
		List<UserMembershipInfo> userMembershipInfos = userMembershipInfoService.getAllUserMemberships(userId);

		List<UserMembershipInfoResponse> responses = userMembershipInfos.stream()
			.map(UserMembershipInfoResponse::from)
			.toList();

		return UserMembershipInfoAllResponse.from(responses);
	}

	public MembershipPlansInfoResponse getMembershipPlansInfo(Long userId) {
		UserIdentity userIdentity = userService.findUserById(userId);
		MembershipPlansInfo membershipPlansInfo = membershipPlanInfoService.getMembershipPlans(userId, userIdentity.role());
		return MembershipPlansInfoResponse.from(membershipPlansInfo);
	}

	@Transactional
	public UserMembershipInfoResponse purchaseMembership(Long userId, PlanPurchaseRequest request) {
		MembershipPlanInfo planInfo = membershipPlanInfoService.getMembershipPlan(request.planId());
		UserMembershipInfo pendingMembership = userMembershipInfoService.createPendingMembership(userId, planInfo);

		paymentService.processPayment(userId, pendingMembership.id(), planInfo.price(), planInfo.id());

		return UserMembershipInfoResponse.from(pendingMembership);
	}
}
