package com.shoonglogitics.hubservice.infrastructure.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.shoonglogitics.hubservice.domain.vo.GeoLocation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeoLocationConverter implements AttributeConverter<GeoLocation, Point> {

	@Override
	public Point convertToDatabaseColumn(GeoLocation geoLocation) {
		return createPoint(geoLocation);
	}

	@Override
	public GeoLocation convertToEntityAttribute(Point point) {
		return GeoLocation.of(point.getY(), point.getX());
	}

	private static Point createPoint(GeoLocation geoLocation) {
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel());

		Coordinate coordinate = new Coordinate(geoLocation.getLongitude(), geoLocation.getLatitude());
		return geometryFactory.createPoint(coordinate);
	}

}
