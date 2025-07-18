package org.ringle.domain.membership;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
	List<UserMembership> findAllByUserId(Long userId);

	Optional<UserMembership> findByUserId(Long userId);
}
