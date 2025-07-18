package org.ringle.infra.external.pay;

import java.math.BigDecimal;

public interface PaymentGateway {
	boolean requestPayment(Long userId, Long membershipPlanId, BigDecimal amount);
}
