package com.shoonglogitics.companyservice.domain.company.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.companyservice.domain.common.entity.BaseEntity;
import com.shoonglogitics.companyservice.domain.company.vo.ProductInfo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_product")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "product_category_id", columnDefinition = "uuid")
	private UUID productCategoryId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "name", nullable = false)),
		@AttributeOverride(name = "price", column = @Column(name = "price", nullable = false)),
		@AttributeOverride(name = "description", column = @Column(name = "description", nullable = false, columnDefinition = "text"))
	})
	private ProductInfo productInfo;

	public static Product create(
		UUID productCategoryId,
		String name,
		Integer price,
		String description
	) {
		Product product = new Product();
		product.productCategoryId = productCategoryId;
		product.productInfo = ProductInfo.of(name, price, description);

		return product;
	}

	public void changeCategory(UUID newCategoryId) {
		this.productCategoryId = newCategoryId;
	}
}
