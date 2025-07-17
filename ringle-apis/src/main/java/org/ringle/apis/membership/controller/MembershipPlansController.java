package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.response.MembershipPlansInfoResponse;
import org.ringle.apis.membership.usecase.MembershipUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership-plans")
public class MembershipPlansController implements MembershipPlansApi {
	private final MembershipUseCase membershipUseCase;

	@GetMapping
	@Override
	public ResponseEntity<MembershipPlansInfoResponse> getMembershipPlans(
		@AuthenticationPrincipal Long userId
	) {
		MembershipPlansInfoResponse response = membershipUseCase.getMembershipPlansInfo();
		return ResponseEntity.ok(response);
	}
}
