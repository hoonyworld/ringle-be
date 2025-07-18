package org.ringle.apis.payment.exception;

import org.ringle.globalutils.exception.BaseErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements BaseErrorCode {

	PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "PAYMENT_001", "결제에 실패하였습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
