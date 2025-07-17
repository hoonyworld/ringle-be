package org.ringle.apis.auth.exception;

import org.ringle.globalutils.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "존재하지 않는 사용자입니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_002", "이미 존재하는 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_003", "비밀번호가 올바르지 않습니다."),
	INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "USER_004", "이메일 또는 비밀번호가 올바르지 않습니다."),
	USER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "USER_005", "접근 권한이 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
