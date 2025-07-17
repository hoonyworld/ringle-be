package org.ringle.apis.auth.controller;

import org.ringle.apis.auth.dto.request.SocialLoginRequest;
import org.ringle.apis.auth.dto.response.AuthResponse;
import org.ringle.apis.auth.dto.response.UserProfileResponse;
import org.ringle.globalutils.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "인증 관련 API")
public interface AuthApi {

	@Operation(
		summary = "소셜 로그인으로 로그인 또는 회원가입",
		description = "카카오 또는 애플 소셜 로그인 정보를 이용해 사용자를 로그인합니다. 존재하지 않는 사용자인 경우 자동으로 회원가입됩니다."
	)
	@ApiResponse(
		responseCode = "200",
		description = "성공적으로 로그인 또는 회원가입됨",
		content = @Content(schema = @Schema(implementation = AuthResponse.class))
	)
	@ApiResponse(
		responseCode = "400",
		description = "잘못된 요청 또는 자격 증명 오류",
		content = @Content(schema = @Schema(implementation = ErrorResponse.class))
	)
	@ApiResponse(
		responseCode = "409",
		description = "이미 다른 계정과 연동된 이메일",
		content = @Content(schema = @Schema(implementation = ErrorResponse.class))
	)
	@PostMapping("/login")
	ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SocialLoginRequest request);

	@Operation(
		summary = "사용자 프로필 조회",
		description = "사용자 ID를 기반으로 프로필 정보를 조회합니다."
	)
	@ApiResponse(
		responseCode = "200",
		description = "프로필 정보 조회 성공",
		content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
	)
	@ApiResponse(
		responseCode = "404",
		description = "사용자를 찾을 수 없음",
		content = @Content(schema = @Schema(implementation = ErrorResponse.class))
	)
	@GetMapping("/me")
	ResponseEntity<UserProfileResponse> getUserProfile(@AuthenticationPrincipal Long userId);
}
