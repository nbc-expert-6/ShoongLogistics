package com.shoonglogitics.companyservice.domain.productcategory.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.companyservice.domain.common.entity.BaseAggregateRoot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_product_category")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory extends BaseAggregateRoot<ProductCategory> {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;
}
