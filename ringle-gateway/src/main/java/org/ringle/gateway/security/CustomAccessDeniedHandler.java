package org.ringle.gateway.security;

import org.ringle.globalutils.exception.CommonErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final SecurityErrorResponseWriter securityErrorResponseWriter;

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) {
		log.warn("Access Denied - uri: {}", request.getRequestURI(), accessDeniedException);
		securityErrorResponseWriter.write(response, CommonErrorCode.FORBIDDEN);
	}
}
