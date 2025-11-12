package com.shoonglogitics.companyservice.application.command.stock;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.shoonglogitics.companyservice.presentation.common.dto.PageRequest;

import lombok.Builder;

@Builder
public record GetStocksCommand(
	UUID productId,
	PageRequest pageRequest
) {
	public Pageable toPageable() {
		return pageRequest.toPageable();
	}
}
