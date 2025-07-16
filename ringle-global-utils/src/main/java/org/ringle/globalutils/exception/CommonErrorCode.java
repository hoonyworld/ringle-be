package org.ringle.globalutils.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements BaseErrorCode {

	CONFLICT(HttpStatus.CONFLICT, "COMMON_001", "Duplicated Value"),
	REQUEST_PARAMETER_BIND_FAILED(HttpStatus.BAD_REQUEST, "COMMON_002", "PARAMETER_BIND_FAILED"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_003", "BAD REQUEST"),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_004", "INVALID REQUEST"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_005", "INTERNAL SERVER ERROR"),
	MALFORMED_JSON(HttpStatus.BAD_REQUEST, "COMMON_006", "MALFORMED JSON"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_007", "METHOD NOT ALLOWED"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_008", "UNAUTHORIZED"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_009", "FORBIDDEN"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_010", "NOT FOUND");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
