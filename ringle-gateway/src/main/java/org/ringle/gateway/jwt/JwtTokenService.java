package org.ringle.gateway.jwt;

import org.ringle.globalutils.auth.Role;

public interface JwtTokenService {
	String generateAccessToken(Long userId, Role role);

	String generateRefreshToken(Long userId);

	long getRefreshTokenExpiration();

	Long getUserIdFromToken(String toke);
}
