package org.ringle.apis.membership.usecase;

import org.ringle.apis.membership.dto.response.MembershipPlansInfoResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.service.MembershipPlansInfoService;
import org.ringle.apis.membership.service.UserMembershipInfoService;
import org.ringle.domain.membership.vo.MembershipPlansInfo;
import org.ringle.domain.membership.vo.UserMembershipInfo;
import org.ringle.globalutils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipUseCase {
	private final UserMembershipInfoService userMembershipInfoService;
	private final MembershipPlansInfoService membershipPlansInfoService;

	public UserMembershipInfoResponse getUserMembershipInfo(Long userId) {
		UserMembershipInfo userMembershipInfo = userMembershipInfoService.getUserMemberShip(userId);
		return UserMembershipInfoResponse.from(userMembershipInfo);
	}

	public MembershipPlansInfoResponse getMembershipPlansInfo() {
		MembershipPlansInfo membershipPlansInfo = membershipPlansInfoService.getMembershipPlans();
		return MembershipPlansInfoResponse.from(membershipPlansInfo);
	}
}
