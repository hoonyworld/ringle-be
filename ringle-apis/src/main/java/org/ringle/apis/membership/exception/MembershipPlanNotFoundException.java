package org.ringle.apis.membership.exception;

import org.ringle.globalutils.exception.CommonException;

public class MembershipPlanNotFoundException extends CommonException {
	private final MembershipErrorCode errorCode;

	public MembershipPlanNotFoundException(MembershipErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
