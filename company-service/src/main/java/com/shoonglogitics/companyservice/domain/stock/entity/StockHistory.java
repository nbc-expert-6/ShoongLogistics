package com.shoonglogitics.companyservice.domain.stock.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.companyservice.domain.common.entity.BaseEntity;
import com.shoonglogitics.companyservice.domain.stock.vo.StockChange;
import com.shoonglogitics.companyservice.domain.stock.vo.StockChangeType;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_stock_history")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockHistory extends BaseEntity {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "order_id", columnDefinition = "uuid")
	private UUID orderId;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "beforeAmount", column = @Column(name = "before_amount", nullable = false)),
		@AttributeOverride(name = "changeAmount", column = @Column(name = "change_amount", nullable = false)),
		@AttributeOverride(name = "afterAmount", column = @Column(name = "after_amount", nullable = false)),
	})
	private StockChange stockChange;

	@Enumerated(EnumType.STRING)
	@Column(name = "change_type", nullable = false)
	private StockChangeType changeType;

	@Column(name = "reason")
	private String reason;

	public static StockHistory createForOrder(
		UUID orderId,
		Integer beforeAmount,
		Integer changeAmount,
		Integer afterAmount
	) {
		StockHistory history = new StockHistory();
		history.orderId = orderId;
		history.stockChange = StockChange.of(beforeAmount, changeAmount, afterAmount);
		history.changeType = StockChangeType.ORDER;
		return history;
	}

	public static StockHistory createForOrderCancel(
		UUID orderId,
		Integer beforeAmount,
		Integer changeAmount,
		Integer afterAmount
	) {
		StockHistory history = new StockHistory();
		history.orderId = orderId;
		history.stockChange = StockChange.of(beforeAmount, changeAmount, afterAmount);
		history.changeType = StockChangeType.ORDER_CANCEL;
		return history;
	}

	public static StockHistory createForStockIn(
		UUID stockId,
		Integer beforeAmount,
		Integer changeAmount,
		Integer afterAmount,
		String reason
	) {
		StockHistory history = new StockHistory();
		history.stockChange = StockChange.of(beforeAmount, changeAmount, afterAmount);
		history.changeType = StockChangeType.STOCK_IN;
		history.reason = reason;
		return history;
	}

	public static StockHistory createForStockOut(
		UUID stockId,
		Integer beforeAmount,
		Integer changeAmount,
		Integer afterAmount,
		String reason
	) {
		StockHistory history = new StockHistory();
		history.stockChange = StockChange.of(beforeAmount, changeAmount, afterAmount);
		history.changeType = StockChangeType.STOCK_OUT;
		history.reason = reason;
		return history;
	}
}
