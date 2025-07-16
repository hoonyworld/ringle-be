package org.ringle.gateway.jwt;

import java.util.List;

import org.ringle.globalutils.auth.Role;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public String generateAccessToken(Long userId, Role role) {
		return jwtTokenProvider.generateAccessToken(userId, List.of(role));
	}

	@Override
	public String generateRefreshToken(Long userId) {
		return jwtTokenProvider.generateRefreshToken(userId);
	}

	@Override
	public long getRefreshTokenExpiration() {
		return jwtTokenProvider.getRefreshTokenExpiration();
	}
}
