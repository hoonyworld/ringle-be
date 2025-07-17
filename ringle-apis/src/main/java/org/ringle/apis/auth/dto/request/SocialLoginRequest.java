package org.ringle.apis.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
	name = "SocialLoginRequest",
	description = "이메일과 비밀번호로 로그인 요청을 보낼 때 사용되는 DTO"
)
public record SocialLoginRequest(
	@NotBlank @Email
	@Schema(description = "사용자 이메일", example = "user@example.com")
	String email,

	@Schema(description = "사용자 비밀번호", example = "password1234")
	String password

) {
}
