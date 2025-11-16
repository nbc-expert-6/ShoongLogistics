package com.shoonglogitics.orderservice.domain.product.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.product.domain.vo.Quantity;
import com.shoonglogitics.orderservice.global.common.entity.BaseAggregateRoot;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_stock")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseAggregateRoot<Stock> {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "productId", nullable = false)
	private UUID productId;

	@Embedded
	private Quantity quantity;

	public static Stock create(UUID productId, Quantity quantity) {
		Stock stock = new Stock();
		stock.productId = productId;
		stock.quantity = quantity;
		return stock;
	}

	public void decreaseStock(Integer amount) {
		if (this.quantity.getQuantity() < amount) {
			throw new IllegalArgumentException("재고가 부족합니다.");
		}
		this.quantity = this.quantity.subtract(amount);
	}
}
