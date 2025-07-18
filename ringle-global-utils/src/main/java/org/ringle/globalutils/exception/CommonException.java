package org.ringle.globalutils.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
	private final BaseErrorCode errorCode;

	public CommonException(BaseErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public CommonException(BaseErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseErrorCode getErrorCode() {
		return errorCode;
	}
}
