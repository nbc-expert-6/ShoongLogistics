package com.shoonglogitics.orderservice.delivery.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Embeddable
public class DeliveryEstimate {
    @Column(name = "estimated_distance")
    private Long distance;
    @Column(name = "estimated_duration")
    private Integer duration;
}
