package com.shoonglogitics.companyservice.domain.company.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAddress {
	private String address;
	private String addressDetail;
	private String zipCode;

	public static CompanyAddress of(String address, String detail, String zipCode) {
		CompanyAddress value = new CompanyAddress();
		value.address = address;
		value.addressDetail = detail;
		value.zipCode = zipCode;
		return value;
	}
}
