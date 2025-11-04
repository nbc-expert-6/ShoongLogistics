package com.shoonglogitics.orderservice.order.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseEntity;
import com.shoonglogitics.orderservice.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Quentity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "p_order_item")
@Where(clause = "deleted_at IS NULL")
@Getter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

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
