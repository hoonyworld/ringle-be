package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.usecase.MembershipUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
public class MembershipController implements MembershipApi {
	private final MembershipUseCase membershipUseCase;

	@GetMapping("/me")
	@Override
	public ResponseEntity<UserMembershipInfoResponse> getUserMembership(
		@AuthenticationPrincipal Long userId
	) {
		UserMembershipInfoResponse response = membershipUseCase.getUserMembershipInfo(userId);
		return ResponseEntity.ok(response);
	}
}
