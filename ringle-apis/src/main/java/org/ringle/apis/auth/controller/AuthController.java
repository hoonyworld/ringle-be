package org.ringle.apis.auth.controller;

import org.ringle.apis.auth.dto.request.SocialLoginRequest;
import org.ringle.apis.auth.dto.response.AuthResponse;
import org.ringle.apis.auth.dto.response.UserProfileResponse;
import org.ringle.apis.auth.usecase.AuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApi {

	private final AuthUseCase authUseCase;

	@PostMapping("/login")
	@Override
	public ResponseEntity<AuthResponse> signIn(
		@Valid @RequestBody SocialLoginRequest request
	) {
		AuthResponse response = authUseCase.signIn(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/me")
	@Override
	public ResponseEntity<UserProfileResponse> getUserProfile(
		@AuthenticationPrincipal Long userId
	) {
		var userProfile = authUseCase.getUserProfile(userId);
		return ResponseEntity.ok(userProfile);
	}
}
