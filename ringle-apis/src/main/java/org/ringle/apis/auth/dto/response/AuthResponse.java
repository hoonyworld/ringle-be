package org.ringle.apis.auth.dto.response;

import org.ringle.domain.user.vo.UserIdentity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
	name = "AuthResponse",
	description = "액세스 토큰과 리프레시 토큰 및 사용자 정보가 포함된 응답"
)
public record AuthResponse(
	@Schema(description = "사용자 인가를 위한 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	String accessToken,
	@Schema(description = "새로운 액세스 토큰을 발급받기 위한 리프레시 토큰", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
	String refreshToken,
	@Schema(description = "사용자 정보")
	UserData userData
) {

	public static AuthResponse of(String accessToken, String refreshToken, UserIdentity user) {
		return new AuthResponse(
			accessToken,
			refreshToken,
			UserData.from(user)
		);
	}

	@Schema(
		name = "UserData",
		description = "사용자 정보"
	)
	public static record UserData(
		@Schema(description = "사용자 고유 ID", example = "12345")
		Long id,
		@Schema(description = "사용자 이메일", example = "user@example.com")
		String email,
		@Schema(description = "사용자 닉네임", example = "닉네임")
		String name,
		@Schema(description = "사용자 역할", example = "USER")
		String role
	) {
		public static UserData from(UserIdentity user) {
			return new UserData(
				user.id(),
				user.email(),
				user.nickname(),
				user.role().toString()
			);
		}
	}
}
