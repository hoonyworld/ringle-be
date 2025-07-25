package org.ringle.gateway.security;

import org.ringle.globalutils.exception.CommonErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final SecurityErrorResponseWriter securityErrorResponseWriter;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) {
		log.warn("Not Authenticated Request - uri: {}", request.getRequestURI(), authException);
		securityErrorResponseWriter.write(response, CommonErrorCode.UNAUTHORIZED);
	}
}
