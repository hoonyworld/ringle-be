package org.ringle.domain.company;

import java.util.Objects;

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
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private CompanyName companyName;

	@Builder(access = AccessLevel.PRIVATE)
	private Company(CompanyName companyName) {
		this.companyName = companyName;
	}

	public static Company create(CompanyName companyName) {
		return Company.builder()
			.companyName(companyName)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Company company))
			return false;
		return id != null && id.equals(company.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

