package com.shoonglogitics.companyservice.domain.company.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.companyservice.domain.common.entity.BaseAggregateRoot;
import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyAddress;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;
import com.shoonglogitics.companyservice.infrastructure.persistence.converter.GeoLocationConverter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_company")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseAggregateRoot<Company> {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "p_hub_id", columnDefinition = "uuid")
	private UUID hubId;

	@Column(name = "name", nullable = false)
	private String name;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
		@AttributeOverride(name = "addressDetail", column = @Column(name = "address_detail", nullable = false)),
		@AttributeOverride(name = "zipCode", column = @Column(name = "zip_code", nullable = false)),
	})
	private CompanyAddress address;

	@Column(name = "location", nullable = false, columnDefinition = "geometry(Point, 4326)")
	@Convert(converter = GeoLocationConverter.class)
	private GeoLocation location;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private CompanyType type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "company_id")
	private List<Product> products = new ArrayList<>();


	public static Company create(
		UUID hubId,
		String name,
		CompanyAddress address,
		GeoLocation location,
		CompanyType type
	) {
		Company company = new Company();
		company.hubId = hubId;
		company.name = name;
		company.address = address;
		company.location = location;
		company.type = type;

		return company;
	}
}
