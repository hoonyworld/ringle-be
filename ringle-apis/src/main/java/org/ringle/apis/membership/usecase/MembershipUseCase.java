package org.ringle.apis.membership.usecase;

import java.util.List;

import org.ringle.apis.membership.dto.request.PlanPurchaseRequest;
import org.ringle.apis.membership.dto.response.MembershipPlansInfoResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoAllResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.service.MembershipPlanInfoService;
import org.ringle.apis.membership.service.UserMembershipInfoService;
import org.ringle.apis.payment.service.PaymentService;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.domain.membership.vo.MembershipPlansInfo.MembershipPlanInfo;
import org.ringle.domain.membership.vo.UserMembershipInfo;
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

	public UserMembershipInfoAllResponse getUserMembershipInfo(Long userId) {
		List<UserMembershipInfo> userMembershipInfos = userMembershipInfoService.getAllUserMemberships(userId);

		List<UserMembershipInfoResponse> responses = userMembershipInfos.stream()
			.map(UserMembershipInfoResponse::from)
			.toList();

		return UserMembershipInfoAllResponse.from(responses);
	}

	public MembershipPlansInfoResponse getMembershipPlansInfo() {
		MembershipPlansInfo membershipPlansInfo = membershipPlanInfoService.getMembershipPlans();
		return MembershipPlansInfoResponse.from(membershipPlansInfo);
	}

	@Transactional
	public UserMembershipInfoResponse purchaseMembership(Long userId, PlanPurchaseRequest request) {
		UserMembershipInfo userMembershipInfo = userMembershipInfoService.getUserMemberShip(userId);
		MembershipPlanInfo planInfo = membershipPlanInfoService.getMembershipPlan(request.planId());

		paymentService.processPayment(userId, userMembershipInfo.id(), planInfo.price(), planInfo.id());
		return UserMembershipInfoResponse.from(userMembershipInfo);
	}
}
