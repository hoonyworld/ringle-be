package org.ringle.admin.service;

import org.ringle.domain.company.Company;
import org.ringle.domain.company.CompanyRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCompanyInfoService {

	private final CompanyRepository companyRepository;

	public Company findById(Long companyId) {
		return companyRepository.findById(companyId)
			.orElseThrow(() -> new EntityNotFoundException("Company not found: " + companyId));
	}
}
