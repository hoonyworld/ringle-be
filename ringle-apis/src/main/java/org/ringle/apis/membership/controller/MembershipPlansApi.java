package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.response.MembershipPlansInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Membership", description = "멤버십 관련 API")
@RequestMapping("/api/v1/memberships-plans")
public interface MembershipPlansApi {

	@Operation(
		summary = "모든 멤버십 플랜 조회",
		description = "사용 가능한 모든 멤버십 플랜 목록을 조회합니다."
	)
	@GetMapping
	ResponseEntity<MembershipPlansInfoResponse> getMembershipPlans(
		@Parameter(hidden = true, description = "인증된 사용자 ID")
		@AuthenticationPrincipal Long userId
	);
}
