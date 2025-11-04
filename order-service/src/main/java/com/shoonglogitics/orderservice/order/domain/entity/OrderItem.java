package com.shoonglogitics.orderservice.order.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseTimeEntity;
import com.shoonglogitics.orderservice.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Quentity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "p_order_item")
@Getter
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "productId", column = @Column(name = "product_id")),
            @AttributeOverride(name = "price", column = @Column(name = "price"))
    })
    private ProductInfo productInfo;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "amount"))
    private Quentity amount;

}
