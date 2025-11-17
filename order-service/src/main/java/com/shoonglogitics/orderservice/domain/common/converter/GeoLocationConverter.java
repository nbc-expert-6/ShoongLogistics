package com.shoonglogitics.orderservice.domain.common.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeoLocationConverter implements AttributeConverter<GeoLocation, Point> {

	private static final GeometryFactory GEOMETRY_FACTORY =
		new GeometryFactory(new PrecisionModel(), 4326);

	@Override
	public Point convertToDatabaseColumn(GeoLocation location) {
		if (location == null) {
			return null;
		}

		return GEOMETRY_FACTORY.createPoint(
			new Coordinate(location.getLongitude(), location.getLatitude())
		);
	}

	@Override
	public GeoLocation convertToEntityAttribute(Point point) {
		if (point == null) {
			return null;
		}

		return GeoLocation.of(point.getY(), point.getX());
	}
}