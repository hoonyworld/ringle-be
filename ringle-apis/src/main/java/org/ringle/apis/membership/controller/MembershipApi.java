package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Membership", description = "멤버십 관련 API")
@SecurityRequirement(name = "Bearer Authentication")
public interface MembershipApi {

	@Operation(
		summary = "내 멤버십 정보 조회",
		description = "로그인한 사용자의 멤버십 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "멤버십 정보 조회 성공"),
		@ApiResponse(responseCode = "401", description = "인증 실패"),
		@ApiResponse(responseCode = "404", description = "멤버십 정보 없음")
	})
	@GetMapping("/me")
	ResponseEntity<UserMembershipInfoResponse> getUserMembership(@AuthenticationPrincipal Long userId);
}
