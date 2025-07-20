package org.ringle.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AssignPlanToCompanyRequest(
	@NotBlank
	Long planId
) {}

