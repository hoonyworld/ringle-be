package org.ringle.gateway.security;

import java.io.IOException;

import org.ringle.globalutils.exception.CommonErrorCode;
import org.ringle.globalutils.exception.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityErrorResponseWriter {

	private final ObjectMapper objectMapper;

	public void write(HttpServletResponse response, CommonErrorCode errorCode) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.status(errorCode.getHttpStatus().value())
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();

		try {
			response.setStatus(errorCode.getHttpStatus().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		} catch (IOException e) {
			log.error("Failed to write error response", e);
		}
	}
}
