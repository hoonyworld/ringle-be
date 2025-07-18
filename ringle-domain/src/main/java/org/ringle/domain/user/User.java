package org.ringle.domain.user;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.SQLRestriction;
import org.ringle.domain.BaseTimeEntity;
import org.ringle.globalutils.auth.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column()
	private Long companyId;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private User(String name, String email, String password, Role role, Long companyId) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.companyId = companyId;
	}

	public static User create(String name, String email, String password, Role role, Long companyId) {
		return User.builder()
			.name(name)
			.email(email)
			.password(password)
			.role(role)
			.companyId(companyId)
			.build();
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User user)) return false;
		return id != null && id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
