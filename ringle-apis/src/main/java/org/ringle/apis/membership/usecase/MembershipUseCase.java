package org.ringle.apis.membership.usecase;

import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.service.UserMembershipInfoService;
import org.ringle.domain.membership.vo.UserMembershipInfo;
import org.ringle.globalutils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipUseCase {
	private final UserMembershipInfoService userMembershipInfoService;

	public UserMembershipInfoResponse getUserMembershipInfo(Long userId) {
		UserMembershipInfo userMembershipInfo = userMembershipInfoService.getUserMemberShip(userId);
		return UserMembershipInfoResponse.from(userMembershipInfo);
	}
}
