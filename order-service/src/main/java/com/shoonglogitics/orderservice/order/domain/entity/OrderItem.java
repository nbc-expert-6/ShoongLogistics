package com.shoonglogitics.orderservice.order.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseEntity;
import com.shoonglogitics.orderservice.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Quentity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "p_order_item")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id",columnDefinition = "uuid")
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

    //Todo 생성시 검증 로직 추가
    public static OrderItem create(ProductInfo productInfo, Quentity amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.productInfo = productInfo;
        orderItem.amount = amount;
        return orderItem;
    }
}
