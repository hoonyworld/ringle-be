package org.ringle.apis.membership.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "멤버십 플랜 구매 요청")
public record PlanPurchaseRequest(

	@Schema(description = "구매할 멤버십 플랜 ID", example = "123")
	@NotNull
	Long planId
) {
}
