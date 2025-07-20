package org.ringle.domain.membership;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyMembershipPlanRepository extends JpaRepository<CompanyMembershipPlan, Long> {
	@Query("SELECT cmp.membershipPlanId FROM CompanyMembershipPlan cmp WHERE cmp.companyId = :companyId")
	List<Long> findPlanIdsByCompanyId(@Param("companyId") Long companyId);

	Optional<CompanyMembershipPlan> findByCompanyIdAndMembershipPlanId(Long companyId, Long membershipPlanId);
}
