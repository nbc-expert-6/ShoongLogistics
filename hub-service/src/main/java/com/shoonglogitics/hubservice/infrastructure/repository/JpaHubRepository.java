package com.shoonglogitics.hubservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.hubservice.domain.vo.HubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.hubservice.domain.entity.Hub;

@Repository
public interface JpaHubRepository extends JpaRepository<Hub, UUID> {

    List<Hub> findByDeletedAtIsNull();

    @Query("SELECT h FROM Hub h WHERE h.deletedAt IS NULL")
    List<Hub> findAllActive();

    @Query("SELECT h FROM Hub h WHERE h.id = :id AND h.deletedAt IS NULL")
    Optional<Hub> findActiveById(@Param("id") UUID id);

    List<Hub> findByHubType(HubType hubType);

    @Query(value = """
        SELECT ST_Distance(
            (SELECT location FROM p_hub WHERE id = :fromId)::geography,
            (SELECT location FROM p_hub WHERE id = :toId)::geography
        )
        """, nativeQuery = true)
    Double calculateDistanceInMeters(@Param("fromId") UUID fromId, @Param("toId") UUID toId);

}
