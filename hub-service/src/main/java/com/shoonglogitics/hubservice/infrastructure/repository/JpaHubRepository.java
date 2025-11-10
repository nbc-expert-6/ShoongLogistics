package com.shoonglogitics.hubservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.hubservice.domain.entity.Hub;

@Repository
public interface JpaHubRepository extends JpaRepository<Hub, UUID> {

}
