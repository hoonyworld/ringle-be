package org.ringle.gateway.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_USER = "USER";
	private static final String ROLE_COMPANY = "COMPANY";

	private static final String[] WHITELIST_URLS = {
		"/api/v1/auth/login",
		"/actuator/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/error",
		"/"
	};

	private final JwtDecoder jwtDecoder;
	private final Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		org.ringle.gateway.security.SseJwtAuthenticationFilter sseFilter = new org.ringle.gateway.security.SseJwtAuthenticationFilter(
			jwtDecoder, jwtAuthenticationConverter);

		http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exceptions -> exceptions
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
			)
			.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
			)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(WHITELIST_URLS).permitAll()
				.requestMatchers("/api/v1/admin/**").hasRole(ROLE_ADMIN)
				.requestMatchers("/api/v1/user/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/membership/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/membership-plans/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/conversation/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/conversation-topics/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/subscribe/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/speech/stt/**").hasAnyRole(ROLE_USER, ROLE_ADMIN, ROLE_COMPANY)
				.requestMatchers("/api/v1/auth/**").authenticated()
				.anyRequest().authenticated()
			)
			.addFilterBefore(sseFilter, BearerTokenAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:5173"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
