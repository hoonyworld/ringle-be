package org.ringle.apis.auth.dto.response;

import org.ringle.domain.user.vo.UserIdentity;

public record UserProfileResponse(
	Long id,
	String email,
	String name,
	String role
) {
	public static UserProfileResponse from(UserIdentity user) {
		return new UserProfileResponse(
			user.id(),
			user.email(),
			user.nickname(),
			user.role().toString()
		);
	}
}
