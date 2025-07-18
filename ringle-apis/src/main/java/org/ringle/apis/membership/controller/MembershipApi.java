package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.request.PlanPurchaseRequest;
import org.ringle.apis.membership.dto.response.UserMembershipInfoAllResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Membership", description = "멤버십 관련 API")
public interface MembershipApi {

	@Operation(
		summary = "내 멤버십 정보 조회",
		description = "로그인한 사용자의 멤버십 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "멤버십 정보 조회 성공",
			content = @Content(schema = @Schema(implementation = UserMembershipInfoAllResponse.class))),
		@ApiResponse(responseCode = "401", description = "인증 실패"),
		@ApiResponse(responseCode = "404", description = "멤버십 정보 없음")
	})
	@GetMapping("/me")
	ResponseEntity<UserMembershipInfoAllResponse> getUserMemberships(
		@Parameter(hidden = true, description = "인증된 사용자 ID")
		@AuthenticationPrincipal Long userId
	);

	@Operation(
		summary = "멤버십 구매",
		description = "사용자가 특정 멤버십 플랜을 구매합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "멤버십 구매 성공",
			content = @Content(schema = @Schema(implementation = UserMembershipInfoResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
		@ApiResponse(responseCode = "401", description = "인증 실패", content = @Content),
		@ApiResponse(responseCode = "404", description = "멤버십 플랜 없음", content = @Content)
	})
	@PostMapping("/purchase")
	ResponseEntity<UserMembershipInfoResponse> purchaseMembership(
		@Parameter(hidden = true, description = "인증된 사용자 ID")
		@AuthenticationPrincipal Long userId,
		@Parameter(description = "구매 요청 정보")
		@Valid @RequestBody PlanPurchaseRequest request
	);
}
