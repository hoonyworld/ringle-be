package org.ringle.apis.membership.exception;

import org.ringle.globalutils.exception.CommonException;

public class MembershipForbiddenException extends CommonException {
	private final MembershipErrorCode errorCode;

	public MembershipForbiddenException(MembershipErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
