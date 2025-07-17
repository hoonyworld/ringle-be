package org.ringle.apis.auth.exception;

import org.ringle.globalutils.exception.CommonException;

import lombok.Getter;

@Getter
public class UserNotFoundException extends CommonException {
	private final UserErrorCode errorCode;

	public UserNotFoundException(UserErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
