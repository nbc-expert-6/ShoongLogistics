package com.shoonglogitics.orderservice.order.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseTimeEntity;
import com.shoonglogitics.orderservice.order.domain.vo.Address;
import com.shoonglogitics.orderservice.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Money;
import com.shoonglogitics.orderservice.order.domain.vo.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "p_order")
@Getter
public class Order extends BaseTimeEntity {
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

    private String request;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_price",nullable = false))
    private Money totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address address;

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;
}
