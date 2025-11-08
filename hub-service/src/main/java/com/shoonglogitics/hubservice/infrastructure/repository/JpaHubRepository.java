package com.shoonglogitics.hubservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.hubservice.domain.entity.Hub;

public interface JpaHubRepository extends JpaRepository<Hub, UUID> {

}
