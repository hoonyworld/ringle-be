package org.ringle.apis.membership.controller;

import org.ringle.apis.membership.dto.request.PlanPurchaseRequest;
import org.ringle.apis.membership.dto.response.UserMembershipInfoAllResponse;
import org.ringle.apis.membership.dto.response.UserMembershipInfoResponse;
import org.ringle.apis.membership.usecase.MembershipUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership")
public class MembershipController implements MembershipApi {
	private final MembershipUseCase membershipUseCase;

	@GetMapping("/me")
	@Override
	public ResponseEntity<UserMembershipInfoAllResponse> getUserMemberships(
		@AuthenticationPrincipal Long userId
	) {
		UserMembershipInfoAllResponse response = membershipUseCase.getUserMembershipInfo(userId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/purchase")
	@Override
	public ResponseEntity<UserMembershipInfoResponse> purchaseMembership(
		@AuthenticationPrincipal Long userId,
		@Valid @RequestBody PlanPurchaseRequest request
	) {
		UserMembershipInfoResponse response = membershipUseCase.purchaseMembership(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{membershipId}/activate")
	public ResponseEntity<Void> activateMembership(
		@AuthenticationPrincipal Long userId,
		@PathVariable Long membershipId
	) {
		membershipUseCase.activateMembership(userId, membershipId);
		return ResponseEntity.noContent().build();
	}
}
