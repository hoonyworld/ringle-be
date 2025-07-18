package org.ringle.apis.payment.exception;

import org.ringle.globalutils.exception.CommonException;

public class PaymentFailedException extends CommonException {
	private final PaymentErrorCode errorCode;

	public PaymentFailedException(PaymentErrorCode errorCode) {
		super(errorCode, errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
