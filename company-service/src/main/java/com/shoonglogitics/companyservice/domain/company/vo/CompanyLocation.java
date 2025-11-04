package com.shoonglogitics.companyservice.domain.company.vo;

import org.springframework.data.geo.Point;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyLocation {
	private Point location;

	public static CompanyLocation of(Point point) {
		CompanyLocation value = new CompanyLocation();
		value.location = point;

		return value;
	}
}
