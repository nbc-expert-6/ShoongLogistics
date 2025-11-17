package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;
import com.shoonglogitics.orderservice.domain.common.converter.GeoLocationConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(name = "location", nullable = false, columnDefinition = "geometry(Point, 4326)")
	@Convert(converter = GeoLocationConverter.class)
	private GeoLocation location;

	public static Address of(String address, String addressDetail, String zipCode, GeoLocation location) {
		Address addr = new Address();
		addr.address = address;
		addr.addressDetail = addressDetail;
		addr.zipCode = zipCode;
		addr.location = location;
		return addr;
	}
}
