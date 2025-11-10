package com.shoonglogitics.companyservice.application.command;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import lombok.Builder;

@Builder
public record GetProductsCommand(
	UUID companyId,
	List<UUID> categoryIds,
	Pageable pageable
) {

}
