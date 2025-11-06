package com.shoonglogitics.orderservice.domain.order.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

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
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private List<OrderItem> orderItems = new ArrayList<>();

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "companyId", column = @Column(name = "receiver_company_id")),
		@AttributeOverride(name = "companyName", column = @Column(name = "receiver_company_name"))
	})
	private CompanyInfo receiver;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "companyId", column = @Column(name = "supplier_company_id")),
		@AttributeOverride(name = "companyName", column = @Column(name = "supplier_company_name"))
	})
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

	//Todo 생성시 검증 로직 추가
	public static Order create(CompanyInfo receiver,
		CompanyInfo supplier,
		String request,
		Money totalPrice,
		Address address,
		List<OrderItem> orderItems) {
		Order order = new Order();
		order.receiver = receiver;
		order.supplier = supplier;
		order.request = request;
		order.totalPrice = totalPrice;
		order.address = address;
		order.status = OrderStatus.PAYMENT_PENDING;
		order.orderItems = orderItems != null ? new ArrayList<>(orderItems) : new ArrayList<>();
		return order;
	}
}
