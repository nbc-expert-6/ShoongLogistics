package com.shoonglogitics.orderservice.domain.order.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;
import com.shoonglogitics.orderservice.global.common.entity.BaseAggregateRoot;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseAggregateRoot<Order> {
	@Id
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id = UUID.randomUUID();

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private List<OrderItem> orderItems = new ArrayList<>();

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "companyId", column = @Column(name = "receiver_company_id")),
		@AttributeOverride(name = "companyName", column = @Column(name = "receiver_company_name"))})
	private CompanyInfo receiver;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "companyId", column = @Column(name = "supplier_company_id")),
		@AttributeOverride(name = "companyName", column = @Column(name = "supplier_company_name"))})
	private CompanyInfo supplier;

	@Column(name = "request")
	private String request;

	@Embedded
	private Money totalPrice;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	private Address address;

	@Column(name = "paid_at")
	private LocalDateTime paidAt;

	public static Order create(Long userId, CompanyInfo receiver, CompanyInfo supplier, String request,
		Money totalPrice,
		Address address, List<OrderItem> orderItems) {
		Order order = new Order();
		order.userId = userId;
		order.receiver = receiver;
		order.supplier = supplier;
		order.request = request;
		order.totalPrice = totalPrice;
		order.address = address;
		order.status = OrderStatus.PENDING;
		order.orderItems = orderItems != null ? new ArrayList<>(orderItems) : new ArrayList<>();
		//불변식 검증
		order.validateInvariants();

		order.registerEvent(new OrderCreatedEvent(
			order.id
		));
		return order;
	}

	private void validateInvariants() {
		if (receiver == null || supplier == null) {
			throw new IllegalArgumentException("공급업체와 수령업체 정보는 필수입니다.");
		}
		if (receiver.getCompanyId().equals(supplier.getCompanyId())) {
			throw new IllegalArgumentException("공급업체와 수령업체가 동일할 수 없습니다.");
		}
		if (address == null) {
			throw new IllegalArgumentException("주소 정보는 필수입니다.");
		}
		if (orderItems == null || orderItems.isEmpty()) {
			throw new IllegalArgumentException("주문 항목이 비어 있을 수 없습니다.");
		}
		if (totalPrice == null || totalPrice.isNegative()) {
			throw new IllegalArgumentException("주문 총액은 0보다 커야 합니다.");
		}
	}

	public void changeStatus(OrderStatus next) {
		if (!status.canTransitionTo(next)) {
			throw new IllegalStateException(String.format("현재 상태(%s)에서는 %s 상태로 변경할 수 없습니다.", status, next));
		}
		this.status = next;
	}

	public int getOrderItemCount() {
		return orderItems.size();
	}

	public boolean isCancellable() {
		if (!status.canBeCancelled()) {
			return false;
		}
		return true;
	}
}
