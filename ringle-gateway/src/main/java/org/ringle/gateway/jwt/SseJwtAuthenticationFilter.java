package org.ringle.gateway.security;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SseJwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtDecoder jwtDecoder;
	private final Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		if (request.getRequestURI().startsWith("/api/v1/subscribe")) {
			String token = request.getParameter("token");

			if (StringUtils.hasText(token)) {
				try {
					Jwt jwt = this.jwtDecoder.decode(token);
					AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					log.info("Successfully authenticated user from SSE query parameter token.");
				} catch (JwtException e) {
					log.debug("Failed to process JWT from SSE query parameter", e);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
