package org.ringle.apis.membership.exception;

import org.ringle.globalutils.exception.CommonException;
import lombok.Getter;

@Getter
public class UserMembershipNotFoundException extends CommonException {
	private final MembershipErrorCode errorCode;

	public UserMembershipNotFoundException(MembershipErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
