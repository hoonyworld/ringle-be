package org.ringle.domain.user.vo;

import org.ringle.domain.user.User;
import org.ringle.globalutils.auth.Role;

public record UserIdentity(
	Long id,
	String email,
	String nickname,
	Role role
) {
	public static UserIdentity newInstance(User user) {
		Long id = user.getId();
		String email = user.getEmail();
		String nickname = user.getName();
		Role role = user.getRole();
		return new UserIdentity(id, email, nickname, role);
	}
}
