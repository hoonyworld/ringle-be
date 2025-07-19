package org.ringle.domain.membership;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {
	List<UserMembership> findAllByUserId(Long userId);

	Optional<UserMembership> findByUserId(Long userId);

	boolean existsByUserIdAndStatus(Long userId, MembershipStatus status);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select um from UserMembership um where um.userId = :userId and um.status = :status")
	Optional<UserMembership> findByUserIdAndStatusWithLock(Long userId, MembershipStatus status);
}
