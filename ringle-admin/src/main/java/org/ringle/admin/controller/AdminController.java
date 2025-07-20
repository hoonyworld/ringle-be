package org.ringle.admin.controller;

import org.ringle.admin.dto.request.AssignPlanToCompanyRequest;
import org.ringle.admin.dto.response.AssignPlanToCompanyResponse;
import org.ringle.admin.usecase.AdminUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

	private final AdminUseCase adminUseCase;

	@PostMapping("/companies/{companyId}/plans")
	public ResponseEntity<AssignPlanToCompanyResponse> assignPlanToCompany(
		@PathVariable Long companyId,
		@Valid @RequestBody AssignPlanToCompanyRequest request
	) {
		AssignPlanToCompanyResponse response = adminUseCase.assignB2bPlan(companyId, request.planId());
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(response);
	}

	@DeleteMapping("/companies/{companyId}/plans/{planId}")
	public ResponseEntity<Void> removePlanFromCompany(
		@PathVariable Long companyId,
		@PathVariable Long planId
	) {
		adminUseCase.removeB2bPlan(companyId, planId);
		return ResponseEntity.noContent().build();
	}
}
