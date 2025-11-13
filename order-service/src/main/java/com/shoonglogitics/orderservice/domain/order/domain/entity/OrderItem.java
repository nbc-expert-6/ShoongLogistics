package com.shoonglogitics.orderservice.domain.order.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quantity;
import com.shoonglogitics.orderservice.global.common.entity.BaseEntity;

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
@Table(name = "p_order_item")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "productId", column = @Column(name = "product_id")),
		@AttributeOverride(name = "price", column = @Column(name = "price"))
	})
	private ProductInfo productInfo;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "quantity"))
	private Quantity quantity;

	public static OrderItem create(ProductInfo productInfo, Quantity quantity) {
		OrderItem orderItem = new OrderItem();
		orderItem.productInfo = productInfo;
		orderItem.quantity = quantity;
		return orderItem;
	}

	public Money calculateTotalPrice() {
		return productInfo.getPrice().multiply(quantity.getValue());
	}
}
