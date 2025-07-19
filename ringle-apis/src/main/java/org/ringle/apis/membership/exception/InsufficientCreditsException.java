package org.ringle.apis.membership.exception;

import org.ringle.globalutils.exception.CommonException;

public class InsufficientCreditsException extends CommonException {
	private final MembershipErrorCode errorCode;

	public InsufficientCreditsException(MembershipErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
