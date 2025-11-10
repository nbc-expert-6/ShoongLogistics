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

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public static ProductCategory create(String name) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.name = name;
		return productCategory;
	}

	public void update(String name) {
		this.name = name;
	}

	public void delete(Long deletedBy) {
		//TODO: 삭제 정책 논의 필요(상품이 가지고있는 기존 카테고리를 어떻게 처리할지)
		this.softDelete(deletedBy);
	}
}
