package org.ringle.gateway.jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.ringle.gateway.constants.JwtConstants;
import org.ringle.globalutils.auth.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtTokenProvider {

	private static final String TOKEN_TYPE_CLAIM = "type";
	private static final String ACCESS_TOKEN_TYPE = "access";
	private static final String REFRESH_TOKEN_TYPE = "refresh";

	private final JwtEncoder jwtEncoder;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;

	public JwtTokenProvider(
		JwtEncoder jwtEncoder,
		@Value("${jwt.access-token-expiration}")
		long accessTokenExpiration,
		@Value("${jwt.refresh-token-expiration}")
		long refreshTokenExpiration
	) {
		this.jwtEncoder = jwtEncoder;
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public String generateAccessToken(Long userId, List<Role> roles) {
		List<String> roleStrings = roles.stream()
			.map(Role::getKey)
			.toList();

		Map<String, Object> claims = Map.of(
			JwtConstants.ROLES_CLAIM, roleStrings,
			TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE
		);
		return generateToken(userId.toString(), accessTokenExpiration, claims);
	}

	public String generateRefreshToken(Long userId) {
		Map<String, Object> claims = Map.of(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE);
		return generateToken(userId.toString(), refreshTokenExpiration, claims);
	}

	private String generateToken(String subject, long expirationSeconds, Map<String, Object> claims) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(expirationSeconds);

		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
			.issuer(JwtConstants.ISSUER)
			.issuedAt(now)
			.expiresAt(expiry)
			.subject(subject)
			.claims(c -> c.putAll(claims))
			.build();

		JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
		JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(header, claimsSet);
		return jwtEncoder.encode(encoderParameters).getTokenValue();
	}
}
