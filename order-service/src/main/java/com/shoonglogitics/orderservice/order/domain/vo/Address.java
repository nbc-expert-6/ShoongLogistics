package com.shoonglogitics.orderservice.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    public static Address of(String address, String addressDetail, String zipCode, Point location) {
        Address addr = new Address();
        addr.address = address;
        addr.addressDetail = addressDetail;
        addr.zipCode = zipCode;
        addr.location = location;
        return addr;
    }
}
