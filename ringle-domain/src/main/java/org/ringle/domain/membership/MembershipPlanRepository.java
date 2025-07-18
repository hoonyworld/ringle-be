package org.ringle.domain.membership;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
	List<MembershipPlan> findByCustomerType(CustomerType customerType);

}
