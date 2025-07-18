package org.ringle.apis.membership.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "유저 멤버십 전체 정보 응답")
public record UserMembershipInfoAllResponse(

	@Schema(description = "유저가 보유한 모든 멤버십 정보")
	List<UserMembershipInfoResponse> memberships

) {
	public static UserMembershipInfoAllResponse from(List<UserMembershipInfoResponse> responses) {
		return new UserMembershipInfoAllResponse(responses);
	}
}
