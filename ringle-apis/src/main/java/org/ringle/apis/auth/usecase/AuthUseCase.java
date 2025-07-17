package org.ringle.apis.auth.usecase;

import org.ringle.apis.auth.dto.request.SocialLoginRequest;
import org.ringle.apis.auth.dto.response.AuthResponse;
import org.ringle.apis.auth.dto.response.UserProfileResponse;
import org.ringle.apis.auth.service.UserAuthService;
import org.ringle.domain.user.vo.UserIdentity;
import org.ringle.gateway.jwt.JwtTokenService;
import org.ringle.globalutils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthUseCase {
	private final UserAuthService userAuthService;
	private final JwtTokenService jwtTokenService;

	@Transactional
	public AuthResponse signIn(SocialLoginRequest request) {
		UserIdentity identity = userAuthService.findUserByEmailAndValidatePassword(request.email(), request.password());
		String accessToken = jwtTokenService.generateAccessToken(identity.id(), identity.role());
		String refreshToken = jwtTokenService.generateRefreshToken(identity.id());

		return AuthResponse.of(accessToken, refreshToken, identity);
	}

	public UserProfileResponse getUserProfile(Long userId) {
		UserIdentity identity = userAuthService.findUserById(userId);
		return UserProfileResponse.from(identity);
	}
}
