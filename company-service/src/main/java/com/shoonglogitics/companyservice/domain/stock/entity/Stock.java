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

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "stock_id")
	private List<StockHistory> stockHistories = new ArrayList<>();

	private Stock(Integer initialAmount) {
		validateAmount(initialAmount);
		this.amount = initialAmount;
	}

	public static Stock create(Integer initialAmount) {
		Stock stock = new Stock(initialAmount);

		// 초기 재고 이력 생성
		StockHistory initialHistory = StockHistory.createForStockIn(
			null,  // stockId는 save 후에 설정됨
			0,
			initialAmount,
			initialAmount,
			"초기 재고 등록"
		);
		stock.stockHistories.add(initialHistory);

		return stock;
	}

	private void validateAmount(Integer amount) {
		if (amount == null || amount < 0) {
			throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
		}
	}
}
