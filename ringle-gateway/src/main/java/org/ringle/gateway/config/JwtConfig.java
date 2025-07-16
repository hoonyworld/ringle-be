package org.ringle.gateway.config;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.crypto.spec.SecretKeySpec;

import org.ringle.gateway.constants.JwtConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {
	private static final MacAlgorithm SIGNATURE_ALGORITHM = MacAlgorithm.HS256;
	private static final String PRINCIPAL_CLAIM = "sub";
	private static final String NO_AUTHORITY_PREFIX = "";
	private final String secretKey;

	public JwtConfig(
		@Value("${jwt.secret-key}")
		String secretKey
	) {
		this.secretKey = secretKey;
	}

	/**
	 * JWT를 암호화하고 서명하는 데 사용될 키의 소스(`JWKSource`)를 생성하여 Bean으로 등록합니다.
	 * HMAC-SHA 알고리즘에 사용될 대칭 키를 `OctetSequenceKey` 형태로 래핑하여 제공합니다.
	 *
	 * @return 생성된 `JWKSource<SecurityContext>` 객체
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		OctetSequenceKey jwk = new OctetSequenceKey.Builder(secretKey.getBytes(StandardCharsets.UTF_8)).build();
		return new ImmutableJWKSet<>(new JWKSet(jwk));
	}

	/**
	 * JWT를 생성(인코딩)하는 `JwtEncoder`를 생성하여 Bean으로 등록합니다.
	 * 주입받은 `JWKSource`를 사용하여 토큰에 디지털 서명을 수행합니다.
	 *
	 * @param jwkSource 토큰 서명에 사용될 키 소스
	 * @return 생성된 `JwtEncoder` 객체
	 */
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	/**
	 * 클라이언트로부터 받은 JWT를 검증(디코딩)하는 `JwtDecoder`를 생성하여 Bean으로 등록합니다.
	 * 토큰의 서명, 만료 시간(기본 검증) 및 발급자(issuer)가 일치하는지 확인합니다.
	 *
	 * @return 생성된 `JwtDecoder` 객체
	 */
	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(
			secretKey.getBytes(StandardCharsets.UTF_8),
			SIGNATURE_ALGORITHM.getName()
		);

		NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
		OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(JwtConstants.ISSUER);
		decoder.setJwtValidator(validator);

		return decoder;
	}

	/**
	 * 유효한 JWT를 Spring Security의 `Authentication` 객체로 변환하는 `JwtAuthenticationConverter` 빈을 설정합니다.
	 * - JWT의 `roles` 클레임을 추출하여 `GrantedAuthority` 리스트로 변환합니다.
	 * - 기본적으로 붙는 권한 접두어(`SCOPE_`)를 제거하기 위해 빈 문자열로 설정합니다.
	 * - JWT의 `sub` 클레임(문자열 형태의 UUID)을 `UUID` 타입으로 변환하여 인증 주체(Principal)로 사용합니다.
	 *
	 * @return JWT를 `UsernamePasswordAuthenticationToken` 으로 변환하는 Converter
	 */
	@Bean
	public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		authoritiesConverter.setAuthoritiesClaimName(JwtConstants.ROLES_CLAIM);
		authoritiesConverter.setAuthorityPrefix(NO_AUTHORITY_PREFIX);

		return jwt -> {
			Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);
			long principal = Long.parseLong(jwt.getClaimAsString(PRINCIPAL_CLAIM));
			return new UsernamePasswordAuthenticationToken(principal, null, authorities);
		};
	}
}
