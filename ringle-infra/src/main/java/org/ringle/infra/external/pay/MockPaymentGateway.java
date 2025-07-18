package org.ringle.infra.external.pay;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class MockPaymentGateway implements PaymentGateway {
	@Override
	public boolean requestPayment(Long userId, Long membershipPlanId, BigDecimal amount) {
		return true; // 항상 결제 성공으로 가정
	}
}
