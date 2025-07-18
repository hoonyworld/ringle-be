package org.ringle.apis.payment.service;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import org.ringle.apis.payment.exception.PaymentErrorCode;
import org.ringle.domain.payment.Payment;
import org.ringle.domain.payment.PaymentRepository;
import org.ringle.domain.payment.PaymentStatus;
import org.ringle.infra.external.pay.PaymentGateway;
import org.ringle.apis.payment.exception.PaymentFailedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {
	private final PaymentGateway paymentGateway;
	private final PaymentRepository paymentRepository;

	public void processPayment(Long userId, Long userMembershipId, BigDecimal amount, Long planId) {
		boolean isPayment = paymentGateway.requestPayment(userId, planId, amount);

		if (!isPayment) {
			Payment failedPayment = Payment.create(userId, userMembershipId, amount, PaymentStatus.FAILED);
			paymentRepository.save(failedPayment);
			log.warn("PG 결제 실패 기록 저장: userId={}, planId={}, amount={}", userId, planId, amount);
			throw new PaymentFailedException(PaymentErrorCode.PAYMENT_FAILED);
		}

		Payment successPayment = Payment.create(userId, userMembershipId, amount, PaymentStatus.COMPLETED);
		paymentRepository.save(successPayment);
	}
}
