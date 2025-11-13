package com.shoonglogitics.hubservice.infrastructure.repository;

import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.vo.HubId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaHubRouteRepository extends JpaRepository<HubRoute, UUID> {

    @Query("SELECT hr FROM HubRoute hr " +
            "WHERE hr.departureHubId = :departure " +
            "AND hr.arrivalHubId = :arrival " +
            "AND hr.deletedAt IS NULL")
    Optional<HubRoute> findByDepartureAndArrival(
            @Param("departure") HubId departure,
            @Param("arrival") HubId arrival
    );

    @Query("SELECT hr FROM HubRoute hr WHERE hr.deletedAt IS NULL")
    List<HubRoute> findAllActive();

    @Query("SELECT hr FROM HubRoute hr " +
            "WHERE hr.departureHubId = :departureId " +
            "AND hr.deletedAt IS NULL")
    List<HubRoute> findByDepartureHubId(@Param("departureId") HubId departureId);
}
