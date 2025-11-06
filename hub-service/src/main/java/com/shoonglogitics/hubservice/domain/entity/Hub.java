package com.shoonglogitics.hubservice.domain.entity;


import com.shoonglogitics.hubservice.domain.entity.common.BaseAggregateRoot;
import com.shoonglogitics.hubservice.domain.event.HubCreatedEvent;
import com.shoonglogitics.hubservice.domain.event.HubDeactivatedEvent;
import com.shoonglogitics.hubservice.domain.event.HubRenamedEvent;
import com.shoonglogitics.hubservice.domain.vo.Address;
import com.shoonglogitics.hubservice.domain.vo.HubType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Table(name = "p_hubs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseAggregateRoot<Hub> {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "address"))
    private Address address;

    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @Enumerated(EnumType.STRING)
    @Column(name = "hub_type", nullable = false, length = 20)
    private HubType hubType;


    @Builder
    public Hub(String name, Address address, Point location, HubType hubType) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.hubType = hubType;
    }

    public static Hub create(String name, String addressValue, Double latitude, Double longitude, HubType hubType) {
        Hub hub = Hub.builder()
                .name(name)
                .address(Address.of(addressValue))
                .location(createPoint(latitude, longitude))
                .hubType(hubType)
                .build();

        hub.registerEvent(new HubCreatedEvent(
                hub.getId(),
                name,
                addressValue,
                latitude,
                longitude
        ));

        return hub;

    }

    public void update(String name){
        String oldName = this.name;
        this.name = name;
        registerEvent(new HubRenamedEvent(this.id, oldName, name));
    }

    public void deactivate(Long userId){
        if(this.isDeleted()){
            throw new IllegalStateException("이미 삭제된 허브입니다.");
        }

        this.softDelete(userId);
        registerEvent(new HubDeactivatedEvent(this.id));
    }


    public boolean isCentralHub(){
        return this.hubType == HubType.CENTRAL;
    }

    public Double getLatitude(){
        return location != null ? location.getY() : null;
    }

    public Double getLongitude(){
        return location != null ? location.getX() : null;
    }

    private static Point createPoint(Double latitude, Double longitude){
        if(latitude == null || longitude == null){
            return null;
        }

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Coordinate coordinate = new Coordinate(longitude, latitude);
        return geometryFactory.createPoint(coordinate);
    }

}
