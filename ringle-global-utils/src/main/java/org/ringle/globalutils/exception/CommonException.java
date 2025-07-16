package org.ringle.globalutils.exception;

import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@Getter
public class CommonException extends ResponseStatusException {

	private final BaseErrorCode errorCode;

	public CommonException(BaseErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public CommonException(BaseErrorCode errorCode, String message) {
		super(errorCode.getHttpStatus(), message);
		this.errorCode = errorCode;
	}
}
