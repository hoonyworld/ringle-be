package org.ringle.domain.membership;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
	Optional<UserMembership> findByUserId(Long userId);
}
