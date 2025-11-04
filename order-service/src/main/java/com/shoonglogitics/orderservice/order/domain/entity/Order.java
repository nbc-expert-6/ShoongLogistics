package com.shoonglogitics.orderservice.order.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseAggregateRoot;
import com.shoonglogitics.orderservice.order.domain.vo.Address;
import com.shoonglogitics.orderservice.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Money;
import com.shoonglogitics.orderservice.order.domain.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "p_order")
@Where(clause = "deleted_at IS NULL")
@Getter
public class Order extends BaseAggregateRoot<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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


}
