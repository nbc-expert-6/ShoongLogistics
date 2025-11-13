package com.shoonglogitics.companyservice.domain.stock.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.companyservice.domain.common.entity.BaseAggregateRoot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

	@Column(name = "product_id", columnDefinition = "uuid", nullable = false, unique = true)
	private UUID productId;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "stock_id")
	private List<StockHistory> stockHistories = new ArrayList<>();

	private Stock(UUID productId, Integer initialAmount) {
		validateAmount(initialAmount);
		this.productId = productId;
		this.amount = initialAmount;
	}

	public static Stock create(UUID productId, Integer initialAmount) {
		Stock stock = new Stock(productId, initialAmount);

		// 초기 재고 이력 생성
		StockHistory initialHistory = StockHistory.createForStockIn(
			stock.id,
			0,
			initialAmount,
			initialAmount,
			"초기 재고 등록"
		);
		stock.stockHistories.add(initialHistory);

		return stock;
	}

	public void increaseStock(Integer quantity, String reason) {
		validateQuantity(quantity);
		
		Integer beforeAmount = this.amount;
		this.amount += quantity;
		
		StockHistory history = StockHistory.createForStockIn(
			this.id,
			beforeAmount,
			quantity,
			this.amount,
			reason
		);
		this.stockHistories.add(history);
	}

	public void decreaseStock(Integer quantity, String reason) {
		validateQuantity(quantity);
		
		if (this.amount < quantity) {
			throw new IllegalStateException(
				String.format("재고가 부족합니다. 현재 재고: %d, 요청 수량: %d", this.amount, quantity)
			);
		}

		Integer beforeAmount = this.amount;
		this.amount -= quantity;

		StockHistory history = StockHistory.createForStockOut(
			this.id,
			beforeAmount,
			-quantity,
			this.amount,
			reason
		);
		this.stockHistories.add(history);
	}

	public void decreaseStockForOrder(UUID orderId, Integer quantity) {
		validateQuantity(quantity);

		if (this.amount < quantity) {
			throw new IllegalStateException(
				String.format("재고가 부족합니다. 현재 재고: %d, 주문 수량: %d", this.amount, quantity)
			);
		}

		Integer beforeAmount = this.amount;
		this.amount -= quantity;

		StockHistory history = StockHistory.createForOrder(
			orderId,
			beforeAmount,
			-quantity,
			this.amount
		);
		this.stockHistories.add(history);
	}

	public void increaseStockForOrderCancel(UUID orderId, Integer quantity) {
		validateQuantity(quantity);

		Integer beforeAmount = this.amount;
		this.amount += quantity;

		StockHistory history = StockHistory.createForOrderCancel(
			orderId,
			beforeAmount,
			quantity,
			this.amount
		);
		this.stockHistories.add(history);
	}

	public void delete(Long deletedBy) {
		this.softDelete(deletedBy);
	}

	private void validateAmount(Integer amount) {
		if (amount == null || amount < 0) {
			throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
		}
	}

	private void validateQuantity(Integer quantity) {
		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("변경 수량은 0보다 커야 합니다.");
		}
	}
}
